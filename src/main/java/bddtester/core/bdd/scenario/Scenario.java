package bddtester.core.bdd.scenario;

import java.util.List;
import java.util.function.Supplier;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.steps.Step;
import bddtester.core.bdd.steps.Steps;
import bddtester.core.reporting.ReportElement;
import bddtester.core.throwables.errors.ScenarioError;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.ScenarioException;
import bddtester.core.throwables.exceptions.StepException;

/**
 * Represents a scenario in the BDD Hierarchy.
 * 
 * @author ckeiner
 *
 */
public class Scenario extends AbstractScenario
{
    /**
     * The steps to execute.
     */
    final private Steps steps;

    /**
     * The steps to always execute after a scenario, even after a failure.
     */
    private Steps postSteps;

    public Scenario(final String description, final Steps steps)
    {
        this(description, () -> steps);
    }

    public Scenario(final String description, final Supplier<Steps> stepsSupplier)
    {
        super(description);
        this.steps = stepsSupplier.get();
    }

    /**
     * Executes the scenario.<br>
     * Every exception and error is re-thrown as {@link ScenarioException} and
     * {@link ScenarioError} respectively.
     */
    @Override
    public void test()
    {
        Throwable throwable;
        if (getStatus().contains(Status.IGNORE))
        {
            return;
        }
        // Set up the report for this element
        ReportElement scenarioReporter = setUpReporter();
        // Set up the reporter for the bddSteps if none was supplied
        if (steps.getReporter() == null && this.getReporter() != null)
        {
            steps.setReporter(this.getReporter());
        }
        // Print some information to the console
        System.out.println("================\nScenario: " + getDescription() + "\n================");
        // Execute the steps for the scenario and catch every exception and error.
        try
        {
            executeScenario(scenarioReporter);
        } catch (StepException e)
        {
            if (scenarioReporter != null)
            {
                // Logs the scenario as fatal
                scenarioReporter.fatal(e);
            }
            // Throws the scenario exception
            throw new ScenarioException("Scenario \"" + getDescription() + "\" failed.", e);
        } catch (StepError e)
        {
            if (scenarioReporter != null)
            {
                // Logs the scenario as failed
                scenarioReporter.fail(e);
            }
            // Throws the scenario error
            throw new ScenarioError("Scenario \"" + getDescription() + "\" failed.", e);
        } finally
        {
            throwable = doPostSteps();
        }
        System.out.println("\n\n");
        // If we are here, then there was some failure in the poststeps, but not in the
        // main scenario
        if (throwable != null)
        {
            throw new ScenarioError("PostStep of Scenario \"" + getDescription() + "\" failed.", throwable);
        }
    }

    /**
     * Executes postSteps added by {@link #postSteps(Supplier)}.
     */
    protected Throwable doPostSteps()
    {
        Throwable throwable = null;
        if (this.postSteps != null)
        {
            this.postSteps.setReporter(getReporter());
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

    @Override
    public void skipScenario()
    {
        // Set up the report for this element
        ReportElement scenarioReporter = setUpReporter(false);
        // Set up the reporter for the bddSteps if none was supplied
        if (steps.getReporter() == null && this.getReporter() != null)
        {
            steps.setReporter(this.getReporter());
        }
        // Skip the steps
        steps.skipSteps();
        scenarioReporter.skip(getDescription());
    }

    /**
     * Executes the steps if the Status is not {@link Status#IGNORE}.<br>
     * If the Status is on ignore, the ReportElement is marked as skipped.
     * 
     * @param scenarioReporter
     *            The ReportElement of the scenario.
     */
    private void executeScenario(ReportElement scenarioReporter)
    {
        if (getStatus().contains(Status.SKIP))
        {
            steps.skipSteps();
            scenarioReporter.skip(getDescription());
        }
        else
        {
            steps.test();
            if (scenarioReporter != null)
            {
                scenarioReporter.pass(getDescription());
            }
        }
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Also assigns the status as category.
     * 
     * @return {@link ReportElement} if a reporter was set, else <code>null</code>.
     */
    private ReportElement setUpReporter()
    {
        return setUpReporter(true);
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Also assigns the status as category.
     * 
     * @param reportStatus
     *            Whether the status should be reported or not.
     * 
     * @return {@link ReportElement} if a reporter was set, else <code>null</code>.
     */
    private ReportElement setUpReporter(boolean reportStatus)
    {
        ReportElement scenarioReporter = null;
        // If a reporter was defined
        if (getReporter() != null)
        {
            // Create a ReportElement for the scenario
            scenarioReporter = getReporter().scenario(getDescription());
            // Assign category if there is one
            if (getStatus() != null && reportStatus)
            {
                scenarioReporter.assignCategory(getStatus());
            }
        }
        // Return scenarioReporter
        return scenarioReporter;
    }

    @Override
    public void addBefores(List<Before> befores)
    {
        steps.addBefores(befores);
    }

    @Override
    public void addAfters(List<After> afters)
    {
        steps.addAfters(afters);
    }

    public Scenario postSteps(Supplier<Steps> postSteps)
    {
        this.postSteps = postSteps.get();
        for (Step step : this.postSteps.getSteps())
        {
            step.setDescription("POSTSTEP: " + getDescription());
        }
        return this;
    }

}
