package uk.co.gt.repository;

import uk.co.gt.model.Person;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class AddressBook {

    private final Set<Person> people = new LinkedHashSet<>();

    public boolean addPerson(Person person) {
        return people.add(person);
    }

    public Set<Person> people() {
        return unmodifiableSet(people);
    }

    public Optional<Person> getBy(String firstName) {
        return people.stream()
                .filter(person -> person.getName().startsWith(firstName))
                .findFirst();
    }

}
