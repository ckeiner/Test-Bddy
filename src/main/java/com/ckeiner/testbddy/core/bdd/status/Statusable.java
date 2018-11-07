package com.ckeiner.testbddy.core.bdd.status;

public interface Statusable
{
    public Statusable ignore();

    public Statusable wip();

    public Statusable skip();

}
