package bddtester.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import bddtester.core.bdd.Feature;
import bddtester.core.reporting.allurereports.AllureReportInterface;

/**
 * Sets ExtentReport as reporter for a {@link Feature}.
 * 
 * @author ckeiner
 *
 */
public abstract class AbstractAllureReportTest
{
    /**
     * Sets the reporter of Feature to {@link AllureReportInterface#getInstance()}.
     */
    @BeforeClass
    public static void initialize()
    {
        Feature.reporter = AllureReportInterface.getInstance();
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
