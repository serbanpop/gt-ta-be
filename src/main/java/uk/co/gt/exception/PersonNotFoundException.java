package uk.co.gt.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String forename) {
        super("Could not find person by the first name: " + forename);
    }
}
