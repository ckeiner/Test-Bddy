package tests.posters.tests.complex;

import static bddtester.api.BddSuite.feature;
import static bddtester.api.BddSuite.given;
import static bddtester.api.BddSuite.scenario;
import static bddtester.api.BddSuite.withData;
import static tests.posters.testdata.Personas.janeExample;
import static tests.posters.testdata.Personas.johnExample;

import org.junit.Test;

import bddtester.api.AbstractExtentReportTest;
import pom.posters.pageobjects.pages.browsing.CartPage;
import pom.posters.pageobjects.pages.browsing.ProductdetailPage;
import pom.posters.util.PosterUtils;
import tests.posters.flows.GuestCheckoutFlow;
import tests.posters.flows.OpenPdpFlow;

/**
 * Tests the order process.
 * 
 * @author ckeiner
 *
 */
public class GuestCheckout extends AbstractExtentReportTest
{
    /**
     * Verifies the guest checkout works as intended with a single data set
     */
    @Test
    public void testGuestCheckoutWithSingleTestdata()
    {
        final ProductdetailPage productPage = new ProductdetailPage();
        final CartPage cartPage = new CartPage();
        //@formatter:off
        feature("Order a product",
            () -> scenario("Browsing to the PDP <getProductName()>",
                    withData(janeExample())
                                    .given("Homepage is opened", PosterUtils::openHomePage)
                                    .then("Open a PDP", data ->
                                        {
                                            OpenPdpFlow.flow(data.getCategoryName(), data.getProductName());
                                        })
                    )
            ,
            () -> scenario("Browsing to a PDP with set Data",
                                    given("Homepage is opened", PosterUtils::openHomePage)
                                    .then(OpenPdpFlow.flow("Transportation", "Ikarus 211"))
                    )
            ,
            () -> scenario("Ordering the product <getProductName()> with address <getAddress()>",
                        withData(janeExample())
                                .given("Homepage is opened", PosterUtils::openHomePage)
                                .and(OpenPdpFlow.flow())
                                .and("<getProductName> is added to the cart", data ->
                                    {
                                        data.setProduct(productPage.getProduct());
                                        final String size = data.getProduct().getSize();
                                        final String style = data.getProduct().getStyle();
                                        productPage.addToCart(size, style);
                                        productPage.miniCart.validateMiniCart(1, data.getProduct());
                                        System.out.println(data.getProduct());
                                    })
                                .when("CartPage is opened", data ->
                                    {
                                        productPage.miniCart.openMiniCart();
                                        productPage.miniCart.openCartPage();
                                        cartPage.validateCartItem(1, data.getProduct());
                                    })
                                .and(GuestCheckoutFlow.flow())
                    )
            ).test();
        //@formatter:on
    }

    /**
     * Verifies the guest checkout works as intended with multiple data sets
     */
    @Test
    public void testGuestCheckoutWithMultipleTestdata()
    {
        final ProductdetailPage productPage = new ProductdetailPage();
        final CartPage cartPage = new CartPage();

        //@formatter:off
        feature("Order products", () ->
                scenario("Browsing to a PDP",
                        withData(janeExample(), johnExample())
                                    .given("Homepage is opened", PosterUtils::openHomePage)
                                    .then("Open a PDP", data ->
                                        {
                                            OpenPdpFlow.flow(data.getCategoryName(), data.getProductName());
                                        })
                                )
            ,
            () ->
                scenario("Ordering a product",
                            withData(janeExample(), johnExample())
                                    .given(OpenPdpFlow.flow())
                                    .and("Product is added to the cart", data ->
                                        {
                                            data.setProduct(productPage.getProduct());
                                            final String size = data.getProduct().getSize();
                                            final String style = data.getProduct().getStyle();
                                            productPage.addToCart(size, style);
                                            productPage.miniCart.validateMiniCart(1, data.getProduct());
                                        })
                                    .when("CartPage is opened", data ->
                                        {
                                            productPage.miniCart.openMiniCart();
                                            productPage.miniCart.openCartPage();
                                            cartPage.validateCartItem(1, data.getProduct());
                                        })
                                    .and(GuestCheckoutFlow.flow())
                                )
            ).test();
        //@formatter:on
    }
}
