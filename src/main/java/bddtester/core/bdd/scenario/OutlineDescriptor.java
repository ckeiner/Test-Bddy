package bddtester.core.bdd.scenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.status.Statusable;
import bddtester.core.bdd.steps.Steps;
import bddtester.core.bdd.steps.TypeSteps;

/**
 * Describes a {@link ScenarioOutline}.<br>
 * Thus, it has information about every test data used in the scenario and the
 * behavior of it.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the test data.
 */
public class OutlineDescriptor<T> implements Statusable
{
    private final TypeSteps<T> steps;

    private final List<T> testdata;

    @SafeVarargs
    public OutlineDescriptor(T... testdata)
    {
        this(new TypeSteps<T>(), testdata);
    }

    @SafeVarargs
    public OutlineDescriptor(TypeSteps<T> steps, T... testdata)
    {
        this.steps = steps;
        this.testdata = new ArrayList<>();
        this.testdata.addAll(Arrays.asList(testdata));
    }

    public TypeSteps<T> getSteps()
    {
        return steps;
    }

    public List<T> getTestdata()
    {
        return testdata;
    }

    /**
     * Adds all steps of the specified scenario to the steps.<br>
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final Steps scenario)
    {
        getSteps().and(scenario);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The {@link TypeSteps} with the steps that should be added to the
     *            steps of this class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final TypeSteps<T> scenario)
    {
        getSteps().and(scenario);
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
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final String description, final Consumer<T> consumer)
    {
        getSteps().and(description, consumer);
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
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final String description, final Runnable runner)
    {

        getSteps().and(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final Steps scenario)
    {
        getSteps().given(scenario);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The {@link TypeSteps} with the steps that should be added to the
     *            steps of this class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final TypeSteps<T> scenario)
    {
        getSteps().given(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Given".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final String description, final Consumer<T> consumer)
    {
        getSteps().given(description, consumer);
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
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final String description, final Runnable runner)
    {
        getSteps().given(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final Steps scenario)
    {
        getSteps().then(scenario);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The {@link TypeSteps} with the steps that should be added to the
     *            steps of this class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final TypeSteps<T> scenario)
    {
        getSteps().then(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Then".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final String description, final Consumer<T> consumer)
    {
        getSteps().then(description, consumer);
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
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final String description, final Runnable runner)
    {
        getSteps().then(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final Steps scenario)
    {
        getSteps().when(scenario);
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The {@link TypeSteps} with the steps that should be added to the
     *            steps of this class.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final TypeSteps<T> scenario)
    {
        getSteps().when(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "When".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final String description, final Consumer<T> consumer)
    {
        getSteps().when(description, consumer);
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
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final String description, final Runnable runner)
    {
        getSteps().when(description, runner);
        return this;
    }

    @Override
    public OutlineDescriptor<T> ignore()
    {
        getSteps().ignore();
        return this;
    }

    @Override
    public OutlineDescriptor<T> wip()
    {
        getSteps().wip();
        return this;
    }

    @Override
    public OutlineDescriptor<T> skip()
    {
        getSteps().skip();
        return this;
    }

}
