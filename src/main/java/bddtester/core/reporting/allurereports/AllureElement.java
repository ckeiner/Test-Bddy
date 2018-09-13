package bddtester.core.reporting.allurereports;

import bddtester.core.reporting.ReportElement;

public class AllureElement implements ReportElement
{
    AllureAllFeatures allFeatures = new AllureAllFeatures();

    @Override

    // @Description("Failed. {description}")
    public void fail(String description)
    {
    }

    @Override
    // @Description("Failed. {description}")
    public void fail(String description, String pathToScreenshot)
    {
    }

    @Override
    // @Description("A fatal error occured. {description}")
    public void fatal(String description)
    {
    }

    @Override
    // @Description("Passed. {description}")
    public void pass(String description)
    {
    }

    @Override
    // @Description("Skipped. {description}")
    public void skip(String description)
    {
    }

    @Override
    // @Description("Failed. {throwable.getMessage()}")
    public void fail(Throwable throwable)
    {
    }

    @Override
    // @Description("Failed. {throwable.getMessage()}")
    public void fail(Throwable throwable, String pathToScreenshot)
    {
    }

    @Override
    // @Description("A fatal error occured. {throwable.getMessage()}")
    public void fatal(Throwable throwable)
    {
    }

    @Override
    // @Description("A fatal error occured. {description}")
    public void fatal(String description, String pathToScreenshot)
    {
    }

    @Override
    // @Description("A fatal error occured. {throwable.getMessage()}")
    public void fatal(Throwable throwable, String pathToScreenshot)
    {
    }

    @Override
    // @Description("Skipped. {throwable}")
    public void skip(Throwable throwable)
    {
    }

    @Override
    public void assignCategory(String... name)
    {
        // TODO
    }

}
