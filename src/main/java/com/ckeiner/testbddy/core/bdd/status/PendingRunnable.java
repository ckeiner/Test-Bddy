package com.ckeiner.testbddy.core.bdd.status;

public class PendingRunnable implements Runnable
{

    @Override
    public void run()
    {
        throw new UnsupportedOperationException("Should not be executed");
    }

}
