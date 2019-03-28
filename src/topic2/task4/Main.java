package topic2.task4;

import utils.GUI.SimpleGUI;
import utils.tasks.Task;
import utils.tests.OneParameterTest;

import java.util.Iterator;

class Main implements Task<OneParameterTest> {

    private String tempFilePath = reportFilePath,
            taskFilePath = "src/topic2/task4/test.txt";

    private static final String TEXT_PREVIOUS_RESULT = "The words of max length: ";
    private static final String TEXT_NEXT_RESULT = "max length: ";

    private int maxLength;

    public static void main(String[] args) {
        Main task2_4 = new Main();
        new SimpleGUI<>(task2_4).setVisible(true);
    }

    @Override
    public String findSolution(OneParameterTest test) {
        if (test == null)
            return TEST_NOT_FOUND;
        maxLength = 0;
        String[] words = test.getParameter().trim().split(" ");
        MyLinkedList<String> taskList = new MyLinkedList<String>(){{
            for (String word:
                 words) {
                add(word.trim());
            }
        }};
        MyLinkedList<String> resList = defineMaxLengthWords(taskList);

        return String.format("%s %s (%s%d)", TEXT_PREVIOUS_RESULT, resList.toString(), TEXT_NEXT_RESULT, maxLength);
    }

    private MyLinkedList<String> defineMaxLengthWords(MyLinkedList<String> taskList) {
        maxLength = 0;
        MyLinkedList<String> maxLengthWords = new MyLinkedList<>();
        Iterator<MyLinkedList<String>.MyListNode<String>> it = taskList.iterator();

        while (it.hasNext()){
            String word = it.next().getValue();
            if (word.length() > maxLength){
                maxLength = word.length();
                maxLengthWords.clear();
                maxLengthWords.add(word);
            }
            else if (word.length() == maxLength)
                maxLengthWords.add(word);
        }
        return maxLengthWords;
    }

    @Override
    public boolean isExtraExit() {
        return false;
    }

    @Override
    public OneParameterTest getTestClassInstance() {
        return new OneParameterTest();
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
}
