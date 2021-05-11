package uk.co.gt.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.gt.TestData.*;

class AddressBookTest {

    private AddressBook testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new AddressBook();
    }

    @Test
    void addsPerson() {
        assertThat(testInstance.people()).isEmpty();

        testInstance.addPerson(BILL);

        assertThat(testInstance.people()).containsOnly(BILL);
    }

    @Test
    void addsMultiplePeople() {
        assertThat(testInstance.people()).isEmpty();

        assertTrue(testInstance.addPerson(BILL));
        assertTrue(testInstance.addPerson(PAUL));
        assertThat(testInstance.people()).containsOnly(BILL, PAUL);
    }

    @Test
    void addsPeopleOnlyOnce() {
        assertThat(testInstance.people()).isEmpty();

        assertTrue(testInstance.addPerson(BILL));
        assertFalse(testInstance.addPerson(BILL));

        assertThat(testInstance.people()).containsOnly(BILL);
    }

    @Test
    void findsPersonByTheirFirstName() {
        testInstance.addPerson(BILL);

        assertThat(testInstance.getBy("Bill")).contains(BILL);
    }

    @Test
    void findsPersonByTheirFirstNameWhenAddressBookContainsMultiplePeople() {
        testInstance.addPerson(BILL);
        testInstance.addPerson(GEMMA);
        testInstance.addPerson(PAUL);

        assertThat(testInstance.getBy("Gemma")).contains(GEMMA);
    }

    @Test
    void doesNotFindPersonByTheirFirstName() {
        testInstance.addPerson(BILL);

        assertThat(testInstance.getBy("Billy")).isEmpty();
    }
}