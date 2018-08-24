package posters.pom.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
public class CategoryPage extends AbstractBrowsingPage
{
    @Override
    @Step("ensure this is a category page")
    public void isExpectedPage()
    {
        $("#productOverview").should(exist);
    }

    public void validateCategoryName(final String categoryName)
    {
        // Category name
        // Ensures the category headline contains the category name
        $("#titleCategoryName").shouldBe(text(categoryName));
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Amount of results
        // Assures the amount of posters displayed in the headline is not 0.
        $("#totalProductCount").shouldNotBe(exactText("0"));
        // Results
        // Assures there's at least one product shown
        $("#product0").shouldBe(visible);
    }

    /**
     * @param productName
     */
    public void validateProductVisible(final String productName)
    {
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").shouldBe(visible);
    }

    /**
     * @param categoryName
     */
    public void validate(final String categoryName)
    {
        validateStructure();
        validateCategoryName(categoryName);
    }

    @Step("click on a product by name \"{productName}\"")
    public ProductdetailPage clickProductByName(final String productName)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").scrollTo().click();
        return new ProductdetailPage();
    }
}
