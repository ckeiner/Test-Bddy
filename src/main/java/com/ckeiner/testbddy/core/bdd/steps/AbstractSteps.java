package com.ckeiner.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.status.Statusable;
import com.ckeiner.testbddy.core.reporting.ReportInterface;
import com.ckeiner.testbddy.core.throwables.errors.StepError;
import com.ckeiner.testbddy.core.throwables.exceptions.StepException;

/**
 * Describes multiple steps.
 * 
 * @param <T>
 *            The type of the steps.
 * @author ckeiner
 */
public abstract class AbstractSteps<T extends AbstractStep<?>> implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * A list of all {@link Step}s.
     */
    private final List<T> steps;

    /**
     * Creates an AbstractSteps with an empty list.
     */
    public AbstractSteps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates AbstractSteps with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of steps that specify the behavior.
     */
    public AbstractSteps(final List<T> steps)
    {
        this.steps = steps;
    }

    /**
     * Skips the specified step.
     * 
     * @param step
     *            The step to skip.
     */
    protected abstract void skipStep(T step);

    /**
     * Executes the specified step.
     * 
     * @param step
     *            The step to execute.
     */
    protected abstract void executeStep(T step);

    /**
     * Executes all {@link Step}s.<br>
     * If an exception or error occurs in any step, the steps which weren't executed
     * yet, are set to be skipped and the throwable is re-thrown.<br>
     * Should both, an exception and an error, occur, a StepException is thrown.
     * 
     * @throws StepException
     *             If a StepException occurred in any Step.
     * @throws StepError
     *             If an StepError and no StepExceptions occurred in any Step.
     */
    public void test()
    {
        StepException stepException = null;
        StepError stepError = null;

        // Execute each step
        for (final T step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            try
            {
                // If an exception or error occured in a previous step, skip this step
                if (stepException != null || stepError != null)
                {
                    skipStep(step);
                }
                // Otherwise test it
                else
                {
                    executeStep(step);
                }
            } catch (StepException e)
            {
                stepException = e;
            } catch (StepError e)
            {
                stepError = e;
            }
        }

        if (stepException != null)
        {
            throw stepException;
        }
        else if (stepError != null)
        {
            throw stepError;
        }
    }

    /**
     * Doesn't execute the behavior, but shows them in the report as skipped.
     */
    public void skipSteps()
    {
        for (final T step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            skipStep(step);
        }
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public List<T> getSteps()
    {
        return steps;
    }

    // TODO continue here
    /**
     * Adds all steps of the specified parameter to the steps.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> and(final Steps scenario);

    /**
     * Adds the step, specified by the description and runner, to the list of
     * {@link #steps}.<br>
     * The GherkinKeyword for the step is "And".
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current {@link AbstractSteps}.
     */
    public abstract AbstractSteps<T> and(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified parameter to {@link #steps}.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> given(final Steps scenario);

    /**
     * Adds the Step, specified by the description and runner, to the list of
     * BddSteps.<br>
     * The GherkinKeyword for the Step is "Given".
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current Scenario.
     */
    public abstract AbstractSteps<T> given(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param scenario
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public abstract AbstractSteps<T> then(final Steps scenario);

    /**
     * Adds the BddStep, specified by the description and runner, to the list of
     * BddSteps.<br>
     * The GherkinKeyword for the BddStep is "Then".
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current Scenario.
     */
    public abstract AbstractSteps<T> then(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param scenario
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public abstract AbstractSteps<T> when(final Steps scenario);

    /**
     * Adds the BddStep, specified by the description and runner, to the list of
     * BddSteps.<br>
     * The GherkinKeyword for the BddStep is "When".
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current Scenario.
     */
    public abstract AbstractSteps<T> when(final String description, final Runnable runner);

    @Override
    public abstract AbstractSteps<T> ignore();

    @Override
    public abstract AbstractSteps<T> wip();

    @Override
    public abstract AbstractSteps<T> skip();

}
