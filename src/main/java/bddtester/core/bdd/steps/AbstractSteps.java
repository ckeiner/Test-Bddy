package bddtester.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.StepException;

public abstract class AbstractSteps<T extends AbstractStep<?>> implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * The steps that happen before all other steps
     */
    private final List<Before> befores;

    /**
     * The steps that happen after all other steps
     */
    private final List<After> afters;

    /**
     * A list of all {@link Step}s
     */
    private final List<T> steps;

    /**
     * Creates a BddScenario.
     */
    public AbstractSteps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public AbstractSteps(final List<T> steps)
    {
        this(steps, new ArrayList<Before>());
    }

    /**
     * Creates a BddScenario with the specified reporter and list of {@link Step}s,
     * {@link Before}s and {@link After}s.
     * 
     * @param befores
     *            The list of steps to execute before each other step.
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public AbstractSteps(final List<T> steps, final List<Before> befores)
    {
        this(steps, befores, new ArrayList<After>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s,
     * {@link Before}s and {@link After}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     * @param befores
     *            The list of steps to execute before the steps.
     * @param afters
     *            The list of steps to execute after the steps.
     */
    public AbstractSteps(final List<T> steps, final List<Before> befores, final List<After> afters)
    {
        this.steps = steps;
        this.befores = befores;
        this.afters = afters;
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
     * Executes the scenario.<br>
     * If an exception or error occurs in the steps, the steps which weren't
     * executed yet, are set to be skipped. Afterwards, the exception or error is
     * re-thrown as {@link StepException} and {@link StepError} respectively.<br>
     * Should both, an exception and an error, occur, a StepException is thrown.
     */
    public void test()
    {
        StepException stepException = null;
        StepError stepError = null;
        // TODO beautify
        try
        {
            executeBeforeSteps(false);
        } catch (StepException e)
        {
            stepException = e;
        } catch (StepError e)
        {
            stepError = e;
        }

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
            executeAfterSteps(true);
            throw stepException;
        }
        else if (stepError != null)
        {
            executeAfterSteps(true);
            throw stepError;
        }

        // If we reached this point, we want to throw the StepException or StepError
        executeAfterSteps(false);
    }

    public void skipSteps()
    {
        executeBeforeSteps(true);
        for (final T step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            skipStep(step);
        }
        executeAfterSteps(true);
    }

    protected void executeBeforeSteps(boolean skip)
    {
        for (Before before : befores)
        {
            Steps steps = before.getSteps();
            steps.setReporter(this.reporter);
            if (skip)
            {
                steps.skipSteps();
            }
            else
            {
                steps.test();
            }
        }
    }

    protected void executeAfterSteps(boolean skip)
    {
        for (After after : afters)
        {
            Steps steps = after.getSteps();
            steps.setReporter(this.reporter);
            if (skip)
            {
                steps.skipSteps();
            }
            else
            {
                steps.test();
            }
        }
    }

    /**
     * Adds all backgrounds to the Steps.
     * 
     * @param befores
     *            The {@link Before}s to add.
     */
    public void addBefores(List<Before> befores)
    {
        this.befores.addAll(befores);
    }

    /**
     * Adds all postSteps to the Steps.
     * 
     * @param afters
     *            The {@link After}s to add.
     */
    public void addAfters(List<After> afters)
    {
        this.afters.addAll(afters);
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
     * Adds the BddStep, specified by the description and runner, to the list of
     * BddSteps.<br>
     * The GherkinKeyword for the BddStep is "And".
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> and(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified parameter to the steps of this Steps.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> given(final Steps scenario);

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
