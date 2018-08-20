package tests.posters.testdata;

import pom.posters.dataobjects.Address;
import pom.posters.dataobjects.CreditCard;
import pom.posters.dataobjects.Product;
import pom.posters.dataobjects.User;

/**
 * Describes a persona with the ability to order and login.
 * 
 * @author ckeiner
 *
 */
public class OrderData extends LoginData
{
    Address address;

    CreditCard creditCard;

    Product product;

    String productName;

    String categoryName;

    public OrderData(final String firstName, final String lastName, final String email, final String password,
            final String company, final String addressLine, final String city, final String state, final String zip,
            final String country, final String cardNumber, final String crypticCardNumber, final String expDateMonth,
            final String expDateYear, final String categoryName, final String productName)
    {
        super(firstName, lastName, email, password);
        address = new Address(firstName + " " + lastName, company, addressLine, city, state, zip, country);
        creditCard = new CreditCard(firstName + " " + lastName, cardNumber, crypticCardNumber, expDateMonth,
                expDateYear);
        this.productName = productName;
        this.categoryName = categoryName;
    }

    public OrderData(final User user, final Address address, final CreditCard creditCard, final String categoryName,
            final String productName)
    {
        super(user);
        this.address = address;
        this.creditCard = creditCard;
        this.productName = productName;
        this.categoryName = categoryName;
    }

    public void setProduct(final Product product)
    {
        this.product = product;
    }

    public Address getAddress()
    {
        return address;
    }

    public User getUser()
    {
        return user;
    }

    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    public Product getProduct()
    {
        return product;
    }

    public String getProductName()
    {
        return productName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    @Override
    public String toString()
    {
        String output = "OrderData: ";
        output += "\n\t " + address.toString();
        output += "\n\t " + user.toString();
        output += "\n\t " + creditCard.toString();
        if (product != null)
        {
            output += "\t " + product.toString();
        }
        output += "\n\t " + productName.toString();
        output += "\n\t " + categoryName.toString();
        return output;
    }

}