package com.ckeiner.testbddy.api;

public class PendingRunnable implements Runnable
{

    @Override
    public void run()
    {
        throw new UnsupportedOperationException("Should not be executed");
    }

}
