package topic1.task5;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame{

    public static void main(String[] args) {
        MainFrame task5 = new MainFrame("task5");
        task5.setVisible(true);
//        Создаём модель для нашей таблицы с заданными именами столбцов
        MyTableModel myTableModel = new MyTableModel(new String[]{"Full name", "Age", "Weight", "Growth", "Info", "etc."});
//        Добавляем в нашу форму нашу таблицу с нашей моделью таблицы и разборщиком команд пользователя.
        MyTable table = new MyTable(myTableModel);
//        Выполняем условия задачи
        table.column("Age").cell(3).setValue("123");
        int value = table.row(3).cell("Age").getValueAsInt();
        table.cell("Age", 4).setValue(value + 1);  // параметрический полиморфизм
        table.cell(5, "Age").setValue(value + 2);  // параметрический полиморфизм
        System.out.println(value);

        task5.add(table);
        task5.pack();
        task5.setLocationRelativeTo(null);
    }

    private MainFrame(String title){
        super(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
