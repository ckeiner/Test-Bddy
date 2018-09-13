package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureScenario
{
    List<AllureStep> scenario;
    String description;

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
