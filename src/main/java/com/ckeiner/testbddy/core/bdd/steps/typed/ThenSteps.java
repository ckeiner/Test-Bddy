package com.ckeiner.testbddy.core.bdd.steps.typed;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.steps.Step;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class ThenSteps extends Steps
{
    /**
     * Creates a BddScenario.
     */
    public ThenSteps()
    {
        super(new ArrayList<>());
    }

    /**
     * Creates a BddScenario with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of BddSteps that specify this BddScenario
     */
    public ThenSteps(final List<Step> steps)
    {
        super(steps);
    }

}
