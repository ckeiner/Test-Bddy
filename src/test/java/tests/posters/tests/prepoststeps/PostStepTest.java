package tests.posters.tests.prepoststeps;

import static bddtester.api.BddSuite.background;
import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.postSteps;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.bdd.steps.Steps;
import posters.pom.pageobjects.pages.browsing.HomePage;
import posters.pom.pageobjects.pages.browsing.RegisterPage;
import posters.pom.util.PosterUtils;
import tests.posters.testdata.LoginData;

/**
 * Verifies that PostSteps are actually run after each scenario.
 * 
 * @author ckeiner
 *
 */
public class PostStepTest extends AbstractExtentReportTest
{
    @Test
    public void testPostStepNoTestdata()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();

        // @formatter:off
        feature("PreFeature - No Testdata",
                background(() -> given("Open Homepage", PosterUtils::openHomePage)
                    )
                ,
                postSteps(() -> given("PostStep Open Homepage", PosterUtils::openHomePage)
                                                  .when("There is a step in the postSteps", () -> {
                                                      System.out.println("I see the printed stuff");
                                                  }),
                                 () -> new Steps().and("Some other PostStep Step", () -> {
                                         System.out.println("Some print");
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
