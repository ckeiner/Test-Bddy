package tests.posters.tests.simple;

import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;

import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.pageobjects.pages.browsing.RegisterPage;
import pom.posters.util.PosterUtils;
import tests.posters.testdata.LoginData;
import tests.posters.tests.AbstractExtentReportTest;

@Browser("Chrome_1500x1000")
public class ScenarioTest extends AbstractExtentReportTest
{
    @Test
    public void testScenario()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();

        //@formatter:off
        scenario("Test Model",
                given("Open HomePage", PosterUtils::openHomePage)
                .and("Homepage is validated", homepage::validateStructure)
                .when("Register is opened", homepage::clickRegister)
                .and("RegisterPage can be validated", registerPage::validateStructure)
        ).test();
        //@formatter:on
    }

    @Test
    public void testScenarioOutline()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();
        scenario("Test Type Model",
                withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret"))
                        .given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure)
                        .when("Register is opened", homepage::clickRegister)
                        .and("RegisterPage can be validated", registerPage::validateStructure)
                        .then("Data is printed to console:", (user) ->
                            {
                                final LoginData data = user;
                                System.out.println(data.toString());
                            })).test();
    }

}
