package bddtester.core.bdd.beforeAfter;

import bddtester.core.bdd.steps.Steps;

/**
 * Contains a list of steps that are executed before each scenario of a feature.
 * 
 * @author ckeiner
 *
 */
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
