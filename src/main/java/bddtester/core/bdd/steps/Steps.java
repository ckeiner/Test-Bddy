package bddtester.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.StepException;

/**
 * Describes a BDD Scenario.<br>
 * Thus, it has the given, when, then, and methods.
 * 
 * @author ckeiner
 *
 */
public class Steps implements Statusable
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
    private final List<Step> steps;

    /**
     * Creates a BddScenario.
     */
    public Steps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public Steps(final List<Step> steps)
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
    public Steps(final List<Step> steps, final List<Before> befores)
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
    public Steps(final List<Step> steps, final List<Before> befores, final List<After> afters)
    {
        this.steps = steps;
        this.befores = befores;
        this.afters = afters;
    }

    /**
     * Executes the BddScenario by executing each step in {@link #getSteps()}.<br>
     * 
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
        for (final Step step : steps)
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
                    step.skipStep();
                }
                // Otherwise test it
                else
                {
                    step.test();
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
        // TODO beautify
        try
        {
            executeAfterSteps(false);
        } catch (StepException e)
        {
            stepException = e;
        } catch (StepError e)
        {
            stepError = e;
        }
    }

    /**
     * Marks all steps as skipped.
     */
    public void skipSteps()
    {
        executeBeforeSteps(true);
        for (Step step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            step.skipStep();
        }
        executeAfterSteps(true);
    }

    private void executeBeforeSteps(boolean skip)
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

    private void executeAfterSteps(boolean skip)
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

    /**
     * Adds a step to the list of BddSteps.
     * 
     * @param keyword
     *            The String, that describes the {@link GherkinKeyword}.
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of the step.
     */
    private void addStep(String keyword, String description, Runnable runner)
    {
        try
        {
            this.steps.add(new Step(new GherkinKeyword(keyword), description, runner));
        } catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Unknown Keyword " + keyword, e);
        }
    }

    /**
     * Adds a step to the list of {@link Step}s.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of the step.
     */
    private void addStep(GherkinKeyword keyword, String description, Runnable runner)
    {
        this.steps.add(new Step(keyword, description, runner));
    }

    /**
     * Adds a step to the list of {@link Step}s.
     * 
     * @param index
     *            Where you want to add the new Step.
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of the step.
     */
    private void addStep(int index, GherkinKeyword keyword, String description, Runnable runner)
    {
        this.steps.add(index, new Step(keyword, description, runner));
    }

    /**
     * Adds all steps of the specified parameter to the steps of this class.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     */
    private void addAllSteps(Steps scenario)
    {
        for (Step step : scenario.steps)
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    /**
     * Adds all steps of the specified parameter to the steps.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    public Steps and(final Steps scenario)
    {
        addAllSteps(scenario);
        return this;
    }

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
    public Steps and(final String description, final Runnable runner)
    {
        addStep("And", description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified parameter to the steps of this Steps.
     * 
     * @param scenario
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    public Steps given(final Steps scenario)
    {
        addAllSteps(scenario);
        return this;
    }

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
    public Steps given(final String description, final Runnable runner)
    {
        addStep("Given", description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param scenario
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public Steps then(final Steps scenario)
    {
        addAllSteps(scenario);
        return this;
    }

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
    public Steps then(final String description, final Runnable runner)
    {
        addStep("Then", description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param scenario
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     * @return The current BddScenario.
     */
    public Steps when(final Steps scenario)
    {
        addAllSteps(scenario);
        return this;
    }

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
    public Steps when(final String description, final Runnable runner)
    {
        addStep("When", description, runner);
        return this;
    }

    public List<Step> getSteps()
    {
        return steps;
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    @Override
    public Steps ignore()
    {
        getSteps().get(getSteps().size() - 1).ignore();
        return this;
    }

    @Override
    public Steps wip()
    {
        getSteps().get(getSteps().size() - 1).wip();
        return this;
    }

    @Override
    public Steps skip()
    {
        getSteps().get(getSteps().size() - 1).skip();
        return this;
    }

}
