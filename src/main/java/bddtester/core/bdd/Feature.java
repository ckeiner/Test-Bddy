package bddtester.core.bdd;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.scenario.AbstractScenario;
import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.MultipleScenarioWrapperException;
import bddtester.core.throwables.errors.FeatureError;
import bddtester.core.throwables.errors.ScenarioError;
import bddtester.core.throwables.exceptions.FeatureException;
import bddtester.core.throwables.exceptions.ScenarioException;

/**
 * Represents a feature in the BDD Hierarchy.<br>
 * Therefore, this is the entry point for every test.
 *
 * @author ckeiner
 */
public class Feature implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    public static ReportInterface reporter;

    /**
     * The steps to execute before executing a single scenario
     */
    private final List<Before> befores;

    /**
     * The steps to execute after executing a single scenario
     */
    private final List<After> afters;

    /**
     * The description of the feature.
     */
    private final String description;

    /**
     * The list of scenarios to execute.
     */
    private final List<AbstractScenario> scenarios;

    /**
     * The status of the feature
     */
    private final Set<Status> status;

    /**
     * Creates a Feature with the specified description, scenarios, backgrounds and
     * post steps.
     *
     * @param description
     *            String that describes the Feature.
     * @param befores
     *            The list of {@link Before}s to execute before each scenario.
     * @param afters
     *            The list of {@link After}s to execute after each scenario.
     * @param scenarios
     *            List of {@link AbstractScenario}s that the feature should execute.
     */
    public Feature(final String description, final List<Before> befores, final List<After> afters,
            final List<AbstractScenario> scenarios)
    {
        this.befores = befores;
        this.afters = afters;
        this.description = description;
        this.scenarios = scenarios;
        status = new LinkedHashSet<Status>();
    }

    /**
     * Executes the feature.<br>
     * Every exception and error is bundled and thrown in a {@link FeatureException}
     * or {@link FeatureError} respectively.<br>
     * Should both, an exception and an error, occur, a FeatureException is thrown.
     */
    public void test()
    {
        // If we ignore the feature, we neither want to execute nor log it
        if (getStatus().contains(Status.IGNORE))
        {
            return;
        }

        // Set up list of exceptions and error
        final List<ScenarioException> scenarioExceptions = new ArrayList<ScenarioException>();
        final List<ScenarioError> scenarioErrors = new ArrayList<ScenarioError>();

        // Set up reporting
        final ReportElement featureReport = setUpReporter();
        // Print some information to the console
        String logging = " Feature: " + description;
        if (getStatus() != null)
        {
            logging = getStatus().toString() + logging;
        }
        System.out.println(logging);

        // For each scenario
        for (final AbstractScenario scenario : getScenarios())
        {
            // Set the reporter of the scenario if the feature has one
            if (scenario.getReporter() == null && reporter != null)
            {
                scenario.setReporter(reporter);
            }
            // Add the backgrounds to the scenario
            if (befores != null && !befores.isEmpty())
            {
                scenario.addBefores(befores);
            }

            // Add the postSteps to the scenario
            if (afters != null && !afters.isEmpty())
            {
                scenario.addAfters(afters);
            }
            // Execute the scenario and catch all exceptions and errors
            try
            {
                // Execute the feature
                executeScenario(scenario);
            } catch (final ScenarioException e)
            {
                scenarioExceptions.add(e);
            } catch (final ScenarioError e)
            {
                scenarioErrors.add(e);
            }
        }

        // Finish the test with proper reporting, and exception, error throwing
        finishTest(featureReport, scenarioExceptions, scenarioErrors);
    }

    /**
     * Executes the specified scenario.
     * 
     * @param scenario
     *            The {@link AbstractScenario} that should be executed.
     */
    public void executeScenario(AbstractScenario scenario)
    {
        if (getStatus().contains(Status.SKIP))
        {
            scenario.skipScenario();
        }
        else
        {
            scenario.test();
        }
    }

    /**
     * Creates a {@link ReportElement} for the feature if a reporter is set.<br>
     * Also assigns the status as category.
     *
     * @return {@link ReportElement} if a reporter was set, else <code>null</code>.
     */
    private ReportElement setUpReporter()
    {
        // Define ReportElement featureReport
        ReportElement featureReport = null;
        // If a reporter exists
        if (reporter != null)
        {
            // Describe the feature
            featureReport = reporter.feature(description);
            // If the status isn't null
            if (getStatus() != null)
            {
                // Assign the Status as category
                featureReport.assignCategory(getStatus());
            }
        }
        // Return the featureReport
        return featureReport;
    }

    /**
     * Handles the test report and bundles any occurred exceptions and errors, and
     * re-throws them in a {@link FeatureException} or {@link FeatureError}.
     *
     * @param featureReport
     *            The {@link ReportElement} generated by the test.
     * @param scenarioExceptions
     *            The {@link ScenarioException}s thrown.
     * @param scenarioErrors
     *            The {@link ScenarioError}s thrown.
     */
    private void finishTest(final ReportElement featureReport, final List<ScenarioException> scenarioExceptions,
            final List<ScenarioError> scenarioErrors)
    {
        // Find out if an exception and/or error occured
        final boolean exception = scenarioExceptions != null && !scenarioExceptions.isEmpty();
        final boolean error = scenarioErrors != null && !scenarioErrors.isEmpty();

        // If an exception or error happened
        if (exception || error)
        {
            // Gather all errors and exceptions
            final MultipleScenarioWrapperException scenarioWrapperException = new MultipleScenarioWrapperException(
                    scenarioExceptions, scenarioErrors);
            // Print the stack trace
            scenarioWrapperException.printStackTrace();

            // If an exception happened
            if (exception)
            {
                // Report the feature as fatal
                if (featureReport != null)
                {
                    featureReport.fatal(scenarioWrapperException);
                }
                // Throw the FeatureException with the wrapper for all exceptions and errors
                throw new FeatureException("Feature \"" + description + "\" failed.", scenarioWrapperException);
            }
            // If no exception but an error happened
            else if (error)
            {
                if (featureReport != null)
                {
                    // Report the feature as failed
                    featureReport.fail(scenarioWrapperException);
                }
                // Throw the FeatureError with the wrapper for all exceptions and errors
                throw new FeatureError("Feature \"" + description + "\" failed.", scenarioWrapperException);
            }
        }
        // If no errors occured and a featureReport exists
        else if (featureReport != null)
        {
            // If the Status contains SKIP, then report the feature as skipped.
            if (getStatus().contains(Status.SKIP))
            {
                featureReport.skip(description);
            }
            // If the Status doesn't contain skipped, then report the feature as passed.
            else
            {
                featureReport.pass(description);
            }
        }
    }

    public String getDescription()
    {
        return description;
    }

    /**
     * Returns all scenarios.
     *
     * @return Returns all {@link AbstractScenario} of this feature.
     */
    public List<AbstractScenario> getScenarios()
    {
        return scenarios;
    }

    public Set<Status> getStatus()
    {
        return status;
    }

    @Override
    public Feature ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public Feature wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    @Override
    public Feature skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

    public Feature withReporter(ReportInterface reporter)
    {
        Feature.reporter = reporter;
        return this;
    }
}
