package bddtester.core.bdd.steps.gwat;

import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.steps.TypeSteps;

public interface TypeGWAT<T>
{
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
    public TypeGWAT<T> and(final TypeSteps<T> scenario);

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
    public TypeGWAT<T> and(final String description, final Consumer<T> consumer);

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
    public TypeGWAT<T> given(final TypeSteps<T> scenario);

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
    public TypeGWAT<T> given(final String description, final Consumer<T> consumer);

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
    public TypeGWAT<T> then(final TypeSteps<T> scenario);

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
    public TypeGWAT<T> then(final String description, final Consumer<T> consumer);

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
    public TypeGWAT<T> when(final TypeSteps<T> scenario);

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
    public TypeGWAT<T> when(final String description, final Consumer<T> consumer);

}
