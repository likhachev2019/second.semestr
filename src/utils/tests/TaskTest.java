package utils.tests;

import utils.Utils;

import java.util.Scanner;

public interface TaskTest<T extends TaskTest> {

    char FIRST_SYMBOL = Utils.FIRST_SYMBOL,
            END_SYMBOL = Utils.END_SYMBOL;

    String intParameterPattern = String.format("[%1$c]\\s*\\d+\\s*[%2$c]", FIRST_SYMBOL, END_SYMBOL),
            textPattern = String.format("[%1$c](.+\\R)*.+[%2$c]", FIRST_SYMBOL, END_SYMBOL);

    default int getFirstIntParameter(String string) {
        return Integer.valueOf(string.substring(string.indexOf(FIRST_SYMBOL) + 1, string.indexOf(END_SYMBOL)).trim());
    }

    default String getSecondStringParameter(String string) {
        return string.substring(string.lastIndexOf(FIRST_SYMBOL) + 1, string.lastIndexOf(END_SYMBOL));
    }

    T readTestFromFile(String firstLine, Scanner fileScanner);

    boolean isTest(String string);
}