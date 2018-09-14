package bddtester.core.reports.allure;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractAllureReportTest;

public class AllureReportTest extends AbstractAllureReportTest
{
    int a, b = 1, c = 0;

    //@formatter:off
    @Test
    public void passingFeature()
    {
        feature("My passing feature", 
                () -> scenario("My first scenario", 
                        given("I have sth.", () ->
                        {
                            a = b +c;
                        })
                        .when("I do sth.", () ->
                        {
                            a = a*a;
                        }).wip()
                        .then("I should have done sth", () ->
                        {
                            Assert.assertEquals((Integer)1, (Integer)a);
                        })
                        )
                ).test();
    }

    @Test
    public void failingFeature()
    {
        feature("My failing Feature", 
                () -> scenario("My second scenario", 
                        given("I have sth.", () ->
                        {
                            a = b +c;
                        })
                        .when("I do sth. that is failing", () ->
                        {
                            Assert.assertTrue(false);
                        })
                        .then("My report should show this", () -> 
                        {
                            
                        })
                )
                ).test();

    }
    //@formatter:on
}
