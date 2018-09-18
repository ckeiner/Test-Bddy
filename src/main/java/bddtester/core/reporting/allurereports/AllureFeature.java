package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Step;

public class AllureFeature extends AbstractAllureListType
{
    private List<AllureScenario> feature;

    private String description;

    public List<AllureScenario> getFeature()
    {
        return feature;
    }

    public void setFeature(List<AllureScenario> feature)
    {
        this.feature = feature;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public AllureFeature(String description)
    {
        this.feature = new ArrayList<AllureScenario>();
        this.description = description;
    }

    public void add(AllureScenario scenario)
    {
        feature.add(scenario);
    }

    public AllureScenario getLastScenario()
    {
        return feature.get(feature.size() - 1);
    }

    @Step("Feature: {description}")
    public void report(String description) throws Throwable
    {
        Throwable scenarioError = null;
        // List<String> noStatusRepetition = new ArrayList<String>();

        for (AllureScenario scenario : getFeature())
        {
            for (AllureStatus status : scenario.getStatus())
            {
                // noStatusRepetition.add(status.getStatus());
                // for (String statusCheck : noStatusRepetition) {
                // if (status.equals(status.getStatus()))
                // }
                // setStatus(status.getStatus());
            }
            try
            {
                scenario.report(scenario.getDescription());
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
}
