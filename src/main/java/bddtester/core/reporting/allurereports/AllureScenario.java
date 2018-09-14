package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

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

    public void add(String step)
    {
        scenario.add(new AllureStep(step));
    }

    public AllureStep getLastStep()
    {
        return scenario.get(scenario.size() - 1);
    }
}
