package com.ckeiner.testbddy.dogtests.tests;

import static com.ckeiner.testbddy.api.BddSuite.feature;
import static com.ckeiner.testbddy.api.BddSuite.given;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.withData;

import org.junit.Assert;
import org.junit.Test;

import com.ckeiner.testbddy.api.AbstractExtentReportTest;
import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.ckeiner.testbddy.core.bdd.scenario.Scenario;
import com.ckeiner.testbddy.core.bdd.scenario.ScenarioOutline;
import com.ckeiner.testbddy.core.bdd.status.Status;
import com.ckeiner.testbddy.core.bdd.steps.Steps;
import com.ckeiner.testbddy.core.bdd.steps.TypeSteps;

public class StatusTest extends AbstractExtentReportTest
{
    Feature feature;

    Scenario scenario;

    ScenarioOutline<Object> scenarioOutline;

    Steps steps;

    TypeSteps<Object> typeSteps;

    @Test
    public void pending()
    {
        //@formatter:off
        feature("The pending status is added correctly",
                () -> scenario("A feature without scenarios is defined", 
                        given("A feature without scenarios is defined", () -> {
                            feature  = featureWithoutScenarios();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be pending", () -> {
                            Assert.assertTrue(feature.getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A scenario without steps is defined",
                        given("A scenario without steps is defined", () -> {
                            scenario = scenarioWithoutSteps();
                        })
                        .when("The scenario is run", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be pending", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A scenarioOutline without steps is defined",
                        given("A scenarioOutline without steps is defined", () -> {
                            scenarioOutline = scenarioOutlineWithoutSteps();
                        })
                        .when("The scenarioOutline is run", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenarioOutline should be pending", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A step without behaviour is defined",
                        given("A step without behaviour is defined", () -> {
                            steps = stepsWithoutBehavior();
                        })
                        .when("The step is run", () -> {
                            steps.test();
                        })
                        .then("The step should be pending", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A typeStep without behaviour is defined",
                        given("A typeStep without behaviour is defined", () -> {
                            typeSteps = typeStepsWithoutBehavior();
                        })
                        .when("The typeStep is run", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be pending", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.PENDING));
                        })
                )
        ).test();
        //@formatter:on
    }

    @Test
    public void ignore()
    {
        //@formatter:off
        feature("The IGNORE status ignores the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature without scenarios is ignored", 
                        given("A feature without scenarios is ignored", () -> {
                            feature  = featureWithoutScenarios().ignore();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be ignored", () -> {
                            Assert.assertTrue(feature.getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A feature with scenarios is ignored", 
                        given("A feature with scenarios is ignored", () -> {
                            feature  = feature("Feature with Scenario",
                                            () -> scenario("A scenario that throws an error",
                                                    stepsThatThrowsAnError()
                                            )
                                        ).ignore();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be ignored", () -> {
                            // Verify feature is marked skipped
                            Assert.assertTrue(feature.getStatus().contains(Status.IGNORE));
                            // Verify the scenario does not have the status skipped
                            Assert.assertFalse(feature.getScenarios().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario without steps is ignored",
                        given("A scenario without steps is ignored", () -> {
                            scenario = scenarioWithoutSteps().ignore();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be ignored", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A scenario with steps is ignored",
                        given("A scenario with steps is ignored", () -> {
                            scenario = scenario("Scenario with Steps", stepsThatThrowsAnError()).ignore();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be ignored", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.IGNORE));
                            Assert.assertFalse(scenario.getSteps().getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * Scenario Outline
                 */
                () -> scenario("A scenario outline without steps is ignored",
                        given("A scenario outline without steps is ignored", () -> {
                            scenarioOutline = scenarioOutlineWithoutSteps().ignore();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be ignored", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A scenario outline with steps is ignored",
                        given("A scenario outline with steps is ignored", () -> {
                            scenarioOutline = scenario("Scenario without Steps", descriptorWithStepsThatThrowsAnError()).ignore();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be ignored", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.IGNORE));
                            Assert.assertFalse(scenarioOutline.getSteps().getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * Steps
                 */
                () -> scenario("A step without behaviour is ignored",
                        given("A step without behaviour is ignored", () -> {
                            steps = stepsWithoutBehavior().ignore();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be ignored", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A step with behaviour is skipped",
                        given("A step with behaviour is skipped", () -> {
                            steps = stepsThatThrowsAnError().ignore();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be skipped", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * TypeSteps
                 */
                () -> scenario("A typeStep without behaviour is skipped",
                        given("A typeStep without behaviour is skipped", () -> {
                            typeSteps = typeStepsWithoutBehavior().ignore();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be skipped", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A typeStep with behaviour is ignored",
                        given("A typeStep with behaviour is ignored", () -> {
                            typeSteps = typeStepsThatThrowsAnError().ignore();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be ignored", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                )
        ).test();
        //@formatter:on
    }

    @Test
    public void skip()
    {
        //@formatter:off
        feature("The SKIP status skips the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature without scenarios is skipped", 
                        given("A feature without scenarios is skipped", () -> {
                            feature  = featureWithoutScenarios().skip();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be skipped", () -> {
                            Assert.assertTrue(feature.getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A feature with scenarios is skipped", 
                        given("A feature with scenarios is skipped", () -> {
                            feature  = feature("Feature with Scenario",
                                            () -> scenario("A scenario that throws an error",
                                                    stepsThatThrowsAnError()
                                            )
                                        ).skip();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be skipped", () -> {
                            // Verify feature is marked skipped
                            Assert.assertTrue(feature.getStatus().contains(Status.SKIP));
                            // Verify the scenario does not have the status skipped
                            Assert.assertFalse(feature.getScenarios().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario without steps is skipped",
                        given("A scenario without steps is skipped", () -> {
                            scenario = scenarioWithoutSteps().skip();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be skipped", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A scenario with steps is skipped",
                        given("A scenario with steps is skipped", () -> {
                            scenario = scenario("Scenario with Steps", stepsThatThrowsAnError()).skip();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be skipped", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.SKIP));
                            Assert.assertFalse(scenario.getSteps().getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * Scenario Outline
                 */
                () -> scenario("A scenario outline without steps is skipped",
                        given("A scenario outline without steps is skipped", () -> {
                            scenarioOutline = scenarioOutlineWithoutSteps().skip();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be skipped", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A scenario outline with steps is skipped",
                        given("A scenario outline with steps is skipped", () -> {
                            scenarioOutline = scenario("Scenario without Steps", descriptorWithStepsThatThrowsAnError()).skip();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be skipped", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.SKIP));
                            Assert.assertFalse(scenarioOutline.getSteps().getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * Steps
                 */
                () -> scenario("A step without behaviour is skipped",
                        given("A step without behaviour is skipped", () -> {
                            steps = stepsWithoutBehavior().skip();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be skipped", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A step with behaviour is skipped",
                        given("A step with behaviour is skipped", () -> {
                            steps = stepsThatThrowsAnError().skip();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be skipped", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * TypeSteps
                 */
                () -> scenario("A typeStep without behaviour is skipped",
                        given("A typeStep without behaviour is skipped", () -> {
                            typeSteps = typeStepsWithoutBehavior().skip();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be skipped", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A typeStep with behaviour is skipped",
                        given("A typeStep with behaviour is skipped", () -> {
                            typeSteps = typeStepsThatThrowsAnError().skip();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be skipped", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                )
        ).test();
        //@formatter:on
    }

    @Test
    public void wip()
    {
        //@formatter:off
        feature("The WIP status is appended to the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature without scenarios is wip", 
                        given("A feature without scenarios is wip", () -> {
                            feature  = featureWithoutScenarios().wip();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be wip", () -> {
                            Assert.assertTrue(feature.getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A feature with scenarios is wip", 
                        given("A feature with scenarios is wip", () -> {
                            feature  = feature("Feature with Scenario",
                                            () -> scenario("A scenario that throws an error",
                                                    stepsWithoutBehavior()
                                            )
                                        ).wip();
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be wip", () -> {
                            // Verify feature is marked skipped
                            Assert.assertTrue(feature.getStatus().contains(Status.WIP));
                            // Verify the scenario does not have the status skipped
                            Assert.assertFalse(feature.getScenarios().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario without steps is wip",
                        given("A scenario without steps is wip", () -> {
                            scenario = scenarioWithoutSteps().wip();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be wip", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A scenario with steps is skipped",
                        given("A scenario with steps is skipped", () -> {
                            scenario = scenario("Scenario with Steps", stepsWithoutBehavior()).wip();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be wip", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.WIP));
                            Assert.assertFalse(scenario.getSteps().getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * Scenario Outline
                 */
                () -> scenario("A scenario outline without steps is wip",
                        given("A scenario outline without steps is wip", () -> {
                            scenarioOutline = scenarioOutlineWithoutSteps().wip();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be wip", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A scenario outline with steps is wip",
                        given("A scenario outline with steps is wip", () -> {
                            scenarioOutline = scenario("Scenario without Steps", descriptorWithStepsWithoutBehavior()).wip();
                        })
                        .when("The scenario outline is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenario outline should be wip", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.WIP));
                            Assert.assertFalse(scenarioOutline.getSteps().getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * Steps
                 */
                () -> scenario("A step without behaviour is wip",
                        given("A step without behaviour is wip", () -> {
                            steps = stepsWithoutBehavior().wip();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be wip", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A step with behaviour is wip",
                        given("A step with behaviour is wip", () -> {
                            steps = stepsWithBehavior().wip();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("The step should be wip", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * TypeSteps
                 */
                () -> scenario("A typeStep without behaviour is skipped",
                        given("A typeStep without behaviour is skipped", () -> {
                            typeSteps = typeStepsWithoutBehavior().wip();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be skipped", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A typeStep with behaviour is wip",
                        given("A typeStep with behaviour is wip", () -> {
                            typeSteps = typeStepsWithBehavior().wip();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.withData(new Object()).test();
                        })
                        .then("The typeStep should be wip", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                )
        ).test();
        //@formatter:on
    }

    private Scenario scenarioWithoutSteps()
    {
        return scenario("Scenario without Steps", (Steps) null);
    }

    private ScenarioOutline<Object> scenarioOutlineWithoutSteps()
    {
        return scenario("Scenario Outline without Steps", (OutlineDescriptor<Object>) null);
    }

    private Feature featureWithoutScenarios()
    {
        return feature("Feature without Scenarios");
    }

    private Steps stepsWithoutBehavior()
    {
        return given("Given without behavior", null);
    }

    private Steps stepsWithBehavior()
    {
        return given("Given with behavior", () ->
            {
            });
    }

    private Steps stepsThatThrowsAnError()
    {
        return given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    private OutlineDescriptor<Object> descriptorWithStepsThatThrowsAnError()
    {
        return withData(new Object(), new Object(), new Object()).given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    private OutlineDescriptor<Object> descriptorWithStepsWithoutBehavior()
    {
        return withData(new Object(), new Object(), new Object()).given("Given with behavior", () ->
            {
            });
    }

    private TypeSteps<Object> typeStepsWithoutBehavior()
    {
        return withData(new Object(), new Object(), new Object()).given("Some given", (Runnable) null).getSteps();
    }

    private TypeSteps<Object> typeStepsThatThrowsAnError()
    {
        return withData(new Object()).given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            }).getSteps();
    }

    private TypeSteps<Object> typeStepsWithBehavior()
    {
        return withData(new Object()).given("Given with behavior", () ->
            {
            }).getSteps();
    }
}
