package tests.posters.tests.runner;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.runner.RunWith;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.bdd.Feature;
import bddtester.core.runner.JUnitFeatureRunner;
import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.pageobjects.pages.browsing.RegisterPage;
import pom.posters.util.PosterUtils;
import tests.posters.testdata.LoginData;

/**
 * Simple test so I can try to use my own feature runner.
 * 
 * @author ckeiner
 *
 */
@RunWith(JUnitFeatureRunner.class)
public class CustomTestRunner extends AbstractExtentReportTest
{
    /**
     * Saves a feature in a static field
     */
    public static Feature feature;

    /**
     * Defines the feature in an initializing block
     */
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();
        //@formatter:off
        CustomTestRunner.feature =
            feature("Describe a feature",
                    // Scenario 1
                () -> scenario("Use of Testdata",
                        withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret")).given("Open HomePage", PosterUtils::openHomePage)
                                    .and("Homepage is validated", homepage::validateStructure)
                                    .when("Register is opened", homepage::clickRegister)
                                    .and("RegisterPage can be validated", registerPage::validateStructure)
                                    .then("Data is printed to console:", (user) ->
                                    {
                                        System.out.println(user.toString());
                                        // throw new IllegalArgumentException();
                                    })
                ),
                // Scenario 2
                () -> scenario("No Testdata",
                        given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure)
                        .when("Register is opened", homepage::clickRegister)
                        .then("RegisterPage can be validated", registerPage::validateStructure)
                        )
                );
        //@formatter:on
    }
}
