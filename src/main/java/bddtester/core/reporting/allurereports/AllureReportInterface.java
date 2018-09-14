package bddtester.core.reporting.allurereports;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.reporting.ReportElement;
import bddtester.core.reporting.ReportInterface;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Label;

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
        return allureElement;
    }

    @Override
    // @Story("Scenario: \"{description}\"")
    public ReportElement scenario(String description)
    {
        allureElement.getAllFeatures().getLastFeature().add(description);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline:: {description} with data {testdata}")
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        allureElement.getAllFeatures().getLastFeature().add(description + " with data " + testdata);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        return allureElement;
    }

    @Override
    // @Step("Scenario Outline: {description}")
    public <T> ReportElement scenarioOutline(String description)
    {
        allureElement.getAllFeatures().getLastFeature().add(description);
        allureElement.getAllFeatures().setAllureActualElement("scenario");
        return allureElement;
    }

    String gherkinKeyword;

    @Override
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        gherkinKeyword = keyword.toString();
        allureElement.getAllFeatures().getLastFeature().getLastScenario().add(gherkinKeyword + ": " + description);
        allureElement.getAllFeatures().setAllureActualElement("step");
        return allureElement;
    }

    @Step("{description}")
    void setStep(AllureStep step, String description) throws Throwable
    {

        if (step.getStatus() != null)
        {
            System.out.println("\n\n\n\n status: " + step.getStatus() + " \n\n\n\n");
            for (AllureStatus status : step.getStatus())
            {
                if (status.getStatus().equals("fail"))
                {
                    System.out.println("Fail");
                    if (status.getThrowable() != null)
                    {
                        try
                        {
                            throw status.getThrowable();
                        } catch (Throwable e)
                        {
                            e.printStackTrace();
                        }
                    }
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                } else if (status.getStatus().equals("skip"))
                {
                    System.out.println("Skip");
                    if (status.getThrowable() != null)
                    {
                        try
                        {
                            throw status.getThrowable();
                        } catch (Throwable e)
                        {
                            e.printStackTrace();
                        }
                    }
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                } else if (status.getStatus().equals("fatal"))
                {
                    System.out.println("Fatal");
                    if (status.getThrowable() != null)
                    {
                        try
                        {
                            throw status.getThrowable();
                        } catch (Throwable e)
                        {
                            throw e;
                        }
                    }
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                } else if (status.getStatus().equals("wip"))
                {
                    System.out.println("wip");
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                } else if (status.getStatus().equals("ignore"))
                {
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                } else
                {
                    System.out.println("\n\n\n\n\nPass +++++++++++++++++++" + step.getDescription());
                    Label label = new Label();
                    label.setName(status.getStatus());
                    Allure.addLabels(label);
                }
            }
        }
    }

    @Step("{description}")
    public void setScenario(AllureScenario scenario, String description) throws Throwable
    {
        for (AllureStep step : scenario.getScenario())
        {
            setStep(step, step.getDescription());
        }
    }

    @Step("{description}")
    public void setFeature(AllureFeature feature, String description) throws Throwable
    {
        for (AllureScenario scenario : feature.getFeature())
        {
            setScenario(scenario, scenario.getDescription());
        }
    }

    @Override
    public void finishReport() throws Throwable
    {
        // setFeature(allureElement.feature)
        for (AllureFeature feature : allureElement.getAllFeatures().getAllFeature())
        {
            setFeature(feature, feature.getDescription());
        }
    }

}
