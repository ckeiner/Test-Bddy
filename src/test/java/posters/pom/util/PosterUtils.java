package posters.pom.util;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pom.pageobjects.pages.browsing.HomePage;

public class PosterUtils
{

    @Step("I open the homepage")
    public static HomePage openHomePage()
    {
        clearBrowserCookies();

        // open home page
        open(Neodymium.configuration().url());
        final HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    }

}
