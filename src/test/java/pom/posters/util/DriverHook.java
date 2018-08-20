package pom.posters.util;

import com.xceptance.neodymium.util.Driver;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class DriverHook
{

    public static void setUp(final String browser)
    {
        Driver.setUp(browser);
    }

    @After(order = 100)
    public void tearDown(final Scenario scenario)
    {
        Driver.tearDown(scenario);
    }

}
