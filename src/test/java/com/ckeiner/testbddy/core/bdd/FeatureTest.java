package com.ckeiner.testbddy.core.bdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ckeiner.testbddy.core.bdd.scenario.AbstractScenario;
import com.ckeiner.testbddy.core.bdd.scenario.Scenario;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class FeatureTest
{
    /**
     * Verifies that a features can contain no scenarios
     */
    @Test
    public void canContainNoScenarios()
    {
        String featureDescription = "Feature Description";
        // Create feature consisting only of the description
        Feature feature = new Feature(featureDescription, null);

        Assert.assertEquals(featureDescription, feature.getDescription());
        Assert.assertNull(feature.getScenarios());
    }

    /**
     * Verifies that a feature can contain a scenario and doesn't change its values
     */
    @Test
    public void canContainAScenario()
    {
        String scenarioDescription = "Scenario Description";
        String featureDescription = "Feature Description";
        // Create empty steps
        Steps steps = new Steps();
        // Create Scenario with steps and a description
        Scenario scenario = new Scenario(scenarioDescription, steps);
        // Create list to give to the feature
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        scenarioList.add(scenario);
        // Create feature consisting of the description and the scenarioList
        Feature feature = new Feature(featureDescription, scenarioList);

        // Verify the description remains the same
        Assert.assertEquals(featureDescription, feature.getDescription());
        // Verify the feature has no status
        Assert.assertTrue(feature.getStatus().isEmpty());
        // Verify the list of scenarios is correct
        Assert.assertEquals(scenarioList, feature.getScenarios());
        // Verify the size of the list is still correct
        Assert.assertEquals(1, feature.getScenarios().size());
        // Verify the scenario is the same
        Assert.assertEquals(scenario, feature.getScenarios().get(0));
        // Verify the description of the scenario remains the same
        Assert.assertEquals(scenarioDescription, feature.getScenarios().get(0).getDescription());
    }

    /**
     * Verifies that a feature can contain multiple scenarios
     */
    @Test
    public void canContainMultipleScenarios()
    {
        String scenarioDescription = "Scenario Description";
        String anotherScenarioDescription = "Another Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps();
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        Scenario scenario = new Scenario(scenarioDescription, steps);
        Scenario anotherScenario = new Scenario(anotherScenarioDescription, steps);
        scenarioList.add(scenario);
        scenarioList.add(anotherScenario);
        Feature feature = new Feature(featureDescription, scenarioList);

        // Verify the description remains the same
        Assert.assertEquals(featureDescription, feature.getDescription());
        // Verify the feature has no status
        Assert.assertTrue(feature.getStatus().isEmpty());
        // Verify the list of scenarios is correct
        Assert.assertEquals(scenarioList, feature.getScenarios());
        // Verify the size of the list is still correct
        Assert.assertEquals(2, feature.getScenarios().size());
        // Verify the scenario is the same
        Assert.assertEquals(scenario, feature.getScenarios().get(0));
        // Verify the description of the first scenario remains the same
        Assert.assertEquals(scenarioDescription, feature.getScenarios().get(0).getDescription());
        // Verify the description of the second scenario remains the same
        Assert.assertEquals(anotherScenarioDescription, feature.getScenarios().get(1).getDescription());
    }

}
