package uk.co.gt.service;

import org.junit.jupiter.api.Test;
import uk.co.gt.repository.AddressBook;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.gt.TestData.*;

class AddressBookLoaderIntegrationTest {

    private final AddressBookLoader testInstance = new AddressBookLoader();

    @Test
    void loadsFile() {
        final AddressBook addressBook = new AddressBook();

        testInstance.read("AddressBook", addressBook);

        assertThat(addressBook.people()).containsOnly(BILL, PAUL, GEMMA, SARAH, WES);
    }
}