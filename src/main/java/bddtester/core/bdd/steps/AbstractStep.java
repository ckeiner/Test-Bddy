package bddtester.core.bdd.steps;

import java.util.LinkedHashSet;
import java.util.Set;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.status.Status;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.StepException;

public abstract class AbstractStep<T> implements Statusable
{
    /**
     * Shows the status of the step
     */
    private final Set<Status> status;

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

    public AbstractStep(final GherkinKeyword keyword, final String description, final T behavior)
    {
        this.keyword = keyword;
        this.description = description;
        this.behavior = behavior;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * The method called when the step should be executed.<br>
     * First it creates a node via
     * {@link ExtentTest#createNode(GherkinKeyword, String)}. Then, it executes the
     * runner. <br>
     * If an error or exception occurs, it throws them as either {@link StepError}
     * or {@link StepException} and marks the node as failed of fatal
     * respectively.<br>
     * If {@link #skip} is set, the consumer isn't execute. However, the node is
     * still created but marked as skipped.
     */
    public void test()
    {
        if (getStatus().contains(Status.IGNORE))
        {
            return;
        }
        ReportElement element = setUpReporter();
        // Print the description of the step
        System.out.println(description);
        try
        {
            if (!getStatus().contains(Status.SKIP))
            {
                executeStep();
                // Mark the node as passed
                if (element != null)
                {
                    element.pass(description);
                }
            }
            else
            {
                if (element != null)
                {
                    element.skip(getDescription());
                }
            }
        } catch (Exception e)
        {
            // Mark the node as fatal
            if (element != null)
            {
                element.fatal(e);
            }
            // Throw an Exception
            throw new StepException(e);
        } catch (Error e)
        {
            // Mark the node as failed
            if (element != null)
            {
                element.fail(e);
            }
            // Throw an Error
            throw new StepError(e);
        }
    }

    /**
     * Skips the step
     */
    public void skipStep()
    {
        ReportElement element = setUpReporter(true);
        // Mark the node as skipped
        if (element != null)
        {
            element.skip(description);
        }
    }

    /**
     * Defines how the step is executed.
     */
    protected abstract void executeStep();

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
