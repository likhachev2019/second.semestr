package topic7.task1;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;

class Main {

    private static final String FILE_PATH = "src/topic7/task1/test.txt";

    public static void main(String[] args) {
        String[][] adjacencyMatrix = parseFile(FILE_PATH);
        Graph graph = new Graph(adjacencyMatrix);

        System.out.println("Введите имя вершины: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        scanner.close();

        Graph.Node node = graph.get(name);
        if (node == null)
            System.out.println("Такой вершины в графе нет.");
        else {
            String res = graph.findUnreachableNodes(node);
            System.out.println(res);
        }
    }

    static int r = 0;
    static String[][] matrix = null;

    private static String[][] parseFile(String filePath) {

        try {
            File f = new File(filePath);
            Stream<String> lines = Files.lines(f.toPath());
            matrix = new String[(int)lines.count()][];
            lines = Files.lines(f.toPath());
            // Каждую строку разбиваем на столбцы и сохраняем в матрицу
            lines.forEach(line -> matrix[r++] = line.split("\t"));
        }
        catch (Exception e){
            System.out.println(e.getCause());
        }
        return matrix;
    }

}
