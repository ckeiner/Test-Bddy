package bddtester.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.beforeAfter.After;
import bddtester.core.bdd.beforeAfter.Afters;
import bddtester.core.bdd.beforeAfter.Before;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.StepException;

/**
 * Describes a scenario with only one data set.<br>
 * Thus, it has the given, when, then, and methods.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the Testdata.
 */
public class TypeSteps<T> implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * The steps that happen before all other steps
     */
    // TODO make this a stream so we only read once
    private final List<Before> befores;

    /**
     * The steps that happen after all other steps
     */
    private final List<After> afters;

    /**
     * The list of steps to execute.
     */
    private final List<TypeStep<T>> steps;

    /**
     * The used test data.
     */
    private T data;

    /**
     * Creates a BddTypeScenario.
     */
    public TypeSteps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified list of steps.
     *
     * @param steps
     *            The list of steps to execute.
     */
    public TypeSteps(final List<TypeStep<T>> steps)
    {
        this(steps, new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified reporter and list of
     * {@link Step}s and {@link Before}s.
     * 
     * @param steps
     *            The list of steps to execute.
     * @param befores
     *            The list of {@link Before}s to execute before each other steps.
     */
    public TypeSteps(final List<TypeStep<T>> steps, final List<Before> befores)
    {
        this(steps, befores, new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified reporter and list of
     * {@link Step}s, {@link Before}s and {@link After}s.
     * 
     * @param steps
     *            The list of steps to execute.
     * @param befores
     *            The list of {@link Before}s to execute before the other steps.
     * @param afters
     *            The list of {@link Afters}s to execute after the other steps.
     */
    public TypeSteps(final List<TypeStep<T>> steps, final List<Before> befores, final List<After> afters)
    {
        this.steps = steps;
        this.befores = befores;
        this.afters = afters;
    }

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

        for (final TypeStep<T> step : steps)
        {
            // Set the reporter at the step
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            try
            {
                if (stepException != null || stepError != null)
                {
                    step.withData(data).skipStep();
                }
                // Test the step with the data
                else
                {
                    step.withData(data).test();
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

    public void skipSteps()
    {
        executeBeforeSteps(true);
        for (final TypeStep<T> step : steps)
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            step.withData(data).skipStep();
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
     * Adds a step to the list of BddSteps.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     */
    private void addStep(GherkinKeyword keyword, String description, Consumer<T> consumer)
    {
        this.steps.add(new TypeStep<T>(keyword, description, consumer));
    }

    /**
     * Adds a step to the list of BddSteps.
     * 
     * @param keyword
     *            The String, that describes the {@link GherkinKeyword}.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     */
    private void addStep(String keyword, String description, Consumer<T> consumer)
    {
        try
        {
            this.steps.add(new TypeStep<T>(new GherkinKeyword(keyword), description, consumer));
        } catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Unknown Keyword " + keyword, e);
        }
    }

    /**
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param steps
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     */
    private void addAllSteps(TypeSteps<T> steps)
    {
        for (TypeStep<T> step : steps.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final String description, final Consumer<T> consumer)
    {
        addStep("And", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final String description, final Runnable runner)
    {

        addStep("And", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final String description, final Consumer<T> consumer)
    {
        addStep("Given", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Given".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final String description, final Runnable runner)
    {
        addStep("Given", description, runnableToConsumer(runner));

        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final String description, final Consumer<T> consumer)
    {
        addStep("Then", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Then".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final String description, final Runnable runner)
    {
        addStep("Then", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final String description, final Consumer<T> consumer)
    {
        addStep("When", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "When".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final String description, final Runnable runner)
    {
        addStep("When", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Specifies the data of the BddTypeScenario.
     * 
     * @param data
     *            The test data for the scenario.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> withData(final T data)
    {
        this.data = data;
        return this;
    }

    /**
     * Creates a {@link Consumer} out of the specified {@link Runnable}.
     * 
     * @param runner
     *            The Runnable to transform.
     * @return A Consumer, that executes the runner.
     */
    private Consumer<T> runnableToConsumer(final Runnable runner)
    {
        return new Consumer<T>()
            {
                @Override
                public void accept(final T t)
                {
                    runner.run();
                }
            };
    }

    public T getData()
    {
        return data;
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public List<TypeStep<T>> getSteps()
    {
        return steps;
    }

    // public void addBackground(Background background)
    public void addBefore(List<Before> befores)
    {
        this.befores.addAll(befores);
    }

    // public void addBackground(Background background)
    public void addAfters(List<After> afters)
    {
        this.afters.addAll(afters);
    }

    @Override
    public TypeSteps<T> ignore()
    {
        getSteps().get(getSteps().size() - 1).ignore();
        return this;
    }

    @Override
    public TypeSteps<T> wip()
    {
        getSteps().get(getSteps().size() - 1).wip();
        return this;
    }

    @Override
    public TypeSteps<T> skip()
    {
        getSteps().get(getSteps().size() - 1).skip();
        return this;
    }

}
