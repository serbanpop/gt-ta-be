package uk.co.gt;

import uk.co.gt.model.Person;
import uk.co.gt.repository.AddressBook;
import uk.co.gt.service.AddressBookLoader;
import uk.co.gt.service.PeopleService;

public class Application {

    public static void main(String[] args) {
        new Application().start();
    }

    private static final String CSV_FILE = "AddressBook";

    private final AddressBook addressBook = new AddressBook();
    private final PeopleService peopleService = new PeopleService(addressBook);
    private final AddressBookLoader dataLoader = new AddressBookLoader();

    public void start() {
        dataLoader.read(CSV_FILE, addressBook);

        System.out.println("1. How many males are in the address book?");
        System.out.println(peopleService.countBy("Male"));
        System.out.println();

        System.out.println("2. Who is the oldest person in the address book?");
        System.out.println(peopleService.oldestPerson().map(Person::getName).orElse("none"));
        System.out.println();


        System.out.println("3. How many days older is Bill than Paul?");
        System.out.println(peopleService.ageDifferenceInDays("Bill", "Paul"));
        System.out.println();
    }

}
