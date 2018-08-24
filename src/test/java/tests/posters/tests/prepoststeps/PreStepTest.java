package tests.posters.tests.prepoststeps;

import static bddtester.api.BddSuite.and;
import static bddtester.api.BddSuite.background;
import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import posters.pom.pageobjects.pages.browsing.HomePage;
import posters.pom.pageobjects.pages.browsing.RegisterPage;
import posters.pom.util.PosterUtils;
import tests.posters.testdata.LoginData;

/**
 * Verifies that PreSteps are actually run before each scenario.
 * 
 * @author ckeiner
 */
public class PreStepTest extends AbstractExtentReportTest
{
    @Test
    public void testPreStepNoTestdata()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();

        // @formatter:off
        feature("PreFeature - No Testdata",
                background(() -> given("Open Homepage", PosterUtils::openHomePage)
                                                  .when("There is another step", () -> {
                                                      System.out.println("I can print stuff");
                                                  }),
                                 () -> and("Some other Background Step", () -> {
                                         System.out.println("some print");
                                         }
                                        )
                                 ),
                
                () -> scenario("Use of Testdata",
                        withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret")).and("Homepage is validated", homepage::validateStructure)
                             .when("Register is opened", homepage::clickRegister)
                             .and("RegisterPage can be validated", registerPage::validateStructure)
                             .then("Data is printed to console:", (user) ->
                                 {
                                     System.out.println(user.toString());
                                 })
                ),

                () -> scenario("No Testdata", 
                            given("Homepage is validated", homepage::validateStructure)
                            .when("Register is opened", homepage::clickRegister)
                            .then("RegisterPage can be validated", registerPage::validateStructure)
                )
            ).test();
        // @formatter:on
    }
}
