package com.ckeiner.testbddy.core.reporting.extentreports;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.ckeiner.testbddy.core.reporting.ReportElement;

/**
 * Depicts a test element for {@link ExtentReports}, which is a
 * {@link ExtentTest}.
 *
 * @author ckeiner
 *
 */
public class ExtentElement implements ReportElement
{
    /**
     * The element created by the {@link ExtentReportInterface}.
     */
    public ExtentTest extentElement;

    /**
     * Creates a new instance of this class with the given element.
     * 
     * @param extentElement
     *            The {@link ExtentTest}.
     */
    public ExtentElement(ExtentTest extentElement)
    {
        this.extentElement = extentElement;
    }

    @Override
    public void fail(String description)
    {
        extentElement.fail(description);
    }

    @Override
    public void fail(String description, String pathToScreenshot)
    {
        try
        {
            extentElement.fail(description, MediaEntityBuilder.createScreenCaptureFromPath(pathToScreenshot).build());
        } catch (IOException e)
        {
            extentElement.fail(description + " and no Screenshot due to: " + e.getMessage());
        }
    }

    @Override
    public void fatal(String description)
    {
        extentElement.fatal(description);
    }

    @Override
    public void pass(String description)
    {
        extentElement.pass(description);
    }

    @Override
    public void skip(String description)
    {
        extentElement.skip(description);
    }

    @Override
    public void fail(Throwable throwable)
    {
        extentElement.fail(throwable);
    }

    @Override
    public void fail(Throwable throwable, String pathToScreenshot)
    {
        try
        {
            extentElement.fail(throwable, MediaEntityBuilder.createScreenCaptureFromPath(pathToScreenshot).build());
        } catch (IOException e)
        {
            extentElement.fatal(throwable);
        }
    }

    @Override
    public void fatal(Throwable throwable)
    {
        extentElement.fatal(throwable);
    }

    @Override
    public void fatal(String description, String pathToScreenshot)
    {
        try
        {
            extentElement.fatal(description, MediaEntityBuilder.createScreenCaptureFromPath(pathToScreenshot).build());
        } catch (IOException e)
        {
            extentElement.fatal(description + " and no Screenshot due to: " + e.getMessage());
        }
    }

    @Override
    public void fatal(Throwable throwable, String pathToScreenshot)
    {
        try
        {
            extentElement.fatal(throwable, MediaEntityBuilder.createScreenCaptureFromPath(pathToScreenshot).build());
        } catch (IOException e)
        {
            extentElement.fatal(throwable);
        }
    }

    public ExtentTest getElement()
    {
        return this.extentElement;
    }

    @Override
    public void skip(Throwable throwable)
    {
        extentElement.skip(throwable);
    }

    @Override
    public void pending(String description)
    {
        this.skip("<b>PENDING</b>: " + description);
    }

    @Override
    public void assignCategory(String... name)
    {
        getElement().assignCategory(name);
    }

}
