package com.ckeiner.testbddy.core.bdd.status;

import java.util.function.Consumer;

public class PendingConsumer<T> implements Consumer<T>
{

    @Override
    public void accept(T arg0)
    {
        throw new UnsupportedOperationException("Should not be executed");
    }

}
