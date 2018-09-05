package bddtester.core.bdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bddtester.core.bdd.scenario.AbstractScenario;
import bddtester.core.bdd.scenario.Scenario;
import bddtester.core.bdd.steps.Steps;

public class FeatureTest
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

}
