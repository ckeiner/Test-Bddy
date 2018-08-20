/**
 *
 */
package pom.posters.pageobjects.pages.checkout;

import pom.posters.pageobjects.components.CheckoutHeader;
import pom.posters.pageobjects.components.Footer;
import pom.posters.pageobjects.components.UserMenu;
import pom.posters.pageobjects.pages.AbstractPageObject;

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
