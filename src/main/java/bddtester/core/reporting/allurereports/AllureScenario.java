package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Step;

public class AllureScenario extends AbstractAllureListType
{
    private List<AllureStep> scenario;

    private String description;

    public List<AllureStep> getScenario()
    {
        return scenario;
    }

    public void setScenario(List<AllureStep> scenario)
    {
        this.scenario = scenario;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public AllureScenario(String description)
    {
        this.scenario = new ArrayList<AllureStep>();
        this.description = description;
    }

    public void add(AllureStep step)
    {
        scenario.add(step);
    }

    public AllureStep getLastStep()
    {
        return scenario.get(scenario.size() - 1);
    }

    @Step("Scenario: {description}")
    public void report(String description) throws Throwable
    {
        Throwable throwableError = null;
        for (AllureStep step : getScenario())
        {
            try
            {
                step.report(step.getDescription());
            } catch (Throwable e)
            {
                throwableError = e;
            }
        }
        if (throwableError != null)
        {
            throw throwableError;
        }
    }
}
