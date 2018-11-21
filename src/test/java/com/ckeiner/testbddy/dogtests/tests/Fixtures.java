package com.ckeiner.testbddy.dogtests.tests;

import static com.ckeiner.testbddy.api.BddSuite.afters;
import static com.ckeiner.testbddy.api.BddSuite.befores;
import static com.ckeiner.testbddy.api.BddSuite.feature;
import static com.ckeiner.testbddy.api.BddSuite.given;
import static com.ckeiner.testbddy.api.BddSuite.scenario;
import static com.ckeiner.testbddy.api.BddSuite.then;
import static com.ckeiner.testbddy.api.BddSuite.when;
import static com.ckeiner.testbddy.api.BddSuite.withData;

import org.junit.Assert;
import org.junit.Test;

import com.ckeiner.testbddy.core.bdd.Feature;

public class Fixtures
{
    Feature feature;

    boolean exception = false;

    @Test
    public void testBefore()
    {
        //@formatter:off
        feature("Before executes before the actual scenario",
                () -> scenario("A scenario with before",
                        given("A feature with before is defined", () -> {
                            feature = feature("A feature with before",
                                            befores(
                                                () -> when("Exception is set to true", () -> {
                                                    exception = true;
                                                })
                                            ),
                                            () -> scenario("A scenario with before",
                                                    then("exception should be true", () -> {
                                                        Assert.assertTrue(exception);
                                                    })
                                                ));
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    ),
                () -> scenario("A scenario outline with before",
                        given("A feature with before is defined", () -> {
                            feature = feature("A feature with before",
                                            befores(() ->
                                                when("Exception is set to true", () -> {
                                                    exception = true;
                                                   //  exception = Boolean.logicalXor(exception, true);
                                                })
                                            ),
                                            () -> scenario("A scenario with before",
                                                    withData("Some test data", "Some other test data")
                                                    .then("exception should be true", () -> {
                                                        Assert.assertTrue(exception);
                                                    })
                                                ));
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    )
        ).test();
        //@formatter:on
    }

    @Test
    public void testAfter()
    {
        //@formatter:off
        feature("After executes after the actual scenario",
                () -> scenario("A scenario with after",
                        given("A feature with after is defined", () -> {
                            feature = feature("A feature with after",
                                            afters(
                                                () -> then("Exception should be true", () -> {
                                                    Assert.assertTrue(exception);
                                                })
                                            ),
                                            () -> scenario("A scenario with after",
                                                    when("Exception is set to true", () -> {
                                                        exception = true;
                                                    })
                                                ));
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    ),
                () -> scenario("A scenarioOutline with after",
                        given("A feature with after is defined", () -> {
                            feature = feature("A feature with after",
                                            afters(
                                                () -> then("Exception should be true", () -> {
                                                    Assert.assertTrue(exception);
                                                })
                                            ),
                                            () -> scenario("A scenario with after",
                                                    withData("Some test data", "Some other test data")
                                                    .when("Exception is set to true", () -> {
                                                        exception = true;
                                                    })
                                                ));
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    )
        ).test();
        //@formatter:on
    }

    @Test
    public void testBeforeAndAfter()
    {
        //@formatter:off
        feature("Fixtures are executed around the scenario",
                () -> scenario("A scenario with before and after",
                        given("A feature with before and after is defined", () -> {
                            feature = feature("A feature with before and after",
                                            befores(
                                                () -> when("Exception is set to true", () -> {
                                                    exception = true;
                                                })
                                            ),
                                            afters(
                                                () -> then("exception should be true", () -> {
                                                    Assert.assertTrue(exception);
                                                })
                                            ),
                                            () -> scenario("A scenario",
                                                    when("An empty scenario is executed", () -> {
                                                    })
                                                )
                                        );
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    ),
                () -> scenario("A scenario with before and after",
                        given("A feature with before and after is defined", () -> {
                            feature = feature("A feature with before and after",
                                            befores(
                                                () -> when("Exception is set to true", () -> {
                                                    exception = true;
                                                })
                                            ),
                                            afters(
                                                () -> then("exception should be true", () -> {
                                                    Assert.assertTrue(exception);
                                                })
                                            ),
                                            () -> scenario("A scenarioOutline",
                                                    withData("Some test data", "Some other test data")
                                                    .when("An empty scenario is executed", () -> {
                                                    })
                                                )
                                        );
                        })
                        .then("The feature is run without issues", () -> {
                            feature.test();
                        })
                    )
        ).test();
        //@formatter:on
    }
}
