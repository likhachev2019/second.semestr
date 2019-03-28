package topic1.task5;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

/**
 *  Класс служит для хранения ссылок на ячейки таблицы,
 *  а также работы с ними. Альтернатива классам MyCol и MyRow.
 */
class MyVector {
    private static final String ROW_TYPE = "Row";   //  Строковое представление типа вектора-строки
    private static final String COL_TYPE = "Col";   //  Строковое представление типа вектора-столбца
//      Мета-данные вектора
    private String name;
    private int number;
    private String type;
//      Список с ссылками на все ячейки вектора
    private List<JComponent> cells = new ArrayList<>();

    MyVector(String type, int number){
        this.type = type;
        this.number = number;
        name = String.format("%s %d", type, number);
    }

    /**
     * Этот конструктор позволяет задать независимое имя вектору
     *
     * @param type Тип вектора: строки или стобец
     * @param number Порядковый номер вектора в списке строк или столбцов
     * @param name  Произвольное имя для вектора
     */
    MyVector(String type, int number, String name) {
        this.type = type;
        this.number = number;
        this.name = name;
    }

    /**
     * Возвращает указанную ячейку из вектора.
     *
     * @param number Номер ячейки в векторе.
     * @return Ячейка в векторе под указанным номером.
     * @throws RuntimeException Если не существует стандратной ячейки с таким номером
     */
    MyFieldCell cell(int number) {
        if (number <= 0 || number >= cells.size())
            throw new RuntimeException("No cell with such number in this vector!");

        return (MyFieldCell) cells.get(number);
    }

    /**
     * Возвращает указанную ячейку из вектора-строки.
     *
     * @param colName Имя столбца ячейки
     * @return Ячейка в векторе-строке под указанным столбцом.
     * @throws RuntimeException Если не существует стандратной ячейки с таким столбцом в этом векторе
     */
    MyFieldCell cell(String colName) {
        if (colName == null) {
            throw new RuntimeException("Col name is null!");
        }

        for (int c = 1; c < cells.size(); c++) {
            if (colName.equals(((MyFieldCell) cells.get(c)).getColName()))
                return (MyFieldCell) cells.get(c);
        }

        throw new RuntimeException("No cell with such column name in this vector!");
    }

    void addCell(JComponent cell) {
        if (cell == null)
            throw new RuntimeException("Adding null cell");

        cells.add(cell);
    }

    void delCell(int index) {
        cells.remove(index);
    }

    int getSize() {
        return cells.size();
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }

    static String getRowType() {
        return ROW_TYPE;
    }

    static String getColType() {
        return COL_TYPE;
    }

    List<JComponent> getCells() {
        return cells;
    }
}
