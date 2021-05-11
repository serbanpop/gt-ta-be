package uk.co.gt.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Person {

    private final String name;
    private final String gender;
    private final LocalDate dateOfBirth;

}
