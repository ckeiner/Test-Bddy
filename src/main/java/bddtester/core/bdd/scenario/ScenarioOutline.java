package bddtester.core.bdd.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.runners.model.MultipleFailureException;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.steps.TypeStep;
import bddtester.core.bdd.steps.TypeSteps;
import bddtester.core.reporting.ReportElement;
import bddtester.core.throwables.MultipleScenarioWrapperException;
import bddtester.core.throwables.errors.ScenarioError;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.ScenarioException;
import bddtester.core.throwables.exceptions.StepException;

/**
 * Represents a Scenario Outline in the BDD Hierarchy.<br>
 * It is the same as a {@link Scenario} except that it has test data. This
 * means, that the scenario gets executed once with each test data.
 *
 * @author ckeiner
 *
 * @param <T>
 *            The type of the <code>Testdata</code>.
 */
public class ScenarioOutline<T> extends AbstractScenario
{
    private TypeSteps<T> steps;

    /**
     * The steps to always execute after a scenario, even after a failure.
     */
    private TypeSteps<T> postSteps;

    private final List<T> testdata;

    @SafeVarargs
    public ScenarioOutline(final String description, Supplier<TypeSteps<T>> scenarioSupplier,
            Supplier<T>... testdataSupplier)
    {
        super(description);
        setSteps(scenarioSupplier.get());
        this.testdata = new ArrayList<>(testdataSupplier.length);
        for (Supplier<T> supplier : testdataSupplier)
        {
            testdata.add(supplier.get());
        }
    }

    public ScenarioOutline(String description, Supplier<TypeSteps<T>> stepSupplier, List<T> testdata)
    {
        super(description);
        setSteps(stepSupplier.get());
        this.testdata = testdata;
    }

    @Override
    public void addBefores(List<Before> befores)
    {
        getSteps().addBefore(befores);
    }

    @Override
    public void addAfters(List<After> afters)
    {
        getSteps().addAfters(afters);
    }

    /**
     * Executes the scenario outline.<br>
     * Exceptions and errors get caught and the scenario is executed with the rest
     * of the test data. In the end, the exceptions and errors are re-thrown as
     * {@link ScenarioException} and {@link ScenarioError} respectively.<br>
     * Should both, an exception and an error, occur, a FeatureException is thrown.
     */
    @Override
    public void test()
    {
        if (getStatus().contains(Status.IGNORE))
        {
            return;
        }
        // Initialize needed variables
        List<ScenarioException> scenarioExceptions = new ArrayList<>();
        List<ScenarioError> scenarioErrors = new ArrayList<>();
        List<Throwable> postStepFailures = new ArrayList<>();
        System.out.println("================\nScenarioOutline: " + getDescription() + "\n================");

        // For every test data
        for (final T testdatum : this.testdata)
        {
            doSingleTest(testdatum, scenarioExceptions, scenarioErrors, postStepFailures);
        }
        System.out.println("\n\n");
        finishScenario(scenarioExceptions, scenarioErrors, postStepFailures);
    }

    /**
     * Executes a test with a single test datum.
     * 
     * @param testdatum
     *            The test datum.
     * @param scenarioExceptions
     *            The list of {@link ScenarioException}s.
     * @param scenarioErrors
     *            The list of {@link ScenarioError}s.
     * @param postStepFailures
     *            The list of {@link Throwable}s.
     */
    private void doSingleTest(final T testdatum, final List<ScenarioException> scenarioExceptions,
            final List<ScenarioError> scenarioErrors, final List<Throwable> postStepFailures)
    {
        // Set the testdata
        TypeSteps<T> typeSteps = getSteps().withData(testdatum);
        // Tell the reporter the scenario starts
        ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum);

        System.out.println("Using testdata:\n" + testdatum.toString());
        try
        {
            executeScenario(scenarioReporter, typeSteps);
        } catch (StepException e)
        {
            scenarioExceptions.add(scenarioException(testdatum, e, scenarioReporter));
        } catch (StepError e)
        {
            scenarioErrors.add(scenarioError(testdatum, e, scenarioReporter));
        }
        // Always execute the PostStep after the scenario is done
        finally
        {
            // First we execute the postSteps
            Throwable postStepFailure = doPostSteps(testdatum);
            if (postStepFailure != null)
            {
                postStepFailures.add(postStepFailure);
            }
        }
        System.out.println("\n");
    }

    /**
     * Creates and logs a {@link ScenarioError}.
     * 
     * @param testdatum
     *            The used test datum.
     * @param e
     *            The {@link StepError} that occured.
     * @param scenarioReporter
     *            The reporter for reporting.
     * @return The ScenarioError with a proper description and the StepError.
     */
    private ScenarioError scenarioError(T testdatum, StepError e, ReportElement scenarioReporter)
    {
        // Create a ScenarioError
        ScenarioError scenarioError = new ScenarioError(
                "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), e);
        if (scenarioReporter != null)
        {
            // Mark the scenario as failed with the error
            scenarioReporter.fail(scenarioError);
        }
        return scenarioError;
    }

    /**
     * Creates and logs a {@link ScenarioException}
     * 
     * @param testdatum
     *            The used test datum.
     * @param e
     *            The {@link StepException} that occured.
     * @param scenarioReporter
     *            The reporter for reporting.
     * @return The ScenarioException with a proper description and the
     *         StepException.
     */
    private ScenarioException scenarioException(T testdatum, StepException e, ReportElement scenarioReporter)
    {
        // Create a ScenarioError
        ScenarioException scenarioException = new ScenarioException(
                "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), e);
        if (scenarioReporter != null)
        {
            // Mark the scenario as fatal with the exception
            scenarioReporter.fatal(scenarioException);
        }
        return scenarioException;
    }

    @Override
    public void skipScenario()
    {
        for (T testdatum : testdata)
        {
            // Get the step definition
            TypeSteps<T> typeSteps = getSteps();
            // Set the testdata
            typeSteps = typeSteps.withData(testdatum);
            // Set up the report for this element
            ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum, false);
            // Set up the reporter for the bddSteps if none was supplied
            if (typeSteps.getReporter() == null && this.getReporter() != null)
            {
                typeSteps.setReporter(this.getReporter());
            }
            // Skip the steps
            typeSteps.skipSteps();
            scenarioReporter.skip(getDescription());
        }
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Assigns the status as category.
     * 
     * @param typeSteps
     *            The scenario for which to create the reporter.
     * @param testdatum
     *            The test datum for the scenario.
     * @return The ReportElement for the Scenario specified by typeSteps.
     *         <code>null</code> if {@link #getReporter()} returns
     *         <code>null</code>.
     */
    protected ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum)
    {
        return setUpReporter(typeSteps, testdatum, true);
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Also assigns the status as category.
     * 
     * @param typeSteps
     *            The scenario for which to create the reporter.
     * @param testdatum
     *            The test datum for the scenario.
     * @param reportStatus
     *            Whether the status should be reported or not.
     * 
     * @return The ReportElement for the Scenario specified by typeSteps.
     *         <code>null</code> if {@link #getReporter()} returns
     *         <code>null</code>.
     */
    protected ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum, boolean reportStatus)
    {
        ReportElement scenarioReporter = null;
        if (getReporter() != null)
        {
            // // Resolves placeholders in the description
            // String reportDescription = new
            // ParameterResolver<T>().resolvePlaceholders(getDescription(), testdatum);
            // scenarioReporter = getReporter().scenarioOutline(reportDescription);
            // We decided, that for a scenario, the test data should simply be printed,
            // without any placeholders
            scenarioReporter = getReporter().scenarioOutline(this.getDescription(), testdatum);
            if (typeSteps.getReporter() == null)
            {
                typeSteps.setReporter(this.getReporter());
            }
            if (this.getStatus() != null && reportStatus)
            {
                scenarioReporter.assignCategory(getStatus());
            }
        }
        return scenarioReporter;
    }

    /**
     * Executes the supplied steps if the Status is not {@link Status#IGNORE}.<br>
     * If the Status is on ignore, the ReportElement is marked as skipped.
     * 
     * @param scenarioReporter
     *            The ReportElement of the scenario.
     * @param typeSteps
     *            The {@link BddTypeSteps} to execute.
     */
    protected void executeScenario(ReportElement scenarioReporter, TypeSteps<T> typeSteps)
    {
        if (getStatus().contains(Status.SKIP))
        {
            scenarioReporter.skip(getDescription());
        }
        else
        {
            typeSteps.test();
            if (scenarioReporter != null)
            {
                scenarioReporter.pass(getDescription());
            }
        }
    }

    /**
     * Throws {@link ScenarioException} or {@link ScenarioError} if the respective
     * parameter isn't emtpy.<br>
     * If both, an exception and error, occur, a ScenarioException is thrown.
     * 
     * @param scenarioExceptions
     *            The list of scenarioExceptions that happened in the scenario.
     * @param scenarioErrors
     *            The list of scenarioErrors that happened in the scenario.
     */
    private void finishScenario(List<ScenarioException> scenarioExceptions, List<ScenarioError> scenarioErrors,
            List<Throwable> postStepFailures)
    {
        // Then we collect the throwables
        MultipleScenarioWrapperException scenarioWrapperException = new MultipleScenarioWrapperException(
                scenarioExceptions, scenarioErrors);
        // Throw a scenario exception if an exception or exception and error occured
        if (scenarioExceptions != null && !scenarioExceptions.isEmpty())
        {
            throw new ScenarioException(scenarioWrapperException);
        }
        // Throw a scenario error if an error occured
        else if (scenarioErrors != null && !scenarioErrors.isEmpty())
        {
            throw new ScenarioError(scenarioWrapperException);
        }
        if (postStepFailures != null && !postStepFailures.isEmpty())
        {
            MultipleFailureException multipleFailureException = new MultipleFailureException(postStepFailures);
            throw new ScenarioException(multipleFailureException);
        }
    }

    /**
     * Executes postSteps added by {@link #postSteps(Supplier)}.
     */
    protected Throwable doPostSteps(T testdatum)
    {
        Throwable throwable = null;
        if (this.postSteps != null)
        {
            this.postSteps.setReporter(getReporter());
            this.postSteps.withData(testdatum);
            try
            {
                this.postSteps.test();
            } catch (Exception | Error e)
            {
                throwable = e;
            }
        }
        return throwable;
    }

    public List<T> getTestdata()
    {
        return testdata;
    }

    public TypeSteps<T> getSteps()
    {
        return steps;
    }

    public void setSteps(TypeSteps<T> steps)
    {
        this.steps = steps;
    }

    // public ScenarioOutline<T> postSteps(Supplier<Steps> postSteps)
    // {
    // this.postSteps = new TypeSteps<T>().given(postSteps.get());
    // return this;
    // }

    public ScenarioOutline<T> postSteps(Supplier<TypeSteps<T>> postSteps)
    {
        this.postSteps = postSteps.get();
        for (TypeStep<T> step : this.postSteps.getSteps())
        {
            step.setDescription("POSTSTEP: " + getDescription());
        }
        return this;
    }

}
