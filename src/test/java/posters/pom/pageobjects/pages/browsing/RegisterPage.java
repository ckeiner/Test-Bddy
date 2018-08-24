package posters.pom.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pom.dataobjects.User;

/**
 * @author pfotenhauer
 */
public class RegisterPage extends AbstractBrowsingPage
{

    private final SelenideElement registerForm = $("#formRegister");

    private final SelenideElement firstnameField = $("#firstName");

    private final SelenideElement lastnameField = $("#lastName");

    private final SelenideElement emailField = $("#eMail");

    private final SelenideElement passwordField = $("#password");

    private final SelenideElement passwordRepeatField = $("#passwordAgain");

    private final SelenideElement registerButton = $("#btnRegister");

    @Override
    @Step("ensure this is a register page")
    public void isExpectedPage()
    {
        registerForm.should(exist);
    }

    @Step("fill and send register form")
    public LoginPage sendRegisterForm(final User persona, final String passwordRepeat)
    {
        // Fill out the registration form
        // Type the last name parameter into the last name field.
        lastnameField.val(persona.getLastName());
        // Type the first name parameter into the first name field.
        firstnameField.val(persona.getFirstName());
        // Type the email parameter into the email field.
        emailField.val(persona.getEmail());
        // Type the password parameter into the password field.
        passwordField.val(persona.getPassword());
        // Type the second password parameter into the second password field.
        passwordRepeatField.val(passwordRepeat);
        // Register and open the login page if successful
        // Click on the Register Button
        registerButton.scrollTo().click();

        return new LoginPage();
    }

    /**
     * @param user
     * @param passwordRepeat
     * @return
     */
    public LoginPage registerAs(final User persona)
    {
        final LoginPage loginPage = sendRegisterForm(persona, persona.getPassword());
        return loginPage;
    }
}