import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.naming.InvalidNameException;
import java.security.InvalidParameterException;
import java.util.Optional;

// Fetch user's address -> city if both of them not null

public class TestOptionals {
    User user1 = new User("Sehwag", null);        // complete address object null
    User user2 = new User("Sachin", new Address("101", null));         // for address object, 1 parameter is null
    User user3 = new User("Dhoni", new Address("102", "New Delhi"));   // all values available

    @DataProvider(name = "users")
    public Object[][] getUserDetails() {
        return new Object[][]{
                {user1}, {user2}, {user3}
        };
    }


    @Test(priority = 1, dataProvider = "users")
    public void withoutOptionals(User user) {
        if (user.getAddress() != null) {
            if (user.getAddress().getCity() != null) {
                System.out.println("Complete user details are -> " + user);
            } else {
                System.out.println("City details are not available");
            }
        } else {
            System.out.println("Complete address details are not available");
        }
    }

    @Test(priority = 2, dataProvider = "users")
    public void withOptionalsIsPresent(User user) {
        Optional<Address> address = Optional.ofNullable(user.getAddress());
        if (address.isPresent()) {
            Optional<String> city = Optional.ofNullable(user.getAddress().getCity());
            if (city.isPresent()) {
                System.out.println("Complete user details are -> " + user);
            } else {
                System.out.println("City details are not available");
            }
        } else {
            System.out.println("Complete address details are not available");
        }
    }

    @Test(priority = 3, dataProvider = "users")
    public void withOptionalsIfPresent(User user) {
        Optional.ofNullable(user.getAddress()).ifPresent(address -> System.out.println(address.getHouseNumber()));

        // Calls a function using ifPresent()
         Optional.ofNullable(user.getAddress()).ifPresent(address -> testMethod());

         // Prints city name
       Optional.ofNullable(user.getAddress()).ifPresent(address -> Optional.ofNullable(user.getAddress().getCity()).
               ifPresent(System.out::println));


       // Prints whole address object
        Optional.ofNullable(user.getAddress()).ifPresent(address -> System.out.println(address));
    }


    @Test(priority = 4, dataProvider = "users")
    public void withOptionalsMap(User user) {
        Optional.ofNullable(user.getAddress()).map(Address::getCity).ifPresent(System.out::println);
    }

    @Test(priority = 5, dataProvider = "users")
    public void withOptionalsIsPresentOrElseThrow(User user) {
        Optional<Address> address = Optional.ofNullable(user.getAddress());
        boolean isAddressPresent = address.isPresent();
        if (isAddressPresent) {
            System.out.println("Name of the city -> " + user.getAddress().getCity());
        } else {
            address.orElseThrow(InvalidParameterException::new);
        }
    }

    // If value is null, throws exception
    @Test(priority = 6, dataProvider = "users")
    public void withOptionalsOrElseThrow(User user) throws InvalidNameException {
        Optional.ofNullable(user.getAddress()).orElseThrow(InvalidNameException::new);
    }


    public void testMethod() {
        System.out.println("statement 1");
        System.out.println("statement 2");
    }


}
