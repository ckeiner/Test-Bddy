package com.ckeiner.testbddy.core.bdd.steps;

import java.util.LinkedHashSet;
import java.util.Set;

import com.aventstack.extentreports.GherkinKeyword;
import com.ckeiner.testbddy.core.bdd.status.PendingConsumer;
import com.ckeiner.testbddy.core.bdd.status.PendingRunnable;
import com.ckeiner.testbddy.core.bdd.status.Status;
import com.ckeiner.testbddy.core.bdd.status.Statusable;
import com.ckeiner.testbddy.core.reporting.ReportElement;
import com.ckeiner.testbddy.core.reporting.ReportInterface;
import com.ckeiner.testbddy.core.throwables.errors.StepError;
import com.ckeiner.testbddy.core.throwables.exceptions.StepException;

/**
 * Describes a step in the BDD Hierarchy.<br>
 * That means, it contains the actual behavior.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the behavior.
 */
public abstract class AbstractStep<T> implements Statusable
{
    /**
     * Shows the status of the step
     */
    private final Set<Status> status;

    /**
     * The object responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * Defines the {@link GherkinKeyword}.
     */
    private final GherkinKeyword keyword;

    /**
     * Describes the step in natural language.
     */
    private String description;

    /**
     * Contains the behavior of the step.
     */
    private final T behavior;

    /**
     * Creates an AbstractStep with the specified keyword, description and behavior.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} describing whether its a given, when,
     *            then or and step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            Contains the actual execution.
     */
    public AbstractStep(final GherkinKeyword keyword, final String description, final T behavior)
    {
        this.keyword = keyword;
        this.description = description;
        this.behavior = behavior;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * The method called when the step should be executed.<br>
     * If {@link #reporter} was specified, it first prepares the report. Then, it
     * executes the behavior. <br>
     * If {@link #skip} is set, the behavior isn't execute and the report marks the
     * step as skipped.
     * 
     * @throws StepException
     *             If an Exception occurs. The report shows the step as fatal.
     * @throws StepError
     *             If an Error occurs. The report shows the step as failed.
     * @return boolean which describes whether the next step should be executed or
     *         not
     */
    public boolean test()
    {
        boolean executeNextStep = true;
        if (canAndShouldExecuteStep())
        {
            // Create the ReportElement
            ReportElement stepReporter = setUpReporter();
            // Print the description of the step
            System.out.println(getDescription());
            try
            {
                // If it shouldn't be skipped
                if (!getStatus().contains(Status.SKIP))
                {
                    // Execute it
                    executeStep();
                    // Mark the node as passed if it exists
                    if (stepReporter != null)
                    {
                        stepReporter.pass(getDescription());
                    }
                }
                // If the step should be skipped
                else
                {
                    // Mark the node as skipped
                    if (stepReporter != null)
                    {
                        stepReporter.skip(getDescription());
                    }
                }
            } catch (Exception e)
            {
                // Mark the node as fatal
                if (stepReporter != null)
                {
                    stepReporter.fatal(e);
                }
                // Throw an Exception
                throw new StepException(e);
            } catch (Error e)
            {
                // Mark the node as failed
                if (stepReporter != null)
                {
                    stepReporter.fail(e);
                }
                // Throw an Error
                throw new StepError(e);
            }
        }
        // If the status does not contain Ignore but either pending or skip, do not
        // execute the next step
        else if (getStatus().contains(Status.IGNORE)
                && (getStatus().contains(Status.PENDING) || getStatus().contains(Status.SKIP)))
        {
            executeNextStep = false;
        }
        return executeNextStep;
    }

    protected boolean canAndShouldExecuteStep()
    {
        boolean executeStep = true;
        // If the status contains the Ignore-Status, simply don't show
        if (getStatus().contains(Status.IGNORE))
        {
            executeStep = false;
        }

        if (behavior == null)
        {
            throw new IllegalStateException("Null behavior found");
        }

        // If the behavior is either a pending runnable or pending consumer
        if (behavior instanceof PendingRunnable || behavior instanceof PendingConsumer<?>)
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement stepReporter = setUpReporter();
            if (stepReporter != null)
            {
                // Set pending for the reporter
                stepReporter.pending("No behavior was defined");
            }
            // End execution of feature
            executeStep = false;
        }
        return executeStep;
    }

    /**
     * Skips the step
     */
    public void skipStep()
    {
        // Create the ReportElement
        ReportElement element = setUpReporter(true);
        // Mark the node as skipped
        if (element != null)
        {
            element.skip(description);
        }
    }

    /**
     * Defines how a single step is executed.
     */
    protected abstract void executeStep();

    /**
     * Sets the reporter up and shows the {@link Status} defined in {@link #status}.
     * 
     * @return The {@link ReportElement} representing the step.
     */
    protected ReportElement setUpReporter()
    {
        return setUpReporter(true);
    }

    /**
     * Sets the reporter up.
     * 
     * @param showStatus
     *            Whether the status should be shown in the report.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean showStatus)
    {
        return setUpReporter(showStatus, getDescription());
    }

    /**
     * Sets the reporter up with a custom description.
     * 
     * @param showStatus
     *            Whether the status should be shown in the report.
     * @param description
     *            The description of the {@link ReportElement}.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean showStatus, String description)
    {
        ReportElement element = null;
        if (reporter != null)
        {
            element = reporter.step(keyword, description);
            if (getStatus() != null && showStatus)
            {
                // Assign the status as category
                element.assignCategory(getStatus());
            }
        }
        return element;
    }

    protected void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public T getBehavior()
    {
        return behavior;
    }

    public GherkinKeyword getKeyword()
    {
        return keyword;
    }

    public ReportInterface getReporter()
    {
        return this.reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public Set<Status> getStatus()
    {
        return status;
    }

    @Override
    public AbstractStep<T> ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public AbstractStep<T> wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    @Override
    public AbstractStep<T> skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

}
