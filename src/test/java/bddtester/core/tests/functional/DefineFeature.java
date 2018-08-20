package bddtester.core.tests.functional;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bddtester.core.bdd.Feature;
import bddtester.core.bdd.scenario.AbstractScenario;
import bddtester.core.bdd.scenario.Scenario;

public class DefineFeature
{
    @Test
    public void testAFeature()
    {
        final String featureDescription = "Define a feature";
        final String scenarioDescription = "Define a scenario";
        final String givenDescription = "Define a given step";
        final String whenDescription = "Define a when step";
        final String thenDescription = "Define a then step";

        Feature definedFeature = feature(featureDescription,
                () -> scenario(scenarioDescription, given(givenDescription, () ->
                    {
                        System.out.println("Given I define some step");
                    }).when(whenDescription, () ->
                        {
                            System.out.println("I define a when step");
                        }).then(thenDescription, () ->
                            {
                                System.out.println("I should define a then step");
                            })));
        Assert.assertEquals(featureDescription, definedFeature.getDescription());
        List<AbstractScenario> scenarios = definedFeature.getScenarios();
        Assert.assertEquals(1, scenarios.size());
        Assert.assertTrue(scenarios.get(0) instanceof Scenario);
        Scenario scenario = (Scenario) scenarios.get(0);
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
    }
}
