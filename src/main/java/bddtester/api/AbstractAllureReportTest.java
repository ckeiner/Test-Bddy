package bddtester.api;

import org.junit.After;
import org.junit.Before;

//import com.xceptance.neodymium.util.AllureAddons;

import bddtester.core.bdd.Feature;
import bddtester.core.reporting.allurereports.AllureReportInterface;
//import io.qameta.allure.Step;

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
    @Before
    public void initialize()
    {
        Feature.reporter = new AllureReportInterface();
    }

    /**
     * Finishes the report and sets the reporter back to <code>null</code>.
     */
    @After
    public void finishReport() throws Throwable
    {
        // AllureAddons.step("mystep", () ->
        // {
        // System.out.println();
        // });
        if (Feature.reporter != null)
        {
            Feature.reporter.finishReport();
            Feature.reporter = null;
        }
    }
}
