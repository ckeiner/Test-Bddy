package tests.posters.tests.quickstart;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;

/**
 * Defines simple features with scenarios, that don't use test data.
 * 
 * @author ckeiner
 */
public class DefineFeature extends AbstractExtentReportTest
{
    /**
     * Define a simple feature with a scenario, that doesn't use test data.
     * 
     * @author ckeiner
     */
    @Test
    public void testScenarioWithoutData()
    {
        //@formatter:off
        feature("Define a feature", 
            () -> scenario("Define a scenario",
                    given("Define a given step", () ->
                    {
                        System.out.println("Given I define some step");
                    })
                    .when("Define a when step", () ->
                    {
                        System.out.println("I define a when step");
                    })
                    .then("Define a then step", () ->
                    {
                        System.out.println("I should define a then step");
                    })
                )
        ).test();
        //@formatter:on
    }
}
