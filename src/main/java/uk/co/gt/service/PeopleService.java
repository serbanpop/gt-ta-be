package uk.co.gt.service;

import lombok.RequiredArgsConstructor;
import uk.co.gt.repository.AddressBook;

@RequiredArgsConstructor
public class PeopleService {

    private final AddressBook addressBook;

    public long countBy(String gender) {
        return addressBook.people().stream()
                .filter(person -> gender.equals(person.getGender()))
                .count();
    }

}
