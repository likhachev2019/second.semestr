package topic4.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] arr = {1,-2,-99,900,1000,11,12};
        List<SortState> states = insertSort(arr);
        StatePlayer player = new StatePlayer(states);
        player.setVisible(true);
    }

    /**
     * Сортировка простыми вставками массива целых (int) чисел
     *
     * @param arr Сортируемый массив целых (примитивный тип int) чисел
     */
    private static List<SortState> insertSort(int[] arr) {
        List<SortState> states = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            // поиск места элемента в готовой последовательности
            int j;
            for (j = i - 1; j >= 0 && arr[j] > value; j--) {
                arr[j + 1] = arr[j]; 	// сдвигаем элемент направо, пока не дошли до начала массива или элементы уже меньше данного
                states.add(new SortState(Arrays.copyOf(arr,arr.length), value, i, j));
            }
            // если ни один элемент не оказался больше текущего или он стоит на первой позиции (не зашли в вложенный цикл)
            // место найдено, вставить элемент
            arr[j + 1] = value;
            if (i + 1 ==  arr.length)
                states.add(new SortState(Arrays.copyOf(arr,arr.length), value, i, j));
        }
        return states;
    }
}
