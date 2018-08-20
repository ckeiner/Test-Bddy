package pom.posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Selenide.$;

import pom.posters.pageobjects.components.ErrorMessage;
import pom.posters.pageobjects.components.Footer;
import pom.posters.pageobjects.components.Header;
import pom.posters.pageobjects.components.MiniCart;
import pom.posters.pageobjects.components.Search;
import pom.posters.pageobjects.components.SuccessMessage;
import pom.posters.pageobjects.components.TopNavigation;
import pom.posters.pageobjects.components.UserMenu;
import pom.posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractBrowsingPage extends AbstractPageObject
{

    public Header header = new Header();

    public Footer footer = new Footer();

    public MiniCart miniCart = new MiniCart();

    public Search search = new Search();

    public TopNavigation topNavigation = new TopNavigation();

    public UserMenu userMenu = new UserMenu();

    public SuccessMessage successMessage = new SuccessMessage();

    public ErrorMessage errorMessage = new ErrorMessage();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        header.isComponentAvailable();
        footer.isComponentAvailable();
        miniCart.isComponentAvailable();
        search.isComponentAvailable();
        topNavigation.isComponentAvailable();
        userMenu.isComponentAvailable();
        successMessage.isComponentAvailable();
        errorMessage.isComponentAvailable();
    }

    public LoginPage clickLogin()
    {
        $("#userMenu .goToLogin").click();
        return new LoginPage();
    }

    public RegisterPage clickRegister()
    {
        userMenu.openRegister();
        return new RegisterPage();
    }

}
