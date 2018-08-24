package posters.pom.pageobjects.pages.browsing;

import static com.codeborne.selenide.Selenide.$;

import posters.pom.pageobjects.components.ErrorMessage;
import posters.pom.pageobjects.components.Footer;
import posters.pom.pageobjects.components.Header;
import posters.pom.pageobjects.components.MiniCart;
import posters.pom.pageobjects.components.Search;
import posters.pom.pageobjects.components.SuccessMessage;
import posters.pom.pageobjects.components.TopNavigation;
import posters.pom.pageobjects.components.UserMenu;
import posters.pom.pageobjects.pages.AbstractPageObject;

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
