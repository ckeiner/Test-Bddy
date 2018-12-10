package com.ckeiner.testbddy.core.bdd.steps.typed;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.steps.Step;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class WhenSteps extends Steps
{
    /**
     * Creates a BddScenario.
     */
    public WhenSteps()
    {
        super(new ArrayList<>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public WhenSteps(final List<Step> steps)
    {
        super(steps);
    }

}
