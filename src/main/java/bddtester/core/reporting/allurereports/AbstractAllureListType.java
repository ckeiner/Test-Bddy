package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAllureListType
{
    protected List<AllureStatus> status;

    public AbstractAllureListType()
    {
        status = new ArrayList<AllureStatus>();
    }

    public AllureStatus getLastStatus()
    {
        if (status != null && !status.isEmpty())
        {
            return status.get(status.size() - 1);
        }
        else
            return null;
    }

    public List<AllureStatus> getStatus()
    {
        return status;
    }

    public void addStatus(String status)
    {
        this.status.add(new AllureStatus(status));
    }

}
