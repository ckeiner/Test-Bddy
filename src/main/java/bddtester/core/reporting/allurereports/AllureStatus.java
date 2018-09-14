package bddtester.core.reporting.allurereports;

public class AllureStatus
{
    private String status = "";
    private String failure = "";
    private Throwable throwable = null;

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    public String getFailure()
    {
        return failure;
    }

    public void setFailure(String failure)
    {
        this.failure = failure;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public AllureStatus(String status)
    {
        this.status = status;
    }
}
