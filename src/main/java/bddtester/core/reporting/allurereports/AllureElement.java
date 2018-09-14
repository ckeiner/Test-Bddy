package bddtester.core.reporting.allurereports;

import bddtester.core.reporting.ReportElement;

public class AllureElement implements ReportElement
{
    private AllureAllFeatures allFeatures = new AllureAllFeatures();

    public AllureAllFeatures getAllFeatures()
    {
        return allFeatures;
    }

    public void setAllFeatures(AllureAllFeatures allFeatures)
    {
        this.allFeatures = allFeatures;
    }

    public void setStatus(String status, String description)
    {
        if (allFeatures.getAllureActualElement().equals("feature"))
        {
            allFeatures.getLastFeature().setStatus(status);
            allFeatures.getLastFeature().getLastStatus().setFailure(description);
        } else if (allFeatures.getAllureActualElement().equals("scenario"))
        {
            allFeatures.getLastFeature().getLastScenario().setStatus(status);
            allFeatures.getLastFeature().getLastScenario().getLastStatus().setFailure(description);
        } else if (allFeatures.getAllureActualElement().equals("step"))
        {
            allFeatures.getLastFeature().getLastScenario().getLastStep().add(status);
            allFeatures.getLastFeature().getLastScenario().getLastStep().getLastStatus().setFailure(description);
        }
    }

    public void setStatus(String status, Throwable throwable)
    {
        if (allFeatures.getAllureActualElement().equals("feature"))
        {
            allFeatures.getLastFeature().setStatus(status);
            allFeatures.getLastFeature().getLastStatus().setThrowable(throwable);
        } else if (allFeatures.getAllureActualElement().equals("scenario"))
        {
            allFeatures.getLastFeature().getLastScenario().setStatus(status);
            allFeatures.getLastFeature().getLastScenario().getLastStatus().setThrowable(throwable);
        } else if (allFeatures.getAllureActualElement().equals("step"))
        {
            allFeatures.getLastFeature().getLastScenario().getLastStep().add(status);
            System.out.println(allFeatures.getLastFeature().getLastScenario().getLastStep().getStatus()
                    + "\n \n \n \n \n \n \n \n \n" + status);
            allFeatures.getLastFeature().getLastScenario().getLastStep().getLastStatus().setThrowable(throwable);
        }
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
                // if name[i]
            }
        }
    }

}
