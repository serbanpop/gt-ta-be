package uk.co.gt.service;

import lombok.RequiredArgsConstructor;
import uk.co.gt.model.Person;
import uk.co.gt.repository.AddressBook;

import java.util.Optional;

import static java.util.Comparator.comparing;

@RequiredArgsConstructor
public class PeopleService {

    private final AddressBook addressBook;

    public long countBy(String gender) {
        return addressBook.people().stream()
                .filter(person -> gender.equals(person.getGender()))
                .count();
    }

    public Optional<Person> oldestPerson() {
        return addressBook.people().stream()
                .min(comparing(Person::getDateOfBirth));
    }

}
