package com.ckeiner.testbddy.dogtests.tests.status;

import static com.ckeiner.testbddy.api.BddSuite.feature;
import static com.ckeiner.testbddy.api.BddSuite.given;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.with;

import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.ckeiner.testbddy.core.bdd.scenario.Scenario;
import com.ckeiner.testbddy.core.bdd.scenario.ScenarioOutline;
import com.ckeiner.testbddy.core.bdd.steps.Steps;
import com.ckeiner.testbddy.core.bdd.steps.TypeSteps;

public abstract class StatusDefinitions
{

    protected Steps stepsWithEmtpyBehavior()
    {
        return given("Step with emtpy behavior", () ->
            {
            });
    }

    protected OutlineDescriptor<Object> descriptorWithEmtpyBehavior()
    {
        return with(new Object()).given("Type Step with emtpy behavior", data ->
            {
            });
    }

    protected TypeSteps<Object> typeStepsWithEmtpyBehavior()
    {
        return descriptorWithEmtpyBehavior().getSteps();
    }

    protected Scenario scenarioWithEmtpyBehavior()
    {
        return scenario("Scenario with emtpy behavior", stepsWithEmtpyBehavior());
    }

    protected ScenarioOutline<Object> outlineWithEmtpyBehavior()
    {
        return scenario("Scenario with emtpy behavior", descriptorWithEmtpyBehavior());
    }

    protected Feature featureWithEmtpyBehavior()
    {
        return feature("Feature with empty Behavior", () -> scenarioWithEmtpyBehavior());
    }

    /*
     * Defined from earlier
     */

    // protected Scenario scenarioWithoutSteps()
    // {
    // return scenario("Scenario without Steps", (Steps) null);
    // }
    //
    // protected ScenarioOutline<Object> scenarioOutlineWithoutSteps()
    // {
    // return scenario("Scenario Outline without Steps", (OutlineDescriptor<Object>)
    // null);
    // }
    //
    // protected Steps stepsWithoutBehavior()
    // {
    // return given("Given without behavior", null);
    // }

    protected Steps stepsThatThrowsAnError()
    {
        return given("Step that throws an error", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    protected OutlineDescriptor<Object> descriptorThatThrowsAnError()
    {
        return with(new Object()).given("TypeStep that throws an error", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    protected TypeSteps<Object> typeStepsThatThrowsAnError()
    {
        return descriptorThatThrowsAnError().getSteps().withData(new Object());
    }

    protected Scenario scenarioThatThrowsAnError()
    {
        return scenario("Scenario that throws an error", stepsThatThrowsAnError());
    }

    protected ScenarioOutline<Object> outlineThatThrowsAnError()
    {
        return scenario("Scenario Outline that throws an error", descriptorThatThrowsAnError());
    }

    protected Feature featureThatThrowsAnError()
    {
        return feature("Feature that throws an error", () -> scenarioThatThrowsAnError());
    }

    // protected TypeSteps<Object> typeStepsWithoutBehavior()
    // {
    // return withData(new Object(), new Object(), new Object()).given("Some given",
    // (Runnable) null).getSteps();
    // }

}
