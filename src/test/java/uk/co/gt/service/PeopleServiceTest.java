package uk.co.gt.service;

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

import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    @Mock
    private AddressBook addressBook;

    @InjectMocks
    private PeopleService testInstance;

    @ParameterizedTest
    @ArgumentsSource(PeopleArgumentsProvider.class)
    void countsPeopleByGender(List<Person> people, String gender, long expectedCount) {
        given(addressBook.people()).willReturn(people);

        assertThat(testInstance.countBy(gender)).isEqualTo(expectedCount);
    }

}

class PeopleArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(List.of(), "Female", 0),
                Arguments.of(List.of(
                        new Person("Bill McKnight", "Male", now())
                ), "Male", 1),
                Arguments.of(List.of(
                        new Person("Bill McKnight", "Male", now()),
                        new Person("Paul Robinson", "Male", now())
                ), "Male", 2),
                Arguments.of(List.of(
                        new Person("Bill McKnight", "Male", now()),
                        new Person("Paul Robinson", "Male", now())
                ), "Female", 0),
                Arguments.of(List.of(
                        new Person("Bill McKnight", "Male", now()),
                        new Person("Paul Robinson", "Male", now()),
                        new Person("Sarah Stone", "Female", now())
                ), "Female", 1)
        );
    }
}