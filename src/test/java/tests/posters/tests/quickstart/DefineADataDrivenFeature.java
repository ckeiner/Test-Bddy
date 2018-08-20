package tests.posters.tests.quickstart;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;

/**
 * Defines simple features with scenarios, that use test data.
 * 
 * @author ckeiner
 */
public class DefineADataDrivenFeature extends AbstractExtentReportTest
{
    /**
     * Defines a simple feature with a scenario, that uses test data.
     * 
     * @author ckeiner
     */
    @Test
    public void testScenarioWithTestData()
    {
        //@formatter:off
        feature("Define a feature",
            () -> scenario("Define a scenario",
                    withData("Custom Output")
                    .given("Define a given step", () ->
                    {
                        System.out.println("Given I define some step");
                    })
                    .when("Define a when step", data ->
                    {
                        System.out.println("I define " + data);
                    })
                    .then("Define a then step", () ->
                    {
                        System.out.println("I should see my own data");
                    })
            )
        ).test();
        //@formatter:on
    }
}
