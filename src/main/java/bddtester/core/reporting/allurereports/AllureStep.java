package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureStep
{
    List<AllureStatus> step;
    String description;

    public AllureStep(String description)
    {
        this.step = new ArrayList<AllureStatus>();
        this.description = description;
    }

    public void add(String status)
    {
        step.add(new AllureStatus(status));
    }

    public AllureStatus getLastStatus()
    {
        return step.get(step.size() - 1);
    }
}
