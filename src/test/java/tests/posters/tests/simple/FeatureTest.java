package tests.posters.tests.simple;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.pageobjects.pages.browsing.RegisterPage;
import pom.posters.util.PosterUtils;
import tests.posters.testdata.LoginData;
import tests.posters.tests.AbstractExtentReportTest;

/**
 * Verifies features can be properly defined, tagged, etc.
 * 
 * @author ckeiner
 */
public class FeatureTest extends AbstractExtentReportTest
{
    // TODO better description
    /**
     * Verifies the definition of a feature works properly.
     */
    @Test
    public void testFeatureDefinition()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();
        //@formatter:off
        feature("Describe a feature",
                () -> scenario("Use testdata",
                        withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret"))
                        .given("String", data -> {
                            System.out.println("Custom String: " + data.getFirstName());
                        })
                        .and("I define an and", () -> {
                            System.out.println("This is the and");
                        })
                        .and("I open the Homepage", PosterUtils::openHomePage)
                        .when("I want data in my when", data -> {
                            System.out.println("I get data " + data.toString());
                        })
                        .and("I open register", homepage::clickRegister)
                        .then("I see the registerPage", registerPage::validateStructure)
                    )
            ,
            () -> scenario("No Testdata",
                        given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure)
                        .when("Register is opened", homepage::clickRegister)
                        .then("RegisterPage can be validated", registerPage::validateStructure)
                    )
            ).test();
        //@formatter:on
    }

    /**
     * Verifies the tags in a feature work as intended.
     */
    @Test
    public void testFeatureWithTags()
    {
        final HomePage homepage = new HomePage();
        final RegisterPage registerPage = new RegisterPage();
        //@formatter:off
        feature("Describe a feature",
                () -> scenario("Use testdata",
                        withData(new LoginData("Jane", "Doe", "jane@doe.com", "topsecret"))
                        .given("String", data -> {
                            System.out.println("Custom String: " + data.getFirstName());
                        }).skip()
                        .and("I define an and", () -> {
                            System.out.println("This is the and");
                        }).wip()
                        .and("I open the Homepage", PosterUtils::openHomePage)
                        .when("I want data in my when", data -> {
                            System.out.println("I get data " + data.toString());
                        }).ignore()
                        .and("I open register", homepage::clickRegister)
                        .then("I see the registerPage", registerPage::validateStructure)
                    ).wip()
            ,
            () -> scenario("No Testdata",
                        given("Open HomePage", PosterUtils::openHomePage)
                        .and("Homepage is validated", homepage::validateStructure).ignore().wip()
                        .when("Register is opened", homepage::clickRegister).skip().wip()
                        .then("RegisterPage can be validated", registerPage::validateStructure).wip().skip()
                    ).wip()
            ).wip().test();
        //@formatter:on
    }

}
