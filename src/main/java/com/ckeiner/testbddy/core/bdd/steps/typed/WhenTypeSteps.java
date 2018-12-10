package com.ckeiner.testbddy.core.bdd.steps.typed;

import java.util.ArrayList;
import java.util.List;

import com.ckeiner.testbddy.core.bdd.steps.TypeStep;
import com.ckeiner.testbddy.core.bdd.steps.TypeSteps;

public class WhenTypeSteps<T> extends TypeSteps<T>
{
    /**
     * Creates a GivenTypeSteps object with an empty list of steps.
     */
    public WhenTypeSteps()
    {
        super(new ArrayList<>());
    }

    /**
     * Creates a GivenTypeSteps with the specified list of steps.
     *
     * @param steps
     *            The list of steps to execute.
     */
    public WhenTypeSteps(final List<TypeStep<T>> steps)
    {
        super(steps);
    }

}
