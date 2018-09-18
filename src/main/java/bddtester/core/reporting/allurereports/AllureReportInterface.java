package bddtester.core.reporting.allurereports;

import java.util.List;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import io.qameta.allure.Step;

public class AllureReportInterface implements ReportInterface
{
    public AllureElement allureElement = new AllureElement();

    public void setAllureElement(AllureElement allureElement)
    {
        this.allureElement = allureElement;
    }

    public AllureReportInterface()
    {
    }

    @Override
    // @Feature("Feature: {description}")
    public ReportElement feature(String description)
    {
        allureElement.getAllFeatures().getAllFeature().add(new AllureFeature(description));
        allureElement.getAllFeatures().setAllureActualElement("feature");
        allureElement.getAllFeatures().getLastFeature().initStatus();
        return allureElement;
    }

    @Override
    // @Story("Scenario: \"{description}\"")
    public ReportElement scenario(String description)
    {
        allureElement.getAllFeatures().getLastFeature().add(description);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        allureElement.getAllFeatures().getLastFeature().getLastScenario().initStatus();
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline:: {description} with data {testdata}")
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        allureElement.getAllFeatures().getLastFeature().add(description + " with data " + testdata);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        allureElement.getAllFeatures().getLastFeature().getLastScenario().initStatus();
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline: {description}")
    public <T> ReportElement scenarioOutline(String description)
    {
        allureElement.getAllFeatures().getLastFeature().add(description);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        allureElement.getAllFeatures().getLastFeature().getLastScenario().initStatus();
        return allureElement;
    }

    String gherkinKeyword;

    @Override
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        gherkinKeyword = keyword.toString();
        allureElement.getAllFeatures().getLastFeature().getLastScenario().add(gherkinKeyword + ": " + description);
        allureElement.getAllFeatures().setAllureActualElement("step");
        allureElement.getAllFeatures().getLastFeature().getLastScenario().getLastStep().initStatus();
        return allureElement;
    }

    @Step("{status}")
    void setStatus(String status)
    {

    }

    @Step("Step: {description}")
    void setStep(AllureStep step, String description, List<AllureStatus> statusList) throws Throwable
    {
        Throwable throwableError = null;
        if (step.getStatus() != null)
        {
            for (AllureStatus status : step.getStep())
            {
                setStatus(status.getStatus());
                try
                {
                    if (status.getThrowable() != null)
                        throw status.getThrowable();
                } catch (Throwable e)
                {
                    throwableError = e;
                }
            }
        }
        if (throwableError != null)
        {
            throw throwableError;
        }
    }

    @Step("Scenario: {description}")
    public void setScenario(AllureScenario scenario, String description) throws Throwable
    {
        Throwable throwableError = null;
        for (AllureStep step : scenario.getScenario())
        {

            setStep(step, step.getDescription(), step.getStatus());
        }
        try
        {

        } catch (Throwable e)
        {
            throwableError = e;
        }
        if (throwableError != null)
        {
            throw throwableError;
        }
    }

    @Step("Feature: {description}")
    public void setFeature(AllureFeature feature, String description) throws Throwable
    {
        Throwable scenarioError = null;
        // List<String> noStatusRepetition = new ArrayList<String>();

        for (AllureScenario scenario : feature.getFeature())
        {
            for (AllureStatus status : scenario.getStatus())
            {
                // noStatusRepetition.add(status.getStatus());
                // for (String statusCheck : noStatusRepetition) {
                // if (status.equals(status.getStatus()))
                // }
                setStatus(status.getStatus());
            }
            try
            {
                setScenario(scenario, scenario.getDescription());
            } catch (Throwable e)
            {
                scenarioError = e;
            }

        }
        if (scenarioError != null)
        {
            throw scenarioError;
        }
    }

    @Override
    public void finishReport() throws Throwable
    {
        // setFeature(allureElement.feature)
        for (AllureFeature feature : allureElement.getAllFeatures().getAllFeature())
        {
            for (AllureStatus status : feature.getStatus())
            {
                setStatus(status.getStatus());
            }
            setFeature(feature, feature.getDescription());
        }
    }

}
