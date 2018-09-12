package bddtester.core.reporting.allurereports;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

public class AllureReportInterface implements ReportInterface
{
    public AllureElement feature;
    public AllureElement scenario;
    public AllureElement step;

    public AllureReportInterface()
    {
    }

    /**
     * Optimizes concurrent access to the instance (Initialization-on-demand holder
     * idiom)
     * 
     * @author ckeiner
     */
    private static class LazyHolder
    {
        static final AllureReportInterface INSTANCE = new AllureReportInterface();
    }

    /**
     * Creates and gets the instace from the LazyHolder
     * 
     * @return The Singleton Instance of the ExtentReportInterface.
     */
    public static AllureReportInterface getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    @Override
    @Feature("Feature: {description}")
    public ReportElement feature(String description)
    {
        feature = new AllureElement();
        return feature;
    }

    @Override
    @Step("{method}{description}")
    public ReportElement scenario(String description)
    {
        scenario = new AllureElement();
        return scenario;
    }

    @Override
    @Step("{method}{description} with data {testdata}")
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        return new AllureElement();
    }

    @Override
    @Step("{method}{description}")
    public <T> ReportElement scenarioOutline(String description)
    {
        return new AllureElement();
    }

    @Override
    @Step("{keyword}: {description}")
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        step = new AllureElement();
        return step;
    }

    @Override
    public void finishReport()
    {
        // nothing to do here
    }

}
