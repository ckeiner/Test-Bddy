package bddtester.core.bdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ckeiner.testbddy.core.bdd.Feature;
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
        Feature feature = new Feature(featureDescription, null, null, null);

        Assert.assertEquals(featureDescription, feature.getDescription());
        Assert.assertNull(feature.getScenarios());
        // TODO discuss if test and pending should be part of this
    }

    /**
     * Verifies that a feature can contain a scenario and doesn't change its values
     */
    @Test
    public void canContainAScenario()
    {
        String scenarioDescription = "Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps();
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        Scenario scenario = new Scenario(scenarioDescription, steps);
        scenarioList.add(scenario);
        Feature feature = new Feature(featureDescription, null, null, scenarioList);

        Assert.assertEquals(featureDescription, feature.getDescription());
        Assert.assertTrue(feature.getStatus().isEmpty());
        Assert.assertEquals(scenarioList, feature.getScenarios());
        Assert.assertEquals(1, feature.getScenarios().size());
        Assert.assertEquals(scenario, feature.getScenarios().get(0));
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
        Feature feature = new Feature(featureDescription, null, null, scenarioList);

        Assert.assertEquals(featureDescription, feature.getDescription());
        Assert.assertTrue(feature.getStatus().isEmpty());
        Assert.assertEquals(scenarioList, feature.getScenarios());
        Assert.assertEquals(2, feature.getScenarios().size());
        Assert.assertEquals(scenario, feature.getScenarios().get(0));
        Assert.assertEquals(scenarioDescription, feature.getScenarios().get(0).getDescription());
    }

}
