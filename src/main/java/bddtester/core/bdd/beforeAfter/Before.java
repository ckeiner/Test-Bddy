package bddtester.core.bdd.beforeAfter;

import bddtester.core.bdd.steps.Steps;

public class Before
{
    /**
     * The steps to execute.
     */
    final private Steps steps;

    public Before(final Steps steps)
    {
        this.steps = steps;
    }

    public Steps getSteps()
    {
        return steps;
    }

}
