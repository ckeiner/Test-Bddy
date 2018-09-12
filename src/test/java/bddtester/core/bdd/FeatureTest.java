package bddtester.core.bdd;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bddtester.api.AbstractAllureReportTest;
import bddtester.core.bdd.scenario.AbstractScenario;
import bddtester.core.bdd.scenario.Scenario;
import bddtester.core.bdd.steps.Steps;
import io.qameta.allure.Epic;

@Epic("epic")
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
    public void quickAllureTester()
    {
        feature("My first Feature", () -> scenario("A very usefull and nice scenario",
                withData("a", "b", "c").given("My first given step", () ->
                    {
                        System.out.println("given");
                    }).when("My first when step", () ->
                        {
                            System.out.println("3");
                        }).wip())).test();

        feature("My second Feature", () -> scenario("A very usefull and nice scenario",
                withData("a", "132", 12).given("My second given step", () ->
                    {
                        System.out.println("given");
                    }).when("My first when step", () ->
                        {
                            System.out.println("3");
                        }).wip())).test();
    }

    @Test
    public void someTest()
    {
        System.out.println("Test!");
    }
}
