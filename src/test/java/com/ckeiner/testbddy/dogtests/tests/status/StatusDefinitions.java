package com.ckeiner.testbddy.dogtests.tests.status;

import static com.ckeiner.testbddy.api.BddSuite.given;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.withData;

import com.ckeiner.testbddy.api.AbstractExtentReportTest;
import com.ckeiner.testbddy.core.bdd.Feature;
import com.ckeiner.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.ckeiner.testbddy.core.bdd.scenario.Scenario;
import com.ckeiner.testbddy.core.bdd.scenario.ScenarioOutline;
import com.ckeiner.testbddy.core.bdd.steps.Steps;
import com.ckeiner.testbddy.core.bdd.steps.TypeSteps;

public abstract class StatusDefinitions extends AbstractExtentReportTest
{
    Feature feature;

    Scenario scenario;

    ScenarioOutline<Object> scenarioOutline;

    Steps steps;

    TypeSteps<Object> typeSteps;

    protected Scenario scenarioWithoutSteps()
    {
        return scenario("Scenario without Steps", (Steps) null);
    }

    protected ScenarioOutline<Object> scenarioOutlineWithoutSteps()
    {
        return scenario("Scenario Outline without Steps", (OutlineDescriptor<Object>) null);
    }

    protected Steps stepsWithoutBehavior()
    {
        return given("Given without behavior", null);
    }

    protected Steps stepsWithBehavior()
    {
        return given("Given with behavior", () ->
            {
            });
    }

    protected Steps stepsThatThrowsAnError()
    {
        return given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    protected OutlineDescriptor<Object> descriptorWithStepsThatThrowsAnError()
    {
        return withData(new Object(), new Object(), new Object()).given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            });
    }

    protected OutlineDescriptor<Object> descriptorWithStepsWithoutBehavior()
    {
        return withData(new Object(), new Object(), new Object()).given("Given with behavior", () ->
            {
            });
    }

    protected TypeSteps<Object> typeStepsWithoutBehavior()
    {
        return withData(new Object(), new Object(), new Object()).given("Some given", (Runnable) null).getSteps();
    }

    protected TypeSteps<Object> typeStepsThatThrowsAnError()
    {
        return withData(new Object()).given("Given with behavior", () ->
            {
                throw new IllegalStateException("Should not be thrown but is");
            }).getSteps();
    }

    protected TypeSteps<Object> typeStepsWithBehavior()
    {
        return withData(new Object()).given("Given with behavior", () ->
            {
            }).getSteps();
    }

}
