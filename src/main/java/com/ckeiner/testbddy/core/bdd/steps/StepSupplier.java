package com.ckeiner.testbddy.core.bdd.steps;

import java.util.function.Supplier;

public interface StepSupplier extends Supplier<Steps>
{
    default StepSupplier and(StepSupplier otherSupplier)
    {
        return () -> get().and(otherSupplier.get());
    }

    default StepSupplier when(StepSupplier otherSupplier)
    {
        return () -> get().when(otherSupplier.get());
    }

    default StepSupplier then(StepSupplier otherSupplier)
    {
        return () -> get().then(otherSupplier.get());
    }

    default StepSupplier given(StepSupplier otherSupplier)
    {
        return () -> get().given(otherSupplier.get());
    }
}
