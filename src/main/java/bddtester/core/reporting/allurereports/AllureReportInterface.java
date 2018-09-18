package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.MultipleFailureException;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import io.qameta.allure.Step;

public class AllureReportInterface implements ReportInterface
{
    public final AllureElement allureElement;

    private String gherkinKeyword;

    public AllureReportInterface()
    {
        allureElement = new AllureElement();
    }

    @Override
    // @Feature("Feature: {description}")
    public ReportElement feature(String description)
    {
        AllureFeature feature = new AllureFeature(description);
        allureElement.getAllFeatures().getAllFeature().add(feature);
        allureElement.getAllFeatures().setActualElement(feature);
        return allureElement;
    }

    @Override
    // @Story("Scenario: \"{description}\"")
    public ReportElement scenario(String description)
    {
        AllureScenario scenario = new AllureScenario(description);
        allureElement.getAllFeatures().getLastFeature().add(scenario);
        allureElement.getAllFeatures().setActualElement(scenario);
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline:: {description} with data {testdata}")
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        AllureScenario scenario = new AllureScenario(description + " with data " + testdata);
        allureElement.getAllFeatures().getLastFeature().add(scenario);
        allureElement.getAllFeatures().setActualElement(scenario);
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline: {description}")
    public <T> ReportElement scenarioOutline(String description)
    {
        AllureScenario scenario = new AllureScenario(description);
        allureElement.getAllFeatures().getLastFeature().add(scenario);
        allureElement.getAllFeatures().setActualElement(scenario);
        return allureElement;
    }

    @Override
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        AllureStep step = new AllureStep(gherkinKeyword + ": " + description);
        allureElement.getAllFeatures().getLastFeature().getLastScenario().add(step);
        allureElement.getAllFeatures().setActualElement(step);
        return allureElement;
    }

    @Step("{status}")
    private void reportStatus(String status)
    {

    }

    @Override
    public void finishReport() throws Throwable
    {
        List<Throwable> errors = new ArrayList<>();
        for (AllureFeature feature : allureElement.getAllFeatures().getAllFeature())
        {
            try
            {
                feature.report(feature.getDescription());
            } catch (Throwable e)
            {
                errors.add(e);
            }
        }
        if (errors != null && !errors.isEmpty())
        {
            throw new MultipleFailureException(errors);
        }
    }

}
