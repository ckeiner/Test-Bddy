package tests.posters.flows;

import bddtester.core.bdd.steps.Steps;
import bddtester.core.bdd.steps.TypeSteps;
import posters.pom.pageobjects.pages.browsing.CategoryPage;
import posters.pom.pageobjects.pages.browsing.HomePage;
import posters.pom.pageobjects.pages.browsing.ProductdetailPage;
import posters.pom.util.PosterUtils;
import tests.posters.testdata.OrderData;

/**
 * Describes how to open the PDP from the homepage.
 * 
 * @author ckeiner
 */
public class OpenPdpFlow
{
    /**
     * Starts on the {@link HomePage} and browses to the PDP specified by the
     * {@link OrderData}.
     * 
     * @return The {@link TypeSteps} of type OrderData which browses to the
     *         specified PDP.
     */
    public static TypeSteps<OrderData> flow()
    {
        final HomePage homepage = new HomePage();
        final CategoryPage categoryPage = new CategoryPage();
        final ProductdetailPage productPage = new ProductdetailPage();

        //@formatter:off
        return new TypeSteps<OrderData>().given("the homepage is opened", PosterUtils::openHomePage)
                .and("The category is clicked", data ->
                    {
                        homepage.topNavigation.clickCategory(data.getCategoryName());
                        categoryPage.validate(data.getCategoryName());
                    })
                .and("Product is clicked", data ->
                    {
                        categoryPage.clickProductByName(data.getProductName());
                        productPage.validate(data.getProductName());
                    });
        //@formatter:on
    }

    /**
     * Starts at the {@link HomePage} and browses to the PDP specified by the
     * parameters.
     * 
     * @param categoryName
     *            The name of the category.
     * @param productName
     *            The name of the product.
     * @return The {@link Steps} that describe how to open the specified PDP.
     */
    public static Steps flow(final String categoryName, final String productName)
    {
        final HomePage homepage = new HomePage();
        final CategoryPage categoryPage = new CategoryPage();
        final ProductdetailPage productPage = new ProductdetailPage();
        //@formatter:off
        return new Steps().given("This is the homepage", homepage::validateStructure)
                   .and("The category is opened", () -> {
                       homepage.topNavigation.clickCategory(categoryName);                       
                       categoryPage.validate(categoryName);
                   })
                   .and("I open the product", () -> {
                       categoryPage.clickProductByName(productName);
                       productPage.validate(productName);
                   });
        //@formatter:on
    }

}
