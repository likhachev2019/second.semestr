package topic1.task5;

import javax.swing.*;
import java.awt.*;

/**
 * Класс представляет собой заглавную ячейку для вектора
 */
class MyLabelCell extends JLabel {

    private static final int THICKNESS_BORDER = 2;  // Толщина бордера в заголовках векторов
    private static final int SIZE_LABEL_TEXT = 5;   // Размер текста в заголовках вектора
    private static final Color COLOR_VECTOR_HEAD_BACKGROUND = Color.BLUE;   // Цвет фона заголовка вектора
    private static final Color COLOR_VECTOR_HEAD_BORDER = Color.GREEN;   // Цвет бордера заголовка вектора
    private static final String COLOR_ROW_HEAD_TEXT = "red";    //  Цвет текста заглавной ячейки вектора-строки
    private static final String COLOR_COL_HEAD_TEXT = "yellow";    //  Цвет текста заглавной ячейки вектора-столбца
    private static Dimension sizeLabelPreferred = new Dimension(100, 80); // Предпочительные размеры ячейки

    private String colorHeadCell;
    private String textCellHead;

    MyLabelCell() {

    }

    MyLabelCell(MyVector vector){
        textCellHead = String.format("%s", vector.getName());
        colorHeadCell = vector.getType().equals(MyVector.getRowType()) ? COLOR_ROW_HEAD_TEXT : COLOR_COL_HEAD_TEXT;

        setText(String.format("<html><font size='%3$d' color='%1$s'>%2$s</font></html>", colorHeadCell, textCellHead, SIZE_LABEL_TEXT));
        setHorizontalAlignment(SwingConstants.CENTER);
        setToolTipText(textCellHead);
        setOpaque(true);
        setBackground(COLOR_VECTOR_HEAD_BACKGROUND);
        setBorder(BorderFactory.createLineBorder(COLOR_VECTOR_HEAD_BORDER, THICKNESS_BORDER));
        setPreferredSize(sizeLabelPreferred);
    }

}
