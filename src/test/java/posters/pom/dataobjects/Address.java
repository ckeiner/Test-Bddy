/**
 *
 */
package posters.pom.dataobjects;

/**
 * @author pfotenhauer
 */
public class Address
{
    String fullName;

    String company;

    String addressLine;

    String city;

    String state;

    String zip;

    String country;

    /**
     *
     */
    public Address(final String fullName, final String company, final String addressLine, final String city,
            final String state, final String zip, final String country)
    {
        this.fullName = fullName;
        this.company = company;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(final String company)
    {
        this.company = company;
    }

    /**
     * @return the address
     */
    public String getAddressLine()
    {
        return addressLine;
    }

    /**
     * @param addressLine
     *            the address to set
     */
    public void setAddressLine(final String addressLine)
    {
        this.addressLine = addressLine;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(final String city)
    {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState()
    {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(final String state)
    {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(final String zip)
    {
        this.zip = zip;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(final String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Address [fullName()=%s, company()=%s, adressLine()=%s, zip()=%s, city()=%s, state()=%s, country()=%s]",
                getFullName(), getCompany(), getAddressLine(), getZip(), getCity(), getState(), getCountry());
    }
}
