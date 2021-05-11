package uk.co.gt.repository;

import uk.co.gt.model.Person;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class AddressBook {

    private final Set<Person> book = new LinkedHashSet<>();

    public boolean addPerson(Person person) {
        return book.add(person);
    }

    public Set<Person> people() {
        return unmodifiableSet(people());
    }

}
