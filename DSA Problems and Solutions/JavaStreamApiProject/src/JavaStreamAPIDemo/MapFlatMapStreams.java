package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Customer {
    private int id;
    private String name;
    private String email;
    private List<String> phoneNumbers;

    public Customer(int id, String name, String email, List<String> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

public class MapFlatMapStreams {

    public static List<Customer> getAll() {
        return Stream.of(
                        new Customer(101, "john", "John@gmail.com", Arrays.asList("123456789", "986754321")),
                        new Customer(102, "smith", "smith@gmail.com", Arrays.asList("123456789", "986754321")),
                        new Customer(103, "peter", "peter@gmail.com", Arrays.asList("123456789", "986754321")),
                        new Customer(104, "kely", "kely@gmail.com", Arrays.asList("123456789", "986754321")))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        getEmailFromCustomer();

    }

    // 1 get all the list of email id of customers
    public static void getEmailFromCustomer() {
        List<Customer> customers = getAll();
        // mapping of List<Customer to List<String>  -> Data Transformation
        List<String> emails = customers.stream()
                .map(e -> e.getEmail()).collect(Collectors.toList());
        System.out.println(emails);
//        [John@gmail.com, smith@gmail.com, peter@gmail.com, kely@gmail.com]

        // map -> phonenumbers
        System.out.println(customers.stream()
                .map(c->c.getPhoneNumbers()).collect(Collectors.toList()));
//        [[123456789, 986754321], [123456789, 986754321], [123456789, 986754321], [123456789, 986754321]]

        // flatmap
        System.out.println(customers.stream()
                .flatMap(c->c.getPhoneNumbers().stream()).collect(Collectors.toList()));
//        [123456789, 986754321, 123456789, 986754321, 123456789, 986754321, 123456789, 986754321]
    }
}