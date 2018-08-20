package pom.posters.util;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Context;

import io.qameta.allure.Step;
import pom.posters.pageobjects.pages.browsing.HomePage;

public class PosterUtils
{

    @Step("I open the homepage")
    public static HomePage openHomePage()
    {
        clearBrowserCookies();

        // open home page
        open(Context.get().configuration.url());
        final HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    }

}
