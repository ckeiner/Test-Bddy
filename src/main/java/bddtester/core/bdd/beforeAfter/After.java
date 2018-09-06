package bddtester.core.bdd.beforeAfter;

import bddtester.core.bdd.steps.Steps;

/**
 * Contains a list of steps that are executed after each scenario of a feature.
 * 
 * @author ckeiner
 *
 */
public class After
{
    /**
     * The steps to execute.
     */
    final private Steps steps;

    public After(final Steps steps)
    {
        this.steps = steps;
    }

    public Steps getSteps()
    {
        return steps;
    }

}
