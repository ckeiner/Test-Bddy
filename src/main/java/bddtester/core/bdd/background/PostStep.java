package bddtester.core.bdd.background;

import bddtester.core.bdd.steps.Steps;

public class PostStep
{
    /**
     * The steps to execute.
     */
    final private Steps steps;

    public PostStep(final Steps steps)
    {
        this.steps = steps;
    }

    public Steps getSteps()
    {
        return steps;
    }

}
