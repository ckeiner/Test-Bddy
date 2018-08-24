/**
 *
 */
package posters.pom.dataobjects;

/**
 * @author pfotenhauer
 */
public class User
{
    String firstName;

    String lastName;

    String email;

    String password;

    /**
     *
     */
    public User()
    {
    }

    public User(final String firstName, final String lastName, final String email, final String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email)
    {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return String.format("User [firstName()=%s, lastName()=%s, email()=%s, password()=%s]", getFirstName(),
                getLastName(), getEmail(), getPassword());
    }
}
