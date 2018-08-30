package bddtester.core.tests.reports;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;
import static bddtester.api.BddSuite.withDataOfType;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.throwables.errors.FeatureError;

public class PostStepTest extends AbstractExtentReportTest
{
    @Test(expected = FeatureError.class)
    public void testPostStepsAreShown()
    {
        //@formatter:off
        feature("Report when one test datum fails",
            () -> scenario("All errors are shown",
                    withData(5, 8, 3)
                    .given("I dont fail with a 8",
                        data ->
                        {
                            Assert.assertNotEquals(new Integer(8), data);
                        })
            ).postSteps(
                    () -> withDataOfType(Integer.class)
                            .given("I fail every time <data.toString()>", data -> {
                                throw new IllegalArgumentException("I have to fail here");
                            })
                            .getSteps()
                    )
        ).test();
        //@formatter:on
    }

    @Test(expected = FeatureError.class)
    public void testPostSteps()
    {
        //@formatter:off
        feature("PostStep Execution",
            () -> scenario("PostSteps are executed for each",
                    withData(5, 8, 3)
                    .given("I dont fail with a 8",
                        data ->
                        {
                            Assert.assertNotEquals(new Integer(8), data);
                        })
                    .when("I still execute this step", () -> {
                        
                    })
            ).postSteps(
                    () -> withDataOfType(Integer.class)
                            .given("I am executed all the time", () -> {
                                
                            })
                            .getSteps()
                    )
        ).test();
        //@formatter:on
    }

}
