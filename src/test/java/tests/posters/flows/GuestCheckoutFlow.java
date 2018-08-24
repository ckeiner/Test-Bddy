package tests.posters.flows;

import bddtester.core.bdd.steps.TypeSteps;
import posters.pom.dataobjects.Product;
import posters.pom.pageobjects.pages.browsing.CartPage;
import posters.pom.pageobjects.pages.browsing.HomePage;
import posters.pom.pageobjects.pages.checkout.NewPaymentPage;
import posters.pom.pageobjects.pages.checkout.NewShippingAddressPage;
import posters.pom.pageobjects.pages.checkout.PlaceOrderPlace;
import tests.posters.testdata.OrderData;

/**
 * Describes the Order process.
 * 
 * @author ckeiner
 *
 */
public class GuestCheckoutFlow
{
    /**
     * Starts on a {@link CartPage} and completes a guest checkout.
     * 
     * @return The {@link TypeSteps} of type {@link OrderData} that can complete the
     *         checkout.
     */
    public static TypeSteps<OrderData> flow()
    {
        final HomePage homepage = new HomePage();
        final CartPage cartPage = new CartPage();
        final NewShippingAddressPage shippingPage = new NewShippingAddressPage();
        final NewPaymentPage paymentPage = new NewPaymentPage();
        final PlaceOrderPlace placeOrderPlace = new PlaceOrderPlace();

        //@formatter:off
        return new TypeSteps<OrderData>()
            .when("A new Shipping Page is opened", () ->
            {
                cartPage.openNewShippingPage();
                shippingPage.validateStructure();
            })
            .and("The address <getAddress()> is entered", data ->
            {
                shippingPage.sendShippingAddressForm(data.getAddress(), true);
            })
            .and("The payment data <getCreditCard()> is added", data ->
            {
                paymentPage.validateStructure();
                paymentPage.sendPaymentForm(data.getCreditCard());
            })
            .then("The correct data should be shown", data ->
            {
                placeOrderPlace.validateStructure();
                placeOrderPlace.validateAddressesAndPayment(data.getAddress(), data.getAddress(),
                        data.getCreditCard());
                final Product product = data.getProduct();
                placeOrderPlace.validateProduct(1, product.getName(), product.getAmount(),
                        product.getStyle(), product.getSize());
            })
            .and("The order placed", () ->
            {
                placeOrderPlace.placeOrder();
                homepage.successMessage.isComponentAvailable();
            });
        //@formatter:on
    }

}
