package com.ckeiner.testbddy.core.bdd.steps;

import java.util.ArrayList;

import com.aventstack.extentreports.GherkinKeyword;

/**
 * Describes a BDD Scenario.<br>
 * Thus, it has the given, when, then, and methods.
 * 
 * @author ckeiner
 *
 */
public class Steps extends AbstractSteps<Step>
{
    /**
     * Creates a BddScenario.
     */
    public Steps()
    {
        super(new ArrayList<>());
    }

    @Override
    protected boolean testStep(Step step)
    {
        return step.test();
    }

    @Override
    protected void skipStep(Step step)
    {
        step.skipStep();
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
            getSteps().add(new Step(new GherkinKeyword(keyword), description, runner));
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
        getSteps().add(new Step(keyword, description, runner));
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
        for (Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    /**
     * Adds all steps of the specified parameter to the steps.
     * 
     * @param scenario
     *            The additional steps to add to the existing steps.<br>
     *            The Steps with the steps that should be added to the steps of this
     *            class.
     * @return The current Steps.
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public Steps when(final String description, final Runnable runner)
    {
        addStep("When", description, runner);
        return this;
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
