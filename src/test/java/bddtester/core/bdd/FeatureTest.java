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
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

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
    @Step("First step")
    @DisplayName("Some test name")
    @Description("Report")
    public void quickAllureTester()
    {
        feature("Some feature", () -> scenario("some scenario", given("some feature", () ->
            {
                System.out.println("given");
            }).when("I do nothing", () ->
                {
                    System.out.println("when");
                }))).test();
    }

}
