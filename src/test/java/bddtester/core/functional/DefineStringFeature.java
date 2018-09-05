package bddtester.core.functional;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Assert;
import org.junit.Test;

public class DefineStringFeature
{
    @Test
    public void ShouldBeAbleToDefineString()
    {
        String testString = "Teststring";
        String otherTestString = "Another String";

        feature("Some feature",

                () -> scenario("That a single string can easily be passed",
                        withData(testString).given("The String is there", data ->
                            {
                                Assert.assertEquals(testString, data);
                            })),

                () -> scenario("That multiple Strings have two executions",
                        withData(testString, otherTestString).given("Any of the Strings is there", data ->
                            {
                                if (!(data.equals(testString) || data.equals(otherTestString)))
                                {
                                    throw new AssertionError(data + " does not match any of the teststrings");
                                }
                            }))).test();
    }

}