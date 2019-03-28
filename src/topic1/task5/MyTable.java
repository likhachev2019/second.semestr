package topic1.task5;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.*;
/**
 * Класс служит для отрисовки компонентов таблицы и обработки
 * действия пользователя. Данные меняются посредством обращения к
 * методам модели-таблицы, которая задаётся пользователем.
 */
class MyTable extends JPanel{

//      Константы с названиями и подсказками
    private static final String TITLE_TOOL_BAR = "The tool bar";
    private static final String TITLE_ADDR_BUT = "Add row";
    private static final String TITLE_ADDC_BUT = "Add col";
    private static final String TITLE_DELC_BUT = "Del col";
    private static final String TITLE_DELR_BUT = "Del row";
    private static final String TOOLTIP_ADDR_BUT = "Append one a row.";
    private static final String TOOLTIP_ADDC_BUT = "Append one a column.";
    private static final String TOOLTIP_DELC_BUT = "Remove the last column.";
    private static final String TOOLTIP_DELR_BUT = "Remove the last row.";
    private static final String TOOLTIP_CMD = "Command line.";
    private static final String LABEL_CMD = "Enter command: ";
    private static final String TEXT_START_CMD_LINE = "table.";
    private static final String TOOLTIP_CMD_LINE = "Commands: cell(\"col_name\", 5), column(\"name\", 5), row(5), cell(5), " +
                                                            "getValue(), getValueAsInt(), setValue(\"String\"), setValue(5)...";
//    Настраиваемые при создании экземпляра таблицы объекты для работы с ним.
    private MyTableModel tableModel;
    private Parser parser;
//      Компоненты нашей панельки
    private JToolBar toolBar = new JToolBar(TITLE_TOOL_BAR);
    private JButton addRowButton = new JButton(TITLE_ADDR_BUT);
    private JButton addColButton = new JButton(TITLE_ADDC_BUT);
    private JButton delColButton = new JButton(TITLE_DELC_BUT);
    private JButton delRowButton = new JButton(TITLE_DELR_BUT);
    private JPanel tableZone = new JPanel();
    private JPanel cmd = new JPanel();
    private JTextField cmdLineField = new JTextField(40);

    MyTable(MyTableModel tableModel) {
        this.tableModel = tableModel;
//          Говорим парсеру, что обращаться за выполнением команд надо к данному экземпляру таблицы
        this.parser = new Parser(this);
//        Нашей главной панели устанавливаем стандарный менеджер расположения.
        setLayout(new BorderLayout());
        initTableZone();
//        Помещаем табличку с ячейками в прокручивающуюся панельку для её авторегулировки
        JScrollPane scrollPane = new JScrollPane(tableZone);

//        Саму табличку помещаем по центру
        add(scrollPane, BorderLayout.CENTER);
        initToolBar();
        initButtonListeners();
//        Размещаем панельку инструментов вверху
        add(toolBar, BorderLayout.NORTH);
//        Командная внизу
        initCommandLine();
        add(cmd, BorderLayout.SOUTH);
    }

    /**
     * Возвращает указанный столбец из модели таблицы.
     *
     * @param name Имя столбца в модели таблицы.
     * @return Столбец в модели таблицы под указанным именем.
     * @throws RuntimeException Если не существует столбца с таким именем в моделе таблицы.
     */
    MyVector column(String name) {
        if (name == null)
            throw new RuntimeException("Column name is null!");

        List<MyVector> cols = tableModel.getCols();

        for (int c = 1; c < cols.size(); c++) {
            if (name.equals(cols.get(c).getName()))
                return cols.get(c);
        }

        throw new RuntimeException("No column with such name!");
    }

    /**
     * Возвращает указанную строку из модели таблицы.
     *
     * @param number Номер строки в модели таблицы.
     * @return Строка в модели таблицы под указанным номером.
     * @throws RuntimeException Если не существует ненолевой строки с таким номером в модели таблицы.
     */
    MyVector row(int number) {
        if (number <= 0 || number >= tableModel.getRows().size())
            throw new RuntimeException("No row with such number!");

        return tableModel.getRows().get(number);
    }

    /**
     * Возвращает ячейку по указанному столбцу и номеру строки.
     * !Возможны исключения из вызываемых им методов.
     *
     * @param columnName Имя столбца искомой ячейки.
     * @param rowNumber Номер строки искомой ячейки.
     * @return  Искомая ячейка.
     */
    MyFieldCell cell(String columnName, int rowNumber) {
        return column(columnName).cell(rowNumber);
    }

    /**
     * см. описание метода выше.
     */
    MyFieldCell cell(int rowNumber, String columnName) {
        return cell(columnName, rowNumber);
    }

    /**
     * Метод настраивает и иницилизирует (заполняет) зону с ячейками
     */
    private void initTableZone() {
//        Настраиваем табличное расположение в панели для ячеек, устанавливая соответсвующие размеры
        tableZone.setLayout(new GridLayout(tableModel.getRowCount(),tableModel.getColCount()));

        List<MyVector> rows = tableModel.getRows();
//        Перебираем все ячейки наших строк и добавляем их на нашу панель
        for (MyVector row : rows) {
            List<JComponent> cells = row.getCells();
            for (JComponent cell : cells) {
//                GridLayout заполняет построчно
                tableZone.add(cell);
            }
        }
    }

    /**
     * Иницилизируем (заполняем) панельку инструментов
     */
    private void initToolBar() {
//        Добавляем к кнопкам всплывающие подсказки, а затем ставим их в панель инструментов
        addRowButton.setToolTipText(TOOLTIP_ADDR_BUT);
        toolBar.add(addRowButton);
        addColButton.setToolTipText(TOOLTIP_ADDC_BUT);
        toolBar.add(addColButton);
//        Делаем отступ
        toolBar.addSeparator();
        delRowButton.setToolTipText(TOOLTIP_DELR_BUT);
        toolBar.add(delColButton);
        delColButton.setToolTipText(TOOLTIP_DELC_BUT);
        toolBar.add(delRowButton);
    }

    /**
     * Настраиваем командую строку.
     */
    private void initCommandLine() {
        cmd.setToolTipText(TOOLTIP_CMD);
        cmd.add(new JLabel(LABEL_CMD));
        cmd.add(cmdLineField);
        cmdLineField.setText(TEXT_START_CMD_LINE);
        cmdLineField.setToolTipText(TOOLTIP_CMD_LINE);
        cmdLineField.addActionListener(parser::execute);
    }

    void setCmd(boolean bol){
        if (!bol){
            cmd.setBackground(Color.RED);
        }
        else{
            cmd.setBackground(Color.WHITE);
        }
    }

    JPanel getCmd(){
        return cmd;
    }

    /**
     * Метод добавляет слушатели всем кнопкам
     */
    private void initButtonListeners() {
//        Слушатель к кнопке "Добавить строку"
        addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableModel.addVector(MyVector.getRowType());
//                Удаляем все ячейки в таблице и иницилизируем снова, не забываем сказать панели обновить содержимое
                tableZone.removeAll();
                initTableZone();
                revalidate();
//                Перерисовываем принудительно, иначе могут остаться неактивные ячейки, при удалении строк, до добавления доп. столбцов (тут на всякий случай)
                repaint();
            }
        });
//        Слушатель к кнопке "Добавить столбец"
        addColButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tableModel.addVector(MyVector.getColType());
                tableZone.removeAll();
                initTableZone();
                revalidate();
                repaint();
            }
        });
//        Слушатель к кнопке "Удалить строку"
        delRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tableModel.delRow();
                tableZone.removeAll();
                initTableZone();
                revalidate();
                repaint();
            }
        });
//        Слушатель к кнопке "Удалить столбец"
        delColButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tableModel.delCol();
                tableZone.removeAll();
                initTableZone();
                revalidate();
                repaint();
            }
        });
    }

    void showVal(String sellVal) {
        JOptionPane.showMessageDialog(null,"Value: " + sellVal);
    }

    void hideVal() {
        //outValue.setText(null);
    }
}
