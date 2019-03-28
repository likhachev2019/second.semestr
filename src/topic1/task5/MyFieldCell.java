package topic1.task5;

import javax.swing.*;

/**
 * Класс для хранения значения стандартной ячеки таблицы
 */
class MyFieldCell extends JTextField {
//      Поле содержит имя столбца, которому принадлежит данная ячейка
    private String colName;

    MyFieldCell(String colName) {
        this.colName = colName;
    }

    String getValue() {
        return getText();
    }

    int getValueAsInt() {
        try {
            return Integer.valueOf(getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return 0;
    }

    void setValue(String value) {
        setText(value);
    }

    void setValue(int value) {
        setText(String.valueOf(value));
    }

    String getColName() {
        return colName;
    }
}
