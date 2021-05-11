package uk.co.gt;

import uk.co.gt.model.Person;

import java.time.LocalDate;

public interface TestData {

    Person BILL = new Person("Bill McKnight", "Male", LocalDate.of(1977, 3, 16));
    Person PAUL = new Person("Paul Robinson", "Male", LocalDate.of(1985, 1, 15));
    Person GEMMA = new Person("Gemma Lane", "Female", LocalDate.of(1991, 11, 29));
    Person SARAH = new Person("Sarah Stone", "Female", LocalDate.of(1980, 9, 20));
    Person WES = new Person("Wes Jackson", "Male", LocalDate.of(1974, 8, 14));

}
