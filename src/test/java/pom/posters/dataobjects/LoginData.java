package pom.posters.dataobjects;

public class LoginData
{
    private final String email;

    private final String password;

    public LoginData(final String email, final String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

}
