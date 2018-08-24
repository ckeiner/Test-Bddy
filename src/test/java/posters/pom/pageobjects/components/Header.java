package posters.pom.pageobjects.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Header extends AbstractComponent
{

    @Override
    public void isComponentAvailable()
    {
        // Verifies the company Logo and name are visible.
        $("#categoryMenu").shouldBe(visible);
    }

}
