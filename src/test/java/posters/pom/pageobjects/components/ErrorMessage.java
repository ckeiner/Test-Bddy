package posters.pom.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class ErrorMessage extends AbstractComponent
{

    private final SelenideElement errorMessage = $("#errorMessage");

    @Override
    public void isComponentAvailable()
    {
        errorMessage.should(exist);
    }

    @Step("validate that the error message {message} is visible")
    public void validateErrorMessage(final String message)
    {
        // Wait until javascript makes the error message visible
        // Waits until javascript makes the error message visible.
        errorMessage.shouldBe(visible);
        // Makes sure the correct text is displayed.
        errorMessage.shouldHave(exactText("× " + message));
    }

}
