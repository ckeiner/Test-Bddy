package bddtester.core.bdd.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import bddtester.core.bdd.background.Background;
import bddtester.core.bdd.background.PostStep;
import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.steps.TypeSteps;
import bddtester.core.reporting.ReportElement;
import bddtester.core.throwables.MultipleScenarioWrapperException;
import bddtester.core.throwables.errors.ScenarioError;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.ScenarioException;
import bddtester.core.throwables.exceptions.StepException;
import bddtester.core.util.ParameterResolver;

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
    public void addBackgrounds(List<Background> backgrounds)
    {
        getSteps().addBackground(backgrounds);
    }

    @Override
    public void addPostSteps(List<PostStep> postSteps)
    {
        getSteps().addPostSteps(postSteps);
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
        System.out.println("================\nScenarioOutline: " + getDescription() + "\n================");

        // For every test data
        for (final T testdatum : this.testdata)
        {
            // Get the step definition
            TypeSteps<T> typeSteps = getSteps();
            // Set the testdata
            typeSteps = typeSteps.withData(testdatum);

            // Tell the reporter the scenario starts
            ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum);

            System.out.println("Using testdata:\n" + testdatum.toString());
            try
            {
                executeScenario(scenarioReporter, typeSteps);
            } catch (StepException e)
            {
                // Create a ScenarioError
                ScenarioException scenarioException = new ScenarioException(
                        "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), e);
                // Save the exception
                scenarioExceptions.add(scenarioException);
                if (scenarioReporter != null)
                {
                    // Mark the scenario as fatal with the exception
                    scenarioReporter.fatal(scenarioException);
                }
            } catch (StepError e)
            {
                // Create a ScenarioError
                ScenarioError scenarioError = new ScenarioError(
                        "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), e);
                // Save the error
                scenarioErrors.add(scenarioError);
                if (scenarioReporter != null)
                {
                    // Mark the scenario as failed with the error
                    scenarioReporter.fail(scenarioError);
                }
            }
            System.out.println("\n");
        }

        System.out.println("\n\n");
        finishScenario(scenarioExceptions, scenarioErrors);
        // }
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
    private ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum)
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
    private ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum, boolean reportStatus)
    {
        ReportElement scenarioReporter = null;
        if (getReporter() != null)
        {
            String reportDescription = new ParameterResolver<T>().resolvePlaceholders(getDescription(), testdatum);
            // scenarioReporter = getReporter().scenarioOutline(this.getDescription(),
            // testdatum);
            scenarioReporter = getReporter().scenarioOutline(reportDescription);
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
    private void executeScenario(ReportElement scenarioReporter, TypeSteps<T> typeSteps)
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
    public void finishScenario(List<ScenarioException> scenarioExceptions, List<ScenarioError> scenarioErrors)
    {
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

}
