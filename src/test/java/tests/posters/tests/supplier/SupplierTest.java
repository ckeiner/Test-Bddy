package tests.posters.tests.supplier;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.util.PosterUtils;

public class SupplierTest extends AbstractExtentReportTest
{
    @Test
    public void testAnotherFeature()
    {
        HomePage homepage = new HomePage();
        //@formatter:off
        feature("Supplier Feature",
                () -> scenario("Scenario 1",
                                given("Open Posters", PosterUtils::openHomePage)
                                .when("Click Cat", () -> {
                                    homepage.topNavigation.clickCategory("World of Nature");
                                })
                    )
                ,
                () -> scenario("Scenario 1",
                            withData(8, 213, 433)
                                        .given("Open Posters", PosterUtils::openHomePage)
                                        .when("Click Cat", () -> {
                                            homepage.topNavigation.clickCategory("World of Nature");
                                        })
                                        .then("Print the current test data", data -> {
                                            System.out.println(data);
                                        })
                                )
        ).test();
        //@formatter:on
    }

}
