package uk.co.gt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.gt.model.Person;
import uk.co.gt.repository.AddressBook;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static uk.co.gt.service.PeopleServiceTest.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    public static final Person BILL = new Person("Bill McKnight", "Male", LocalDate.of(1977, 3, 16));
    public static final Person PAUL = new Person("Paul Robinson", "Male", LocalDate.of(1985, 1, 15));
    public static final Person GEMMA = new Person("Gemma Lane", "Female", LocalDate.of(1991, 11, 29));
    public static final Person SARAH = new Person("Sarah Stone", "Female", LocalDate.of(1980, 9, 20));
    public static final Person WES = new Person("Wes Jackson", "Male", LocalDate.of(1974, 8, 14));

    @Mock
    private AddressBook addressBook;

    @InjectMocks
    private PeopleService testInstance;

    @ParameterizedTest
    @ArgumentsSource(PeopleByGenerArgumentsProvider.class)
    void countsPeopleByGender(List<Person> people, String gender, long expectedCount) {
        given(addressBook.people()).willReturn(people);

        assertThat(testInstance.countBy(gender)).isEqualTo(expectedCount);
    }


    @ParameterizedTest
    @ArgumentsSource(PeopleByAgeArgumentsProvider.class)
    void retrievesOldestPerson(List<Person> people, Person expectedOldestPerson) {
        given(addressBook.people()).willReturn(people);

        assertThat(testInstance.oldestPerson()).contains(expectedOldestPerson);
    }

    @Test
    void doesNotRetrievesOldestPersonWhenAddressBookIsEmpty() {
        given(addressBook.people()).willReturn(emptyList());

        assertThat(testInstance.oldestPerson()).isEmpty();
    }

}

class PeopleByGenerArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(emptyList(), "Female", 0),
                Arguments.of(List.of(BILL), "Male", 1),
                Arguments.of(List.of(BILL, PAUL), "Male", 2),
                Arguments.of(List.of(BILL, GEMMA, SARAH, WES), "Female", 2)
        );
    }
}

class PeopleByAgeArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(List.of(PAUL), PAUL),
                Arguments.of(List.of(BILL, PAUL), BILL),
                Arguments.of(List.of(BILL, PAUL, GEMMA, SARAH, WES), WES)
        );
    }
}