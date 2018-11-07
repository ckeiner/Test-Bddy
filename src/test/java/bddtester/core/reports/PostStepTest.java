package bddtester.core.reports;

import static com.ckeiner.testbddy.api.BddSuite.feature;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.withData;
import static com.ckeiner.testbddy.api.BddSuite.withDataOfType;

import org.junit.Assert;
import org.junit.Test;

import com.ckeiner.testbddy.api.AbstractExtentReportTest;
import com.ckeiner.testbddy.core.throwables.errors.FeatureError;

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
                    )
        ).test();
        //@formatter:on
    }

}
