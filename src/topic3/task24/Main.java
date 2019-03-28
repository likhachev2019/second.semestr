package topic3.task24;

import utils.GUI.SimpleGUI;
import utils.tasks.Task;
import utils.tests.OneParameterTest;

import java.util.PriorityQueue;
import java.util.Queue;

public class Main implements Task<OneParameterTest> {

    private String tempFilePath = reportFilePath,
            taskFilePath = "src/topic3/task24/test.txt";

    private static final String TEXT_SUCCESSFUL_REPORT = "It's a palindrome.",
                                TEXT_FAILED_REPORT = "It isn't a palindrome";

    private static Queue<Character> queue;
    private static int initialLength;
    private static boolean resultCheck;

    public static void main(String[] args) {
        //Via the default queue:
        queue = new PriorityQueue<>();
        //Via the my queue:
        queue = new MyQueue<>();

        Main task2_24 = new Main();
        new SimpleGUI<>(task2_24).setVisible(true);
    }

    private static boolean isPalindrome(Queue<Character> chars, int length) {
        if (chars.size() == 0) {
            return false;
        }
//        If it's middle element, isn't checking him.
        if (initialLength % 2 == 1 && length - 1 == initialLength/2) {
            chars.add(chars.poll());
            return true;
        }
        char ch1 = chars.poll();
        chars.add(ch1);
//        If it needs compare more than two elements, recursive
        if (length > 2 && --length > initialLength / 2)
            resultCheck = isPalindrome(chars, length);
//        If at least a one pair hasn't coincided, always return false.
        if (resultCheck){
            char ch2 = chars.poll();
            chars.add(ch2);
            return ch1 == ch2;
        }
        return false;
    }

    @Override
    public String findSolution(OneParameterTest test) {
        if (test == null)
            return TEST_NOT_FOUND;
        queue.clear();
        resultCheck = true;
        String str = test.getParameter();
//         The string chars in the queue translating.
        for (int i = 0; i < str.length(); i++){
            queue.add(str.charAt(i));
        }
//        It is remembering the queue initial size
        initialLength = queue.size();

        return isPalindrome(queue, initialLength) ? TEXT_SUCCESSFUL_REPORT : TEXT_FAILED_REPORT;
    }

    @Override
    public String getTaskFilePath() {
        return taskFilePath;
    }

    @Override
    public void setTaskFilePath(String taskFilePath) {
        this.taskFilePath = taskFilePath;
    }

    @Override
    public String getTemp() {
        return tempFilePath;
    }

    @Override
    public void setTemp(String temp) {
        tempFilePath = temp;
    }

    @Override
    public boolean isExtraExit() {
        return false;
    }

    @Override
    public OneParameterTest getTestClassInstance() {
        return new OneParameterTest();
    }
}
