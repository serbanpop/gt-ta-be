package uk.co.gt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class Person {

    private final String name;
    private final String gender;
    private final LocalDate localDate;

}
