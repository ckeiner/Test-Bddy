package com.ckeiner.testbddy.core.bdd.status;

public interface Statusable
{
    /**
     * Ignore this component.<br>
     * This means, it doesn't appear in the report.
     */
    public Statusable ignore();

    /**
     * This component is a work in progess.
     * 
     * @return
     */
    public Statusable wip();

    /**
     * Skip this component.<br>
     * This means, it appears in the report, but is not executed.
     */
    public Statusable skip();

}
