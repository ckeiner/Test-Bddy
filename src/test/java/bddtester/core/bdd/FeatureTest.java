package bddtester.core.bdd;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractAllureReportTest;
import bddtester.core.bdd.scenario.AbstractScenario;
import bddtester.core.bdd.scenario.Scenario;
import bddtester.core.bdd.steps.Steps;
import io.qameta.allure.Step;

public class FeatureTest extends AbstractAllureReportTest
{
    @Test
    public void verifyConstructor()
    {
        String scenarioDescription = "Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps();
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        scenarioList.add(new Scenario(scenarioDescription, steps));
        Feature feature = new Feature(featureDescription, null, null, scenarioList);

        Assert.assertEquals(1, feature.getScenarios().size());
    }

    @Test
    public void test()
    {

    }

    @Test
    @Step("Another step")
    public void doSomething()
    {
        feature("My first Feature",
                () -> scenario("A very usefull and nice scenario", given("My first given step", () ->
                    {
                        System.out.println("given");
                    }).when("My first when step", () ->
                        {
                            System.out.println("3");
                        }))).test();
    }

    @Test
    public void quickAllureTester()
    {
        feature("My first Feature",
                () -> scenario("A very usefull and nice scenario", given("My first given step", () ->
                    {
                        System.out.println("given");
                    }).when("My first when step", () ->
                        {
                            System.out.println("3");
                        }))).test();

        feature("My second Feature",
                () -> scenario("A very usefull and nice scenario", given("My second given step", () ->
                    {
                        System.out.println("given");
                    }).when("My first when step", () ->
                        {
                            System.out.println("3");
                        }))).test();

        feature("A feature").test();
    }

    @Test
    public void someTest()
    {
        System.out.println("Test!");
    }
}
