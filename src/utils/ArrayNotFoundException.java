package utils;

import java.util.NoSuchElementException;

public class ArrayNotFoundException extends NoSuchElementException {

    private static final String forMessage = "Array not found in the string: ";

    ArrayNotFoundException(String message){
        super(forMessage + message);
    }
}