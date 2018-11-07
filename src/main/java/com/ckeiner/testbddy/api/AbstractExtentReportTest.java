package com.ckeiner.testbddy.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.reporting.extentreports.ExtentReportInterface;

/**
 * Sets ExtentReport as reporter for a {@link Feature}.
 * 
 * @author ckeiner
 *
 */
public abstract class AbstractExtentReportTest
{
    /**
     * Sets the reporter of Feature to {@link ExtentReportInterface#getInstance()}.
     */
    @BeforeClass
    public static void initialize()
    {
        Feature.reporter = ExtentReportInterface.getInstance();
    }

    /**
     * Finishes the report and sets the reporter back to <code>null</code>.
     */
    @AfterClass
    public static void finishReport()
    {
        if (Feature.reporter != null)
        {
            Feature.reporter.finishReport();
            Feature.reporter = null;
        }
    }
}
