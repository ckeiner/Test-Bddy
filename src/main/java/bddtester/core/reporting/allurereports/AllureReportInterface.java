package bddtester.core.reporting.allurereports;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import io.qameta.allure.Step;

public class AllureReportInterface implements ReportInterface
{
    public AllureElement allureElement = new AllureElement();

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
    // @Feature("Feature: {description}")
    public ReportElement feature(String description)
    {
        allureElement.allFeatures.allFeature.add(new AllureFeature(description));
        return allureElement;
    }

    @Override
    // @Story("Scenario: \"{description}\"")
    public ReportElement scenario(String description)
    {
        allureElement.allFeatures.getLastFeature().add(description);
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline:: {description} with data {testdata}")
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        allureElement.allFeatures.getLastFeature().add(description + " with data " + testdata);
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline: {description}")
    public <T> ReportElement scenarioOutline(String description)
    {
        allureElement.allFeatures.getLastFeature().add(description);
        return allureElement;
    }

    String gherkinKeyword;

    // @Feature("my feature")
    // @Story("my story")
    // @Step("Step: {description}")
    @Override
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        gherkinKeyword = keyword.toString();
        allureElement.allFeatures.getLastFeature().getLastScenario().add(gherkinKeyword + ": " + description);
        return allureElement;
    }

    @Step("{description}")
    void setStep(AllureStep step, String description)
    {
        // TODO : Iterate over all stati
    }

    @Step("{description}")
    void setScenario(AllureScenario scenario, String description)
    {
        for (AllureStep step : scenario.scenario)
        {
            setStep(step, step.description);
        }
    }

    @Step("{description}")
    void setFeature(AllureFeature feature, String description)
    {

        System.out.println(" Here  feature");
        for (AllureScenario scenario : feature.feature)
        {
            setScenario(scenario, scenario.description);
        }
    }

    @Override
    @Step("Some Step")
    public void finishReport()
    {
        System.out.println(" Here ");
        // setFeature(allureElement.feature)
        for (AllureFeature feature : allureElement.allFeatures.allFeature)
        {
            setFeature(feature, feature.description);
        }
    }

}
