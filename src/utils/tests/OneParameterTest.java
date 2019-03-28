package utils.tests;

import java.util.Scanner;
import java.util.regex.Pattern;

public class OneParameterTest implements TaskTest<OneParameterTest>{

    private String parameter;

    public String getParameter() {
        return parameter;
    }

    public OneParameterTest(){}

    public OneParameterTest(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public OneParameterTest readTestFromFile(String firstLine, Scanner fileScanner) {
        StringBuilder builder = new StringBuilder();
        builder.append(firstLine);
        if (isTest(builder.toString()))
            return new OneParameterTest( getSecondStringParameter(builder.toString()) );
        else
            while (fileScanner.hasNextLine() && ! fileScanner.hasNextInt()) {
                builder.append(System.lineSeparator());
                builder.append(fileScanner.nextLine());
                if (isTest(builder.toString()))
                    return new OneParameterTest( getSecondStringParameter(builder.toString()) );
            }
        return new OneParameterTest("");
    }

    @Override
    public boolean isTest(String string) {
        Pattern testPat = Pattern.compile(String.format(".*%s.*", textPattern));
        return string.matches(testPat.pattern());
    }
}
