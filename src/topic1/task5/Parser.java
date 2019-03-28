package topic1.task5;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс для разбора команд в командной строке таблицы.
 * При его создании необходимо указать к какой таблице он
 * будет обращаться для выполнения команд и уведомлении об ошибках.
 */
class Parser {
//      Объект, к которому обращается синтаксический анализатор для выполнения команд
    private MyTable table;

    private MyVector curVector;
    private MyFieldCell curCell;

    private static final String[] keyWords = {"cell","column", "row", "setValue", "getValue", "getValueAsInt"};

    private String objCurClass;

    Parser(MyTable table) {
        this.table = table;
    }

    void execute(ActionEvent e) {
        JPanel p = table.getCmd();
        JTextField t = (JTextField) p.getComponent(1);
        String com = t.getText();
        boolean r = com.matches(String.format("^table(\\.(%1$s|%2$s|%3$s|%4$s|%5$s|%6$s)\\(.*\\))+$",
                                    keyWords[0], keyWords[1], keyWords[2], keyWords[3], keyWords[4], keyWords[5]));
        table.setCmd(r);
        String cellVal = null;
        objCurClass = "MyTable";
        curCell=null;
        curVector=null;

        if (r){
            int a = com.indexOf('('), b, dot = 5;//начинаем с start

            do{
               String w = getKeyWord(com, dot, a);
               b = findRightBrake(com, a);
               String[] params = getParams(com, a, b);
                try {
                    cellVal=doMethod(w, params);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    table.setCmd(!r);
                    break;
                }
                dot=b+1;
                a=com.indexOf('(',dot);

            }while (b!=com.length()-1);
        }
        if (cellVal != null) {
            table.showVal(cellVal);
        }
        else
            table.hideVal();
    }

    private String doMethod(String keyWord, String[] params) throws Exception {
        switch (objCurClass) {
            case "MyTable":{
                if (keyWord.equals(keyWords[0]))
                    objCurClass = "MyCell";
                else
                    objCurClass = "MyVector";
                doMethod(keyWord, params, table);
                break;}
            case "MyVector":{
                curCell = doMethod(keyWord, params, curVector);
                objCurClass = "MyCell";
                break;}
            case "MyCell":{
                return doMethod(keyWord, params, curCell);
                }
        }
        return null;
    }

    private String getKeyWord(String str,int startIndex, int endIndex) {
        for (String w :
                keyWords) {
            if (w.equals(str.substring(startIndex + 1, endIndex)))
                return w;
        }

        return null;
    }

    private int findRightBrake(String str, int startIndex) {
        int count = 0;
        int endIndex=str.indexOf(')',startIndex+1), otherBrake=str.indexOf('(',startIndex + 1);

        do{
            if (endIndex > otherBrake && otherBrake>0){
                count++;
                startIndex = otherBrake;
                otherBrake = str.indexOf('(',startIndex + 1);
            }
            else {
                endIndex =str.indexOf(')',startIndex+1);
                startIndex = endIndex;
                count--;
            }
        }while (count > 0); //Пока не найдём соотвествующую закрывающую скобку

        return endIndex;
    }

    private String[] getParams(String str, int startIndex, int endIndex) {
        return str.substring(++startIndex, endIndex).split(",");
    }

    private void doMethod(String keyWord, String[] params, MyTable obj) throws Exception{

        if (keyWord.equals(keyWords[0])) {  //cell
            if (params.length != 2) {
                throw new Exception();
            }
            try {
                curCell= obj.cell(Integer.valueOf(params[0].trim()), params[1].trim());
            } catch (Exception e) {
                curCell= obj.cell(params[0].trim(), Integer.valueOf(params[1].trim()));
            }
            return;
        }
        if (keyWord.equals(keyWords[1])) { //column
            if (params.length != 1) {
                throw new Exception();
            }
           curVector= obj.column(params[0]);
            return;
        }
        if (keyWord.equals(keyWords[2])) { //row
            curVector= obj.row(Integer.valueOf(params[0].trim()));
            return;
        }

        throw new Exception();
    }

    private MyFieldCell doMethod(String keyWord, String[] params, MyVector obj) throws Exception{
        if (keyWord.equals(keyWords[0])) {
            try {
                return obj.cell(Integer.valueOf(params[0]));
            } catch (Exception e) {
                return obj.cell(params[0].trim());
            }
        }
        throw new Exception();
    }

    private String doMethod(String keyWord, String[] params, MyFieldCell obj) throws Exception{
        if (keyWord.equals(keyWords[3])) { //setValue
            if (params.length == 1)
                obj.setValue(params[0]);

            return null;
        }
        if (keyWord.equals(keyWords[4])) { //getValue
            return obj.getValue();
        }
        if (keyWord.equals(keyWords[5])) { //getValueAsInt
            return String.valueOf(obj.getValueAsInt());
        }
        throw new Exception();
    }
}
