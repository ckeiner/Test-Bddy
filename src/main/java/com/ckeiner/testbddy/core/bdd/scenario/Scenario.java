package com.ckeiner.testbddy.core.bdd.scenario;

import java.util.function.Supplier;

import com.ckeiner.testbddy.core.bdd.status.Status;
import com.ckeiner.testbddy.core.bdd.steps.Steps;
import com.ckeiner.testbddy.core.reporting.ReportElement;
import com.ckeiner.testbddy.core.throwables.errors.ScenarioError;
import com.ckeiner.testbddy.core.throwables.errors.StepError;
import com.ckeiner.testbddy.core.throwables.exceptions.ScenarioException;
import com.ckeiner.testbddy.core.throwables.exceptions.StepException;

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

    public Scenario(final String description)
    {
        super(description);
        // TODO this doesn't make sense but there is no other way to get pending here
        this.steps = new Steps();
        this.getStatus().add(Status.PENDING);
    }

    public Scenario(final String description, final Steps steps)
    {
        super(description);
        this.steps = steps;
    }

    public Scenario(final String description, final Supplier<Steps> stepsSupplier)
    {
        this(description, stepsSupplier.get());
    }

    /**
     * Executes the scenario.<br>
     * Every exception and error is re-thrown as {@link ScenarioException} and
     * {@link ScenarioError} respectively.
     */
    @Override
    public void test()
    {
        // If the scenario should be executed
        if (canAndShouldExecuteScenario())
        {
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
            }
            System.out.println("\n\n");
        }
    }

    protected boolean canAndShouldExecuteScenario()
    {
        boolean executeScenario = true;
        if (getStatus().contains(Status.IGNORE))
        {
            executeScenario = false;
        }

        if (getSteps() == null)
        {
            throw new IllegalStateException("Null steps found");
        }

        // If there are no steps, then the scenario is pending
        if (getSteps().getSteps().isEmpty())
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement scenarioReporter = setUpReporter();
            if (scenarioReporter != null)
            {// Set pending for the reporter
                scenarioReporter.pending("No steps were defined");
            }
            // End execution of feature
            executeScenario = false;
        }

        return executeScenario;
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
        if (scenarioReporter != null)
        {
            scenarioReporter.skip(getDescription());
        }
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
            if (scenarioReporter != null)
            {
                scenarioReporter.skip(getDescription());
            }
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

    public Steps getSteps()
    {
        return steps;
    }

    @Override
    public Scenario ignore()
    {
        super.ignore();
        return this;
    }

    @Override
    public Scenario wip()
    {
        super.wip();
        return this;
    }

    @Override
    public Scenario skip()
    {
        super.skip();
        return this;
    }

}
