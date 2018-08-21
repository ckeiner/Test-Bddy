package tests.posters.tests.simple;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import bddtester.core.bdd.steps.TypeSteps;
import bddtester.core.testdata.TwoDataType;
import pom.posters.dataobjects.Product;
import pom.posters.pageobjects.pages.browsing.CategoryPage;
import pom.posters.pageobjects.pages.browsing.HomePage;
import pom.posters.pageobjects.pages.browsing.ProductdetailPage;
import pom.posters.util.PosterUtils;

/**
 * Reuses steps, and scenarios with test data.
 * 
 * @author ckeiner
 *
 */
public class ReuseTestdata extends AbstractExtentReportTest
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
        final CategoryPage categoryPage = new CategoryPage();
        //@formatter:off
        feature("Go to PDP",
                () -> scenario("Open Category <first> and product <second>",
                            withData(new TwoDataType<String, String>(categoryName, productName))
                            .given(openCategory())
                            .then("CategoryPage should be validated", categoryPage::validateStructure)
                        )
                ,
                () -> scenario("Open PDP",
                            withData(new TwoDataType<String, String>(categoryName, productName))
                            .given(openCategory()).when("Open HomePage",
                                (data) -> {
                                    new CategoryPage().clickProductByName(data.getSecond());
                                })
                            .and(addToCart())
                            .then("The product <second> is in the cart", () ->
                                {
                                    final Product product = productPage.getProduct();
                                    productPage.miniCart.validateMiniCart(1, product);
                                })
                        )
                ).test();
    }

    private TypeSteps<TwoDataType<String, String>> openCategory()
    {
        final HomePage homepage = new HomePage();
        final TypeSteps<TwoDataType<String, String>> model = new TypeSteps<TwoDataType<String, String>>()
                .given("Open HomePage", PosterUtils::openHomePage)
                .and("Homepage is validated", homepage::validateStructure).when("Register is opened", (data) ->
                    {
                        homepage.topNavigation.clickCategory(data.getFirst());
                    });
        return model;
    }

    private TypeSteps<TwoDataType<String, String>> addToCart()
    {
        final ProductdetailPage productPage = new ProductdetailPage();
        final TypeSteps<TwoDataType<String, String>> model = new TypeSteps<TwoDataType<String, String>>()
                .given("The correct PDP is opened", (data) ->
                    {
                        productPage.validate(data.getSecond());
                    })
                .then("The product is correct", (data) ->
                    {
                        productPage.validate(data.getSecond());
                        productPage.addToCart(productPage.getChosenSize(), productPage.getChosenStyle());
                    });
        return model;
    }
}
