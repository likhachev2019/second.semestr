package topic1.task5;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект класса содержит списки столбцов и строк,
 * а также методы и поля для работы с ними.
 */
class MyTableModel {
//      Списки с ссылками на все ячейки таблицы. Для удобства ссылки дублируются в два вектора — строки и стобцы
    private List<MyVector> cols = new ArrayList<>();
    private List<MyVector> rows = new ArrayList<>();
//      Поле для хранения пользовательских имён стобцов
    private String[] colNames = new String[0];

    /**
     * Создаёт модель таблицы с указанным кол-вом строк и столбцов.
     * По умолчанию всегда создаёт угловую пустую ячейку.
     *
     * @param vecCount Кол-во автоматически создаваемых столбцов
     * @throws RuntimeException Если кол-во столбцов для создания меньше 0.
     */
    MyTableModel(int vecCount) {
        if (vecCount < 0)
            throw new RuntimeException("Vector count must be > 0!");

        createSomeVec(1 + vecCount);
    }

    /**
     * Позволяет задать имена некоторому кол-ву столбцов.
     * По умолчания всегда создаёт угловую пустую ячейку.
     * Дополнительно создаёт кол-во ячеек, равное длине массива.
     *
     * @param colNames Имена автоматически создаваемых столбцов
     * @throws RuntimeException Если массив имён равено пустой ссылке (null)
     */
    MyTableModel(String[] colNames) {
        if (colNames == null)
            throw new RuntimeException("Column names equals null.");

        this.colNames = colNames;
        createSomeVec(1 + colNames.length);
    }

    private void createSomeVec(int vecCount) {
        for (int i = 0; i < vecCount; i++) {
            addVector(MyVector.getRowType());
            addVector(MyVector.getColType());
        }
    }

    void addVector(String typeName) {
        List<MyVector> newVecType, anotherVecType;
        String nameCol = null;
//        Определяем столбец или строку надо добавить
        if (typeName.equals(MyVector.getRowType())){
            newVecType = rows;
            anotherVecType = cols;
        }
        else {
            newVecType = cols;
            anotherVecType = rows;
//          Если создаётся не нолевой столбец и его номер не больше кол-ва заданных имён, определим имя
            nameCol = cols.size()<=colNames.length && cols.size() > 0 ? colNames[cols.size()-1] : String.format("%s %d",typeName, newVecType.size());
        }
//        Если данный вектор - это не нолевой столбец, ему даётся сразу имя
//        иначе вызов конструктора с автоматической генерацией имени
        MyVector newVector;
        if (nameCol == null)
            newVector = new MyVector(typeName, newVecType.size());
        else
            newVector = new MyVector(typeName, newVecType.size(), nameCol);

        JComponent cell;
//          В списке ортогональных новому вектору каждому добавляем в конец новую ячейку.
//          Соответсвенно новый вектор будет содержать кол-во ячеек равное кол-ву ортогональных векторов
        for (int i = 0; i < anotherVecType.size(); i++) {
//          В ячейку нулевого вектора (столбца или строки) добавляем этикетку - неизменяемую
            if (i == 0) {
                cell = new MyLabelCell();
//          Если это не первая ячейка в нолевом векторе, то её раскрашиваем спец. конструктором
                if (newVecType.size() != 0) {
                    cell = new MyLabelCell(newVector);
                }
            }
//            Если это обычная ячейка, делаем её текстовым полем
            else{
//                  Если добавляется строка, определяем столбец каждой ячейки
                if (nameCol == null)
                    cell = new MyFieldCell(anotherVecType.get(i).getName());
//                  Если добавляется столбец, присвоить ячейке имя столбца легко
                else
                    cell = new MyFieldCell(nameCol);
            }

            newVector.addCell(cell);
            anotherVecType.get(i).addCell(cell);
        }
        newVecType.add(newVector);
    }

    /**
     *  Так как строки и столбцы хранят ссылки на одни и те же ячейки, удалять их
     * можно с помощью одного метода.
     *
     *  Удаляет строку либо столбец таблицы, в зависимости от порядка аргументов
     * @param vectorList  Из этого списка удалит крайний вектор
     * @param anotherVectorList А из этого удалит все элементы, пересекающиеся с удалённым вектором
     */
    private void delVector(List<MyVector> vectorList, List<MyVector> anotherVectorList) {
//        Левую верхнюю ячейку всегда оставляем
        if (vectorList.size() <= 1)
            return;
//        Удаляем крайний вектор из полученного списка
        vectorList.remove(vectorList.size() - 1);
//        Из векторов другого списка удаляем крайние ячейки (принадлежащие удалённому вектору)
        for (MyVector vec: anotherVectorList) {
            vec.delCell(vec.getSize() - 1);
        }
    }

    /**
     * Удаляет крайнюю строку в таблице
     */
    void delRow() {
        delVector(rows, cols);
    }

    /**
     * Удаляет крайний столбец в таблице
     */
    void delCol() {
        delVector(cols, rows);
    }

    List<MyVector> getRows() {
        return rows;
    }

    List<MyVector> getCols() {
        return cols;
    }

    int getRowCount() {
        return rows.size();
    }

    int getColCount() {
        return cols.size();
    }
}
