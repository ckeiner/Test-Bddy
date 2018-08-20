/**
 *
 */
package pom.posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Context;

import io.qameta.allure.Step;
import pom.posters.dataobjects.Product;
import pom.posters.pageobjects.pages.browsing.CartPage;

/**
 * @author pfotenhauer
 */
public class MiniCart extends AbstractComponent
{
    private final SelenideElement headerCart = $("#headerCartOverview");

    private final static String miniCartSelector = "#miniCartMenu";

    private final SelenideElement miniCart = $(miniCartSelector);

    private final SelenideElement subOrderPrice = $(miniCartSelector + " .subOrderPrice");

    private final SelenideElement totalCountElement = $("#btnCartOverviewForm .headerCartProductCount");

    @Override
    public void isComponentAvailable()
    {
        $("#btnCartOverviewForm").should(exist);
    }

    @Step("open the mini cart")
    public void openMiniCart()
    {
        // Click the mini cart icon
        headerCart.scrollTo().click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        miniCart.waitUntil(visible, Context.get().configuration.timeout());
    }

    @Step("close the mini cart")
    public void closeMiniCart()
    {
        // Click the mini cart icon again
        headerCart.scrollTo().click();
        // Move the mouse out of the area
        $("#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        miniCart.waitUntil(not(visible), Context.get().configuration.timeout());
    }

    @Step("open the cart page")
    public CartPage openCartPage()
    {
        // Open the cart
        // Click on the button to go to the Cart
        miniCart.find(".goToCart").scrollTo().click();
        return new CartPage();
    }

    @Step("get the total product count from mini cart")
    public int getTotalCount()
    {
        return Integer.parseInt(totalCountElement.text());
    }

    @Step("validate the mini cart total product count")
    public void validateTotalCount(final int totalCount)
    {
        totalCountElement.shouldHave(exactText(Integer.toString(totalCount)));
    }

    @Step("get the subtotal price from mini cart")
    public String getSubtotal()
    {

        // Store the mini cart subtotal
        // Open mini cart
        openMiniCart();
        // Store subtotal in oldSubTotal
        final String subtotal = subOrderPrice.text();
        // Close mini cart
        closeMiniCart();

        return subtotal;
    }

    @Step("validate the mini cart subtotal price")
    public void validateSubtotal(final String subtotal)
    {
        // Verify the mini cart shows the specified subtotal
        // Open mini cart
        openMiniCart();
        // Verify subtotal equals specified subtotal
        // Compare the subTotal to the parameter
        subOrderPrice.shouldHave(exactText(subtotal));
        // Close Mini Cart
        closeMiniCart();
    }

    /**
     * @param position
     * @param product
     */
    @Step("validate \"{product}\" in the mini cart")
    public void validateMiniCart(final int position, final Product product)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(),
                product.getTotalUnitPrice());
    }

    /**
     * @param position
     * @param product
     * @param productAmount
     * @param productTotalPrice
     */
    @Step("validate \"{product}\" in the mini cart")
    public void validateMiniCart(final int position, final Product product, final int productAmount,
            final String productTotalPrice)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), productAmount,
                productTotalPrice);
    }

    private void validateMiniCart(final int position, final String productName, final String productStyle,
            final String productSize, final int productCount, final String prodTotalPrice)
    {
        // Open the mini cart
        openMiniCart();
        // Validate data of specified item
        // Product Name
        // ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems
        final SelenideElement miniCartItem = $$("#miniCartMenu .cartItems").get(position - 1);
        // Compares the name of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodName").shouldHave(exactText(productName));
        // Product Style
        // Compares the style of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodStyle").shouldHave(exactText(productStyle));
        // Product Size
        // Compares the style of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodSize").shouldHave(exactText(productSize));
        // Amount
        // Compares the amount of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodCount").shouldHave(exactText(Integer.toString(productCount)));
        // Price
        // Compares the price of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodPrice").shouldHave(exactText(prodTotalPrice));
        // Close mini cart
        closeMiniCart();
    }
}
