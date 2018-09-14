package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public class AllureStep extends AbstractAllureListType
{
    private List<AllureStatus> step;
    private String description;

    public List<AllureStatus> getStep()
    {
        return step;
    }

    public void setStep(List<AllureStatus> step)
    {
        this.step = step;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

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
