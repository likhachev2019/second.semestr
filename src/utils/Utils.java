package utils;

import utils.tests.OneParameterTest;
import utils.tests.TaskTest;
import utils.tests.TwoParameterTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static final char FIRST_SYMBOL = '{',
            END_SYMBOL = '}',
            SEPARATOR = ',';

    public static final String ERROR_FIND_FILE = "File not found: ", ERROR_NULL_STRING_WRITE_TO_FILE = "You have tried to write to the file a null string ";
    public static final Class CLASS_ONE_PARAMETER_TEST = OneParameterTest.class,
                            CLASS_TWO_PARAMETER_TEST = TwoParameterTest.class;

    private static Scanner fileScanner, lineScanner, scanExit = new Scanner(System.in);


    public static TaskTest readTestFromFile(String filePath, int testNumber, TaskTest testTypeInstance) {
        try{
            fileScanner = new Scanner(new File(filePath));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        String line;

        while(fileScanner.hasNextLine()) {
            line = fileScanner.nextLine();
            lineScanner = new Scanner(line);
            if (lineScanner.hasNextInt())
                if (lineScanner.nextInt() == testNumber) {
                    return testTypeInstance.readTestFromFile(lineScanner.nextLine(), fileScanner);
                }
        }

        return null;
    }

    public static String filePathConsoleInput(char useDefaultPath){
        String path = scanExit.nextLine();

        while ( ! Utils.isFilePath(path)){
            if (path.length() > 0)
                if (path.charAt(0) == useDefaultPath)
                    return String.valueOf(useDefaultPath);

            System.out.println(ERROR_FIND_FILE + path);
            path = scanExit.nextLine();
        }

        if (path.charAt(0) == useDefaultPath)
            return String.valueOf(useDefaultPath);

        return path;
    }

    public static int countTestsIntoFile(String fileSource) throws FileNotFoundException {
        int count = 0;
        try{
            fileScanner = new Scanner(new File(fileSource));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        String line;

        while (fileScanner.hasNextLine()){
            line = fileScanner.nextLine();
            lineScanner = new Scanner(line);
            if ( isTestNumber(line) ){
                count++;
            }
        }

        return count;
    }

    public static boolean writeToFile(String theResultFile, String string) {
        if (string == null) {
            System.out.println(ERROR_NULL_STRING_WRITE_TO_FILE);
            return false;
        }
        try{
            FileWriter writer = new FileWriter(theResultFile, false);
            writer.write(string);
            writer.flush();
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String readFromFile(String theResultFile) {
        StringBuilder builder = new StringBuilder();

        try{
            fileScanner = new Scanner(new File(theResultFile));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        while (fileScanner.hasNextLine()){
            builder.append(fileScanner.nextLine());
            if (fileScanner.hasNextLine())
                builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    public static boolean isFilePath(String filePath){
        if (filePath == null)
            return false;
        try {
            new Scanner(new File(filePath));
            return true;
        }
        catch (FileNotFoundException e){
            return false;
        }
    }

    public static boolean isTestNumber(String str){
        int checkIndex = 0;
        for (int i = 0; i < str.length() && str.charAt(i) >= 48 && str.charAt(i) <= 57; i++) {
            checkIndex++;
        }
        if (checkIndex > 0)
            return str.length() <= checkIndex || str.charAt(checkIndex) == ' ';
        else
            return false;
    }

    public static int[] listToArray(ArrayList<Integer> array){
        int lenght = array.size();
        int [] arrayInt = new int[lenght];
        int index = 0;

        for(; lenght > 0; lenght--, index++){
            arrayInt[index] = array.get(index);
        }
        return arrayInt;
    }

    public static ArrayList<Integer> stringToList(String str) throws ArrayNotFoundException{
        if (str == null)
            return null;

        int startIndex = str.indexOf(FIRST_SYMBOL), endIndex = str.indexOf(END_SYMBOL), fromFirstDot = 0, secondDot = startIndex;

        ArrayList<Integer> arrayList = new ArrayList<>();
        String sub;

        if (startIndex < endIndex){
            while(true) {
                fromFirstDot = ++secondDot;
                secondDot = str.indexOf(SEPARATOR, fromFirstDot);
                if (secondDot == -1 || secondDot > endIndex) {
                    sub = str.substring(fromFirstDot, endIndex);
                    try{
                        arrayList.add(Integer.valueOf(sub.trim()));
                    }
                    catch (NumberFormatException e){
                        throw new ArrayNotFoundException(str);
                    }
                    return arrayList;
                }
                sub = str.substring(fromFirstDot, secondDot);
                try{
                    arrayList.add(Integer.valueOf(sub.trim()));
                }
                catch (NumberFormatException e){
                    throw new ArrayNotFoundException(str);
                }
            }
        }
        else
            throw new ArrayNotFoundException(str);
    }

    public static int[] stringToArray(String str){
        return listToArray(stringToList(str));
    }

    public static boolean isArray(String input) {
        return input.matches("^\\{([+-]?\\d+,)*-?\\d+}$");
    }
}
