package tests.posters.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import bddtester.core.bdd.Feature;
import bddtester.core.reporting.extentreports.ExtentReportInterface;

public abstract class AbstractExtentReportTest
{
    @BeforeClass
    public static void initialize()
    {
        Feature.reporter = ExtentReportInterface.getInstance();
    }

    @AfterClass
    public static void finishReport()
    {
        if (Feature.reporter != null)
        {
            Feature.reporter.finishReport();
        }
    }
}
