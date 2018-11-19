package com.ckeiner.testbddy.core.bdd.status;

public interface Statusable
{
    /**
     * State that the Statusable should be ignored.<br>
     * Hence, it doesn't appear in the report.
     */
    public Statusable ignore();

    /**
     * State that the Statusable is still a work in progess.
     * 
     * @return
     */
    public Statusable wip();

    /**
     * State that the Statusable should be skipped.
     */
    public Statusable skip();

}
