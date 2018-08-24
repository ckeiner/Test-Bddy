package tests.posters.testdata;

import posters.pom.dataobjects.Address;
import posters.pom.dataobjects.CreditCard;
import posters.pom.dataobjects.User;

/**
 * Defines some personas as {@link OrderData}.
 * 
 * @author ckeiner
 */
public final class Personas
{
    public static OrderData janeExample()
    {
        final String cardNumber = "4111111111111111";
        final String crypticCardNumber = "xxxx xxxx xxxx 1111";
        final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");
        final String fullName = user.getFirstName() + " " + user.getLastName();
        final Address address = new Address(fullName, "Xceptance GmbH", "Leutragraben 2-4", "Jena", "Thuringia",
                "07743", "Germany");
        final CreditCard creditCard = new CreditCard(fullName, cardNumber, crypticCardNumber, "11", "2020");
        return new OrderData(user, address, creditCard, "World of Nature", "Great Grey Owl");
    }

    public static OrderData johnExample()
    {
        final String cardNumber = "4111111111111111";
        final String crypticCardNumber = "xxxx xxxx xxxx 1111";
        final User user = new User("John", "Doe", "john@doe.com", "topsecret");
        final String fullName = user.getFirstName() + " " + user.getLastName();
        final Address address = new Address(fullName, "Xceptance Inc", "One Broadway, 14th Floor", "Cambridge",
                "Massachusetts", "02142", "United States");
        final CreditCard creditCard = new CreditCard(fullName, cardNumber, crypticCardNumber, "11", "2020");
        return new OrderData(user, address, creditCard, "Dining", "Finger Food");
    }

}
