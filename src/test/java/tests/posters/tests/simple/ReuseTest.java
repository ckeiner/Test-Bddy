package tests.posters.tests.simple;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.bdd.steps.Steps;
import pom.posters.dataobjects.Product;
import pom.posters.pageobjects.pages.browsing.CategoryPage;
import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.pageobjects.pages.browsing.ProductdetailPage;
import pom.posters.util.PosterUtils;

/**
 * Verifies that test data, steps, and scenarios can simply be reused. Thus, you
 * need to only defined them once and then you can use it again.
 * 
 * @author ckeiner
 *
 */
public class ReuseTest extends AbstractExtentReportTest
{
    /**
     * Tests that verifies that the PDP works correctly.
     */
    @Test
    public void goToPdp()
    {
        final String categoryName = "World of Nature";
        final String productName = "Great Grey Owl";
        final ProductdetailPage productPage = new ProductdetailPage();
        feature("Go to PDP", () ->
        //@formatter:off
                scenario("Open Catalog",
                        given(openCategory(categoryName))
                        .then("CategoryPage should be validated", new CategoryPage()::validateStructure)
                    ),

                () -> scenario("Open PDP",
                        given(openCategory(categoryName)).when("Open HomePage", () ->
                            {
                                new CategoryPage().clickProductByName(productName);
                            })
                        .and(addToCart(productName))
                        .then("The correct product is in the cart", () ->
                            {
                                final Product product = productPage.getProduct();
                                productPage.miniCart.validateMiniCart(1, product);
                            })
                    )
                //@formatter:on
        ).test();

    }

    /**
     * Defined the flow of opening a specified category in BDD-Form.
     * 
     * @param categoryName
     *            The name of the category which should be opened.
     * @return The {@link Steps} for opening the specified category.
     */
    private Steps openCategory(final String categoryName)
    {
        final HomePage homepage = new HomePage();
        final Steps scenario = given("Open HomePage", PosterUtils::openHomePage)
                .and("Homepage is validated", homepage::validateStructure).when("Register is opened", () ->
                    {
                        homepage.topNavigation.clickCategory(categoryName);
                    });
        return scenario;
    }

    /**
     * Adds a product to the cart in BDD-Form.
     * 
     * @param categoryName
     *            The name of the category which should be opened.
     * @return The {@link Steps} for adding a product to the cart.
     */
    private Steps addToCart(final String productName)
    {
        final ProductdetailPage productPage = new ProductdetailPage();
        final Steps scenario = given("The correct PDP is opened", () ->
            {
                productPage.validate(productName);
            }).then("The product is correct", () ->
                {
                    productPage.validate(productName);
                    productPage.addToCart(productPage.getChosenSize(), productPage.getChosenStyle());
                });
        return scenario;
    }

}
