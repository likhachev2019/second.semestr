package topic7.task1;

import java.util.ArrayList;
import java.util.List;

class Graph {
//    Все вершины графа
    private List<Node> nodes;
//      Получает матрицу смежности
    Graph(String[][] adjacencyMatrix){
        nodes = getNodes(adjacencyMatrix);
    }

    private List<Node> getNodes(String[][] adjacencyMatrix) {
        List<Node> nodes = new ArrayList<>();
        if (!isAdjacencyMatrix(adjacencyMatrix))
            throw new RuntimeException("Wrong adjacency matrix");
        // Добавляем все элементы в список, сохраняя только имя
        for (int i = 1; i < adjacencyMatrix.length; i++)
            nodes.add(new Node(adjacencyMatrix[i][0]));
        // Для каждого элемента формируем список связей
        for (int i = 1; i < adjacencyMatrix.length; i++) {
            List<Node> links = new ArrayList<Node>();
            for (int j = 1; j < adjacencyMatrix[i].length; j++) {
                if (Integer.valueOf(adjacencyMatrix[i][j]) == 1){
                    // индекс столбца соответсвует связи элементом в списке под индексом j - 1;
                    links.add(nodes.get(j - 1));
                }
            }
            nodes.get(i-1).setLinks(links);
        }
        // Получаем список узлов со всеми связями
        return nodes;
    }

    private boolean isAdjacencyMatrix(String[][] adjacencyMatrix) {
        if (adjacencyMatrix == null || adjacencyMatrix.length == 0 || adjacencyMatrix[0].length < 2)
            return false;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[i].length != adjacencyMatrix.length)
                return false;
        }
        return true;
    }

    Node get(String name) {
        Node res = null;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals(name))
                res = nodes.get(i);
        }
        return res;
    }

    String findUnreachableNodes(Node node) {
        StringBuilder res = new StringBuilder();
        res.append("Недостижимые вершины: ");
        for (int i = 0; i < nodes.size(); i++) {
            if (!node.links.contains(nodes.get(i)))
                res.append(String.format("%s ", nodes.get(i)));
        }
        return res.toString();
    }

    class Node {
        String name;
//        Связи с другими вершинами
        List<Node> links;

        void setLinks(List<Node> links) {
            this.links = links;
        }

        Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
