package tests.posters.testdata;

import pom.posters.dataobjects.User;

/**
 * Describes a persona with login capabilities.
 * 
 * @author ckeiner
 *
 */
public class LoginData
{
    public final User user;

    public LoginData(final String firstName, final String lastName, final String email, final String password)
    {
        this.user = new User(firstName, lastName, email, password);
    }

    public LoginData(final User user)
    {
        this.user = user;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return user.getFirstName();
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return user.getLastName();
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return user.getEmail();
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String toString()
    {
        return user.toString();
    }

}
