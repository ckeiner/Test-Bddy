package com.ckeiner.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.status.Statusable;
import com.ckeiner.testbddy.core.bdd.steps.typed.GivenSteps;
import com.ckeiner.testbddy.core.bdd.steps.typed.ThenSteps;
import com.ckeiner.testbddy.core.bdd.steps.typed.WhenSteps;
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
     * Creates AbstractSteps with the lists initialized as <code>ArrayList</code>.
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
     * Executes the specified step.
     * 
     * @param step
     *            The step to execute.
     */
    protected abstract void testStep(T step);

    /**
     * Skips the specified step.
     * 
     * @param step
     *            The step to skip.
     */
    protected abstract void skipStep(T step);

    /**
     * Executes the {@link Step}s.<br>
     * If an exception or error occurs in the steps, the steps which weren't
     * executed yet, are set to be skipped and the throwable is re-thrown.<br>
     * Should both, an exception and an error, occur, a StepException is thrown.
     * 
     * @throws StepException
     *             If a StepException occured in any Step.
     * @throws StepError
     *             If an StepError and no StepExceptions occured in any Step.
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
                    testStep(step);
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
     * @param givenSteps
     *            The GivenSteps with the steps that should be added to the steps of
     *            this class.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> given(final GivenSteps givenSteps);

    /**
     * Adds the BddStep, specified by the description and runner, to the list of
     * BddSteps.<br>
     * The GherkinKeyword for the BddStep is "Given".
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
     * @param thenSteps
     *            The ThenSteps which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public abstract AbstractSteps<T> then(final ThenSteps thenSteps);

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
     * @param whenSteps
     *            The WhenSteps which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public abstract AbstractSteps<T> when(final WhenSteps whenSteps);

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
