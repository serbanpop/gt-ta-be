package uk.co.gt.service;

import lombok.SneakyThrows;
import uk.co.gt.model.Person;
import uk.co.gt.repository.AddressBook;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.nio.file.Files.readAllLines;

public class AddressBookLoader {

    private static final String DELIMITER = ", ";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/"))
            .appendValueReduced(ChronoField.YEAR, 2, 4, 1900)
            .toFormatter();

    @SneakyThrows
    public void read(String filename, AddressBook addressBook) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(filename).getFile());

        readAllLines(file.toPath()).forEach(line -> {
            final String[] values = line.split(DELIMITER);
            addressBook.addPerson(new Person(values[0], values[1], LocalDate.parse(values[2], DATE_TIME_FORMATTER)));
        });
    }
}
