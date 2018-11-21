package com.ckeiner.testbddy.dogtests.tests;

import static com.ckeiner.testbddy.api.BddSuite.feature;
import static com.ckeiner.testbddy.api.BddSuite.given;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.withData;

import org.junit.Test;

import com.ckeiner.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.ckeiner.testbddy.core.bdd.steps.Steps;

public class Pending
{
    @Test
    public void foo()
    {
        feature("Pending Feature").test();
        feature("Pending Scenario", () -> scenario("Eating a certain amount of cucumbers", (Steps) null),
                () -> scenario("Eating a certain amount of cucumbers", (OutlineDescriptor<Object>) null),
                () -> scenario("Eating a certain amount of cucumbers", given("Some given", null)),
                () -> scenario("Eating a certain amount of cucumbers",
                        withData(new Object(), new Object(), new Object()).given("Some given", (Runnable) null)))
                                .test();
    }

}
