package com.ckeiner.testbddy.core.bdd.steps.typed;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.steps.Step;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class GivenSteps extends Steps
{
    /**
     * Creates a BddScenario.
     */
    public GivenSteps()
    {
        super(new ArrayList<>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public GivenSteps(final List<Step> steps)
    {
        super(steps);
    }

}
