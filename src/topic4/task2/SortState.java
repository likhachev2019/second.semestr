package topic4.task2;

import java.util.Arrays;

class SortState {

//    Копия частичного отсортированного исходного массива чисел.
    private int[] currentArray;
//    Значение, сортируемое в данный момент.
    private int value;
//    Позиция элемента в исходном массиве
    private int i,
//    Индекс сравниваемого элемента с данным из отсортированно части массива.
        j;

    SortState(int[] currentArray, int i, int j, int value) {
        this.currentArray = currentArray;
        this.i = i;
        this.j = j;
        this.value = value;
    }

    String getCurrentArray() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        for(int i : currentArray) {
            builder.append(i);
            builder.append(", ");
        }
        builder.delete(builder.length()-2,builder.length());
        builder.append(" }");
        return builder.toString();
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getValue() {
        return value;
    }
}
