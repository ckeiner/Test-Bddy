/**
 *
 */
package posters.pom.pageobjects.pages.checkout;

import posters.pom.pageobjects.components.CheckoutHeader;
import posters.pom.pageobjects.components.Footer;
import posters.pom.pageobjects.components.UserMenu;
import posters.pom.pageobjects.pages.AbstractPageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractCheckoutPage extends AbstractPageObject
{
    public CheckoutHeader header = new CheckoutHeader();

    public Footer footer = new Footer();

    public UserMenu userMenu = new UserMenu();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        header.isComponentAvailable();
        footer.isComponentAvailable();
        userMenu.isComponentAvailable();
    }
}
