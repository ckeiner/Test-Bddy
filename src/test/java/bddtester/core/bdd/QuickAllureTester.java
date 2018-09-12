package bddtester.core.bdd;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import org.junit.Test;

import bddtester.core.reporting.allurereports.AbstractAllureReportTest;

public class QuickAllureTester extends AbstractAllureReportTest
{
    @Test
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
