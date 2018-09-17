package bddtester.core.reports.allure;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractAllureReportTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.model.Label;

public class AllureReportTest extends AbstractAllureReportTest
{
    int a, b = 1, c = 0;

    @Attachment
    public void doNothing()
    {

    }

    @Attachment("attachment {noUse}")
    public String produceAAttachment(String noUse)
    {
        return "a new attachment";
    }

    @Test
    @Step("some step")
    public void testAllure()
    {
        produceAAttachment("nothing");
        doNothing();
    }

    //@formatter:off
    @Test
    public void passingFeature()
    {
        produceAAttachment("somesth");
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
        Label label = new Label();
        label.setValue("some value");
        label.setName("some name");
         Allure.addLabels(label);
         Allure.addDescription("some description on failure");
    }

    @Test
    public void failingFeature()
    {
        feature("My failing Feature", 
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
                        ),
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
