package pom.posters.util;

import com.xceptance.neodymium.util.WebDriverUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class DriverHook
{

    public static void setUp(final String browser)
    {
        WebDriverUtils.setUp(browser);
    }

    @After(order = 100)
    public void tearDown(final Scenario scenario)
    {
        WebDriverUtils.tearDown(scenario);
    }

}
