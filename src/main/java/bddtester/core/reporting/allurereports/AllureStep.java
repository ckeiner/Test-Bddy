package bddtester.core.reporting.allurereports;

import io.qameta.allure.Step;

public class AllureStep extends AbstractAllureListType
{
    private String description;

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
        this.description = description;
    }

    @Step("Step: {description}")
    public void report(String description) throws Throwable
    {
        Throwable throwableError = null;
        if (getStatus() != null)
        {
            for (AllureStatus status : getStatus())
            {
                // setStatus(status.getStatus());
                if (status.getThrowable() != null)
                {
                    throwableError = status.getThrowable();
                }
            }
        }
        if (throwableError != null)
        {
            throw throwableError;
        }
    }
}
