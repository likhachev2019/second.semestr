package topic6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final String TEST_PATH = "src/topic6/task1/test.txt";

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите N: (кол-во слов)");
        int n = scanner.nextInt();
        System.out.println("Введите K: (мин-ая длина)");
        int k = scanner.nextInt();
        scanner.close();
        Map<String, Integer> map;
//          VIA default map
        map = new HashMap<>();
        //          VIA my map
//        map = new MyHashMap<>(1000);
        Map<String, Integer> words = parseFile(TEST_PATH, k, map);
        List<Map.Entry<String, Integer>> result = seekWords(n, words);
        result.forEach((e) -> {
            System.out.printf("Слово: \"%s\"  (%d - длина, %d - повторений)\n", e.getKey(), e.getKey().length(), e.getValue());
        });
        if (result.size() == 0)
            System.out.println("Слов такой длины не найдено.");
    }

    private static Map<String, Integer> parseFile(String path, int minLnht, Map<String, Integer> words) {
        if (minLnht <= 0)
            throw new RuntimeException("Incorrect word length!");
        String content = null;
        try {
            content = Files.lines(Paths.get(path)).reduce("", String::concat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] wordList = content.split("\\s+");
        for (String word : wordList) {
            // отсеиваем все слова меньше указанной длины
            if (word.length() < minLnht)
                continue;
            word = word.toLowerCase();
            // Если слово встретили впервые
            if (!words.containsKey(word))
                words.put(word, 1);
                // иначе увеличиваем его кол-во на 1
            else
                words.replace(word, words.get(word) + 1);
        }
        return words;
    }

    // Возвращает слписок с ключом - слово, значение - кол-во его повторений
    private static List<Map.Entry<String, Integer>> seekWords(int wordAmount, Map<String, Integer> words) {
        if (wordAmount <= 0)
            throw new RuntimeException("Word amount must be > 0");
        List<Map.Entry<String, Integer>> res = new ArrayList<>();
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        words.entrySet().forEach(list::add);
        list.sort(Comparator.comparingInt(Map.Entry::getValue));
        for (int i = list.size() - 1, amount = 1; i >= 0 && amount < wordAmount; i--) {
            if (i == list.size() - 1)
                res.add(list.get(i));
            else if (res.get(0).getValue() != list.get(i).getValue()) {
                res.add(list.get(i));
                amount++;
            }
        }
        return res;
    }
}
