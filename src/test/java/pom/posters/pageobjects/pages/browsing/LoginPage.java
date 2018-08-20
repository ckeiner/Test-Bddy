package pom.posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Context;

import pom.posters.dataobjects.User;

public class LoginPage extends AbstractBrowsingPage
{
    private final SelenideElement loginForm = $("#formLogin");

    private final SelenideElement emailField = $("#email");

    private final SelenideElement passwordField = $("#password");

    private final SelenideElement signInButton = $("#btnSignIn");

    private final SelenideElement registerLink = $("#linkRegister");

    @Override
    public void isExpectedPage()
    {
        loginForm.should(exist);
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Login headline
        // Make sure the Headline is there and starts with a capital letter followed by
        // at least 3 more symbols.
        loginForm.find("h2").should(matchText("[A-Z].{3,}"));
        // Email field
        // Asserts the Email field has a label displaying the value.
        loginForm.find("label.control-label[for=email]")
                .shouldHave(exactText(Context.localizedText("AccountPages.yourEmail")));
        // Asserts the email field is present.
        emailField.shouldBe(visible);
        // Password field
        // Verifies the password field has a label displaying the value.
        loginForm.find("label.control-label[for=password]")
                .shouldHave(exactText(Context.localizedText("AccountPages.yourPassword")));
        // Asserts the password field is there.
        passwordField.shouldBe(visible);
        // Login button
        // asserts the login button displays the value.
        signInButton.shouldHave(exactText(Context.localizedText("AccountPages.signIn")));
        // Register headline
        // Asserts the Headline for the Registration is there.
        $("#main .h3").shouldHave(exactText(Context.localizedText("AccountPages.newCustomer")));
        // Registration page link
        // Asserts the Register link is there and shows the correct text.
        registerLink.shouldHave(exactText(Context.localizedText("AccountPages.createNewAccount")));
    }

    public void sendFormWithData(final User persona)
    {
        // Input email
        // Fill the email field with the parameter.
        emailField.val(persona.getEmail());
        // Input password
        // Fill the password field with the parameter.
        passwordField.val(persona.getPassword());
        // Log in and open the homepage
        // Click on the Sign In button.
        signInButton.scrollTo().click();
    }

    public HomePage loginAs(final User persona)
    {
        sendFormWithData(persona);
        return new HomePage();
    }

    public LoginPage sendFalseLoginform(final User persona)
    {
        sendFormWithData(persona);
        return new LoginPage();
    }

    public void validateWrongEmail(final User persona)
    {
        errorMessage.validateErrorMessage(Context.localizedText("AccountPages.validation.emailDoesNotExistError"));
        emailField.shouldHave(exactValue(persona.getEmail()));
    }

}
