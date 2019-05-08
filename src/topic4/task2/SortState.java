package topic4.task2;

class SortState {

//    Копия частичного отсортированного исходного массива чисел.
    private int[] currentArray;
    private int value;
//    Позиция элемента, сортируемого в данный момент в исходном массиве
    private int i,
//    Индекс элемента, сравниваемого с данным, из отсортированной части массива.
        j;

    SortState(int[] currentArray) {
        this.currentArray = currentArray;
    }

    SortState(int[] currentArray, int value, int i, int j) {
        this.currentArray = currentArray;
        this.i = i;
        this.j = j;
        this.value = value;
    }

    String toStringCurrArray() {
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

    int get(int i) {
        return currentArray[i];
    }

    int getJIndex() {
        return j;
    }

    int getIndexValue() {
        return i;
    }

    int getValue(){
        return value;
    }
}
