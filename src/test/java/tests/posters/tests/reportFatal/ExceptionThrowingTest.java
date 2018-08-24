package tests.posters.tests.reportFatal;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.throwables.exceptions.FeatureException;
import posters.pom.pageobjects.pages.browsing.HomePage;
import posters.pom.pageobjects.pages.browsing.RegisterPage;
import posters.pom.util.PosterUtils;
import tests.posters.testdata.LoginData;

/**
 * Throws an exception. so the generated report has to report a test as fatal.
 * 
 * @author ckeiner
 */
public class ExceptionThrowingTest extends AbstractExtentReportTest
{
    @Test(expected = FeatureException.class)
    public void throwExceptionInStep()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();
        //@formatter:off
        feature("Describe a feature", () ->
                scenario("Use of Testdata", 
                        withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret"),
                                new LoginData("John", "Dorian", "john@dorian.com", "topsecret"))
                        .given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure)
                        .when("Register is opened", () -> {
                            homepage.clickRegister();
                            throw new IllegalArgumentException("Intentional ScenarioOutline Failure");
                        })
                        .and("RegisterPage can be validated", registerPage::validateStructure)
                        .then("Data is printed to console:", (user) ->
                        {
                            final LoginData data = user;
                            System.out.println(data.toString());
                        })
                ),

                () -> scenario("No Testdata",
                        given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure)
                        .when("Register is opened", () -> {
                            homepage.clickRegister();
                            throw new IllegalArgumentException("Intentional Scenario Failure");
                        })
                        .then("RegisterPage can be validated", registerPage::validateStructure)
                )).test();
        //@formatter:on
    }
}
