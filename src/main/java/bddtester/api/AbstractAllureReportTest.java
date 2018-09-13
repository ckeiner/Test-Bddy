package bddtester.api;

import org.junit.After;
import org.junit.BeforeClass;

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
    @BeforeClass
    public static void initialize()
    {
        Feature.reporter = AllureReportInterface.getInstance();
    }

    /**
     * Finishes the report and sets the reporter back to <code>null</code>.
     */
    @After
    // @Step("AfterClassStep")
    public void finishReport()
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
