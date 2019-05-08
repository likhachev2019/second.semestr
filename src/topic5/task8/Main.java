package topic5.task8;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //Элементы в порядке добавления, дерево не сбалансированно.
        String str1 = "25 10 15 40 5 20 30 25";                // 40
        String str2 = "40 10 55 15 60 40 90 20 7 70 30 25";    // 90

        ArrayList<Integer> list = stringToList(str1);

        BinaryTree binaryTree = new BinaryTree(list);
        int max = binaryTree.seekMax();
        System.out.println(str1);
        System.out.println();
        System.out.println("max: " + max);
    }

    private static ArrayList<Integer> stringToList(String line) {
        String [] strArr = line.split(" ");
        ArrayList<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            list1.add(Integer.parseInt(strArr[i]));
        }
        return list1;
    }
}
