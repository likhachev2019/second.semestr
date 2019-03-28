package utils.tests;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TwoParameterTest implements TaskTest<TwoParameterTest>{

    private int firstParam;
    private String secondParam;

    public TwoParameterTest(){}

    public TwoParameterTest(int firstParam, String secondParam) {
        this.firstParam = firstParam;
        this.secondParam = secondParam;
    }

    public int getFirstParam() {
        return firstParam;
    }

    public String getSecondParam() {
        return secondParam;
    }

    @Override
    public TwoParameterTest readTestFromFile(String firstLine, Scanner fileScanner) {
        StringBuilder builder = new StringBuilder();
        builder.append(firstLine);
        if (isTest(builder.toString()))
            return new TwoParameterTest(getFirstIntParameter(builder.toString()), getSecondStringParameter(builder.toString()));
        else
            while (fileScanner.hasNextLine() && ! fileScanner.hasNextInt()) {
                builder.append(System.lineSeparator());
                builder.append(fileScanner.nextLine());
                if (isTest(builder.toString()))
                    return new TwoParameterTest(getFirstIntParameter(builder.toString()), getSecondStringParameter(builder.toString()));
            }
        return new TwoParameterTest(-1, "");
    }

    @Override
    public boolean isTest(String string) {
        Pattern testPat = Pattern.compile(String.format(".*%1$s.*%2$s.*", intParameterPattern, textPattern));
        return string.matches(testPat.pattern());
    }
}
