package utils.tasks;

import utils.Utils;
import utils.tests.TaskTest;

import java.io.FileNotFoundException;

public interface Task<TestType extends TaskTest> {

    char USE_DEFAULT_PATH = '1';
    String reportFilePath = "src/reportFile.txt",
            SOLUTION_NOT_FOUND = "No solution",
            TEST_NOT_FOUND = "@TEST not found.",
            TEST = "@TEST",
            ANY_TESTS_NOT_FOUND = "No tests found",
            ENTER_PATH_TASK_FILE = String.format("Enter the path of the task samples.txt: ( Enter %c for use default path )", USE_DEFAULT_PATH),
            LIST_ITEM_SEPARATOR = System.lineSeparator(),
            ENTER_OUTPUT_FILE_PATH = String.format("Enter the path of the task report file: ( Enter %c for use default path )", USE_DEFAULT_PATH);


    default void start(boolean extraExit){
        String taskFP = getTaskFilePath();
        String tmp = getTemp();
        int testCount = 0;

        try{
            testCount = Utils.countTestsIntoFile(taskFP);
        }
        catch (FileNotFoundException e){
            System.out.println(Utils.ERROR_FIND_FILE + taskFP);
            System.exit(-1);
        }

        StringBuilder reportBuilder = new StringBuilder();
//      Формируем отчёт для каждого теста
        for (int testNumber = 1; testNumber <= testCount ; testNumber++) {
            reportBuilder.append(String.format("%s%d: %n", TEST, testNumber));
            reportBuilder.append(this.findSolution((TestType) Utils.readTestFromFile(taskFP, testNumber, getTestClassInstance())));
            reportBuilder.append(System.lineSeparator());
        }

        if (reportBuilder.length() == 0)
            if (extraExit){
                System.err.println(ANY_TESTS_NOT_FOUND);
                System.exit(-1);
            }
            else
                reportBuilder.append(ANY_TESTS_NOT_FOUND);

        Utils.writeToFile(tmp, reportBuilder.toString());
    }

    default void consoleStart(boolean extraExit) {
        System.out.println(ENTER_PATH_TASK_FILE);
        String path = Utils.filePathConsoleInput(USE_DEFAULT_PATH);

        if ( ! path.equals(String.valueOf(USE_DEFAULT_PATH)))
            setTaskFilePath(path);

        System.out.println(ENTER_OUTPUT_FILE_PATH);
        String temp = Utils.filePathConsoleInput(USE_DEFAULT_PATH);

        if ( ! temp.equals(String.valueOf(USE_DEFAULT_PATH)))
            setTemp(temp);

        start(extraExit);

        System.out.println(Utils.readFromFile(getTemp()));
    }

    /**
     *  Служит для уведомления дефолтных методов, найдены ли в файле (хоть 1) корректные входные данные.
     * Делать всегда вовзращаемое значение "false", если в условии не требуется экстренного завершения
     * программы с кодом "-1" и уведомлением в консоль.
     *  Если вернуть true, но тестов не будет найдено, увдомление появится в окне вывода решения в GUI.
     * @return "true" если не найдено ни одного теста.
     */
    boolean isExtraExit();

    /**
     * Должнен вернуть программе образец нашего класса тестов, воплощаюего интерфейс TaskTest.
     *
     * @return Экземпляр класса, имплемитирующий TaskTest для данной задачи.
     */
    TestType getTestClassInstance();

    String findSolution(TestType test);

    String getTaskFilePath();

    void setTaskFilePath(String taskFilePath);

    String getTemp();

    void setTemp(String temp);

}
