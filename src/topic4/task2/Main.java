package topic4.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] arr = {};
        List<SortState> states = insertSort(arr);
        StatePlayer player = new StatePlayer(states);
        player.setVisible(true);
    }

    /**
     * Сортировка простыми вставками массива целых (int) чисел
     *
     * @param arr Сортируемый массив целых (примитивный тип int) чисел
     */
    static List<SortState> insertSort(int[] arr) {
        List<SortState> states = new ArrayList<>();
        states.add(new SortState(Arrays.copyOf(arr,arr.length)));
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            // поиск места элемента в готовой последовательности
            int j;
            for (j = i - 1; j >= 0 && arr[j] > value; j--) {
                arr[j + 1] = arr[j]; 	// сдвигаем элементы вправо, пока не дошли до нужного места взятого value
                arr[j] = value;
                states.add(new SortState(Arrays.copyOf(arr,arr.length), value, i, j+1));
            }
            // место найдено, вставить элемент
            arr[j + 1] = value;
            // если ни один элемент не оказался больше текущего или он стоит на первой позиции (не зашли в вложенный цикл)
            if (j == i - 1 || i == 0)
                states.add(new SortState(Arrays.copyOf(arr,arr.length), value, i, -1));
        }
        return states;
    }
}
