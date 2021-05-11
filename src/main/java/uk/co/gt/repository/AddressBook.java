package uk.co.gt.repository;

import uk.co.gt.model.Person;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class AddressBook {

    private final List<Person> book = new ArrayList<>();

    public boolean addPerson(Person person) {
        return book.add(person);
    }

    public List<Person> people() {
        return unmodifiableList(people());
    }

}
