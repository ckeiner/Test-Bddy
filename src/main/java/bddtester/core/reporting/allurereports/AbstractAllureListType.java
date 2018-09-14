package bddtester.core.reporting.allurereports;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAllureListType
{
    protected List<AllureStatus> status = new ArrayList<AllureStatus>();

    public AllureStatus getLastStatus()
    {
        if (!status.equals(null))
        {
            return status.get(status.size() - 1);
        } else
            return null;
    }

    public List<AllureStatus> getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status.add(new AllureStatus(status));
    }

}
