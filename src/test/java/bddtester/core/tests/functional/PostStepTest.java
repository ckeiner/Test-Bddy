package bddtester.core.tests.functional;

import org.junit.Assert;
import org.junit.Test;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;
import static bddtester.api.BddSuite.withDataOfType;

import bddtester.api.AbstractExtentReportTest;

public class PostStepTest extends AbstractExtentReportTest
{
    @Test
    public void testPostSteps()
    {
        //@formatter:off
        feature("Trying to destroy my stuff",
            () -> scenario("Some Scenario",
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

}
