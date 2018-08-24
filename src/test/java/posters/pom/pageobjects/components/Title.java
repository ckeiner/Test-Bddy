package posters.pom.pageobjects.components;

import static com.codeborne.selenide.Selenide.title;

import org.junit.Assert;

import io.qameta.allure.Step;

public class Title extends AbstractComponent
{

    @Override
    public void isComponentAvailable()
    {
    }

    @Step("validate that the page title matches {title}")
    public void validateTitle(final String title)
    {
        Assert.assertEquals(title, title());
    }
}