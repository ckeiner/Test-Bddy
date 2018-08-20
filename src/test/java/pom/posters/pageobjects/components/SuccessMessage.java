package pom.posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

public class SuccessMessage extends AbstractComponent
{
    private final SelenideElement successMessage = $("#successMessage");

    @Override
    public void isComponentAvailable()
    {

    }

    public void validateSuccessMessage(final String message)
    {
        // Wait until javascript makes the success message visible
        // Waits until javascript makes the success message visible.
        successMessage.shouldBe(visible);
        // Makes sure the correct text is displayed.
        successMessage.shouldHave(exactText("× " + message));
    }

}
