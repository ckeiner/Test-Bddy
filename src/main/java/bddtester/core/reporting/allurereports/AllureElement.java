package bddtester.core.reporting.allurereports;

import bddtester.core.reporting.ReportElement;

public class AllureElement implements ReportElement
{
    private AllureAllFeatures allFeatures;

    public AllureElement()
    {
        allFeatures = new AllureAllFeatures();
    }

    public AllureAllFeatures getAllFeatures()
    {
        return allFeatures;
    }

    public void setStatus(String status, String description)
    {
        allFeatures.getActualElement().addStatus(status);
        allFeatures.getActualElement().getLastStatus().setFailure(description);
    }

    public void setStatus(String status, Throwable throwable)
    {
        allFeatures.getActualElement().addStatus(status);
        allFeatures.getActualElement().getLastStatus().setThrowable(throwable);
    }

    @Override
    // @Description("Failed. {description}")
    public void fail(String description)
    {
        setStatus("fail", description);
    }

    @Override
    // @Description("Failed. {description}")
    public void fail(String description, String pathToScreenshot)
    {
        setStatus("fail", description);
    }

    @Override
    // @Description("A fatal error occured. {description}")
    public void fatal(String description)
    {
        setStatus("fatal", description);
    }

    @Override
    // @Description("Passed. {description}")
    public void pass(String description)
    {
        setStatus("pass", description);
    }

    @Override
    // @Description("Skipped. {description}")
    public void skip(String description)
    {
        setStatus("skip", description);
    }

    @Override
    // @Description("Failed. {throwable.getMessage()}")
    public void fail(Throwable throwable)
    {
        setStatus("fail", throwable);
    }

    @Override
    // @Description("Failed. {throwable.getMessage()}")
    public void fail(Throwable throwable, String pathToScreenshot)
    {
        setStatus("fail", throwable);
    }

    @Override
    // @Description("A fatal error occured. {throwable.getMessage()}")
    public void fatal(Throwable throwable)
    {
        setStatus("fatal", throwable);
    }

    @Override
    // @Description("A fatal error occured. {description}")
    public void fatal(String description, String pathToScreenshot)
    {
        setStatus("fatal", description);
    }

    @Override
    // @Description("A fatal error occured. {throwable.getMessage()}")
    public void fatal(Throwable throwable, String pathToScreenshot)
    {
        setStatus("fatal", throwable);
    }

    @Override
    // @Description("Skipped. {throwable}")
    public void skip(Throwable throwable)
    {
        setStatus("skip", throwable);
    }

    @Override
    public void assignCategory(String... name)
    {
        if (name != null)
        {
            for (int i = 0; i < name.length; i++)
            {
                setStatus(name[i], "");
            }
        }
    }

}
