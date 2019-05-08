package topic5.task8;

import java.util.ArrayList;

public class BinaryTree {

    private class TreeNode {
        int data;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int data) {
            this.data = data;
        }
    }

    private TreeNode root = null;

    BinaryTree(ArrayList<Integer> list) {
        for (Integer item : list) {
            int i = item;
            add(i);
        }
    }

    private boolean isEmpty() {
        return root == null;
    }

    public void add(int data) {
        if (isEmpty()) {
            this.root = new TreeNode(data);
            return;
        }
        addRecursion(data, root);
    }
//      Рекрсивно добавляем элемент в дерево
    private void addRecursion(int data, TreeNode node) {
//        Если элемент больше потомка, помещаем в правый узел (когда дойдём до null-го)
        if (data > node.data) {
            if (node.right == null) {
                node.right = new TreeNode(data);
                return;
            }
            addRecursion(data, node.right);
//        Если элемент больше потомка, помещаем в левый узел
        } else {
            if (node.left == null) {
                node.left = new TreeNode(data);
                return;
            }
            addRecursion(data, node.left);
        }
    }

    int seekMax() {
        if (isEmpty())
            throw new UnsupportedOperationException();
        TreeNode node = root;
//        Опускаемся по правой ветке дерева, пока не дойдём до крайнего (максимального) элемента
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }
}
