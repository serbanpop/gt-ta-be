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
import uk.co.gt.exception.PersonNotFoundException;
import uk.co.gt.model.Person;
import uk.co.gt.repository.AddressBook;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
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
    @ArgumentsSource(GenderCountArgumentsProvider.class)
    void countsPeopleByGender(Set<Person> people, String gender, long expectedCount) {
        given(addressBook.people()).willReturn(people);

        assertThat(testInstance.countBy(gender)).isEqualTo(expectedCount);
    }


    @ParameterizedTest
    @ArgumentsSource(OldestPersonArgumentsProvider.class)
    void retrievesOldestPerson(Set<Person> people, Person expectedOldestPerson) {
        given(addressBook.people()).willReturn(people);

        assertThat(testInstance.oldestPerson()).contains(expectedOldestPerson);
    }

    @Test
    void doesNotRetrievesOldestPersonWhenAddressBookIsEmpty() {
        given(addressBook.people()).willReturn(emptySet());

        assertThat(testInstance.oldestPerson()).isEmpty();
    }

    @ParameterizedTest
    @ArgumentsSource(AgeDifferenceArgumentsProvider.class)
    void returnDifferenceInDaysBetweenTwoPeople(String firstForename, Person firstPerson, String secondForename, Person secondPerson, long expectedDifference) {
        given(addressBook.getBy(firstForename)).willReturn(Optional.of(firstPerson));
        given(addressBook.getBy(secondForename)).willReturn(Optional.of(secondPerson));

        assertThat(testInstance.ageDifferenceInDays(firstForename, secondForename)).isEqualTo(expectedDifference);
    }

    @Test
    void throwsExceptionWhenTheFirstPersonDoesNotExist() {
        given(addressBook.getBy(anyString())).willReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.ageDifferenceInDays("Stanley", "Gemma"))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessage("Could not find person by the first name: Stanley");
    }

    @Test
    void throwsExceptionWhenTheSecondPersonDoesNotExist() {
        given(addressBook.getBy(anyString()))
                .willReturn(Optional.of(BILL))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.ageDifferenceInDays("Bill", "Stanley"))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessage("Could not find person by the first name: Stanley");
    }


}

class GenderCountArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(emptySet(), "Female", 0),
                Arguments.of(Set.of(BILL), "Male", 1),
                Arguments.of(Set.of(BILL, PAUL), "Male", 2),
                Arguments.of(Set.of(BILL, GEMMA, SARAH, WES), "Female", 2)
        );
    }
}

class OldestPersonArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(Set.of(PAUL), PAUL),
                Arguments.of(Set.of(BILL, PAUL), BILL),
                Arguments.of(Set.of(BILL, PAUL, GEMMA, SARAH, WES), WES)
        );
    }
}

class AgeDifferenceArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("Bill", BILL, "Paul", PAUL, 2862),
                Arguments.of("Paul", PAUL, "Bill", BILL, 2862),
                Arguments.of("Paul", PAUL, "Sarah", SARAH, 1578),
                Arguments.of("Gemma", GEMMA, "Gemma", GEMMA, 0)
        );
    }
}