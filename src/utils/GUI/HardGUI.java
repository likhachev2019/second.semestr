package utils.GUI;

import utils.Utils;
import utils.internetHelp.RotatedIcon;
import utils.internetHelp.TextIcon;
import utils.tasks.HardGUIAbstractTask;
import utils.tests.OneParameterTest;
import utils.tests.TaskTest;
import utils.tests.TwoParameterTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class HardGUI<Task extends HardGUIAbstractTask> extends SimpleGUI {

    private static final String TEXT_READ_TABLE_BUTTON = "Read from the table.",
                                TEXT_READ_FILE_BUTTON = "Read from the task file",
                                TEXT_ADD_ROW_BUTTON = "Add a row!",
                                TEXT_CLEAN_TABLE_BUTTON = "Clear all!",
                                TEXT_EXTRA_PARAMETER_PANEL = "Extra parameter: ",
                                TEXT_SOLUTION_NOT_FOUND = "Check the parameters!",
                                TEXT_PARSE_TESTS_PANEL_LABEL = "Test balance: ",
                                TEXT_SCAN_NEXT_TEST_BUTTON = "Scan next",
                                TEXT_END_TESTS = "Tests have ended.",
                                TEXT_ADD_COL_BUTTON = "+C",
                                TEXT_DEL_COL_BUTTON = "-C";

    private static final int NUMBER_TEXT_FIELDS_COLUMNS = BUTTON_WIDTH / 20,
                            FIRST_BUTTON_MARGIN_LEFT = (MAIN_PANEL_WIDTH - BUTTON_WIDTH * 2 - PADDING_MAIN_PANEL_X) / 2;

    private static int textFieldIntoEPPanelIndex, textFieldIntoParseTestsPanelIndex;

    private static JTable inputDataTable, outputDataTable;

    private JPanel mainPanel, EPPanel, parseTestsPanel = new JPanel();
    private Task task;
    private TaskTest taskTestInstance;

    public HardGUI(Task task) {
        the = task;
        setTitle(task.getClass().getSimpleName());
        this.task = task;
        taskTestInstance = task.getTestClassInstance();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelInit();
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    void panelInit() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        mainPanel.setLayout(null);

        taskFileInputTableInit(mainPanel);
        dataTablesInit();
        inputPanelInit(mainPanel, inputDataTable);
        if (task.getOutputDataInTableFormat())
            outputPanelInit(mainPanel, outputDataTable);
        else{
            outputPanelInit(mainPanel, reportArea);
            addColButtonInit(mainPanel);
            delColButtonInit(mainPanel);
        }
        if (taskTestInstance.getClass() == Utils.CLASS_TWO_PARAMETER_TEST)
            extraParameterPanelInit(mainPanel);

        addRowButtonInit(mainPanel);
        readTableButtonInit(mainPanel);
        readFileButtonInit(mainPanel);
        cleanTableButtonInit(mainPanel);

        add(mainPanel);
    }

    private void delColButtonInit(JPanel target) {
        JButton delColButton = new JButton(TEXT_DEL_COL_BUTTON);
        delColButton.setSize(PADDING_MAIN_PANEL_X + 6 , BUTTON_HEIGHT / 2 );
        delColButton.setLocation(MAIN_PANEL_WIDTH - delColButton.getWidth(), MARGIN_TOP_INPUT_PANEL );

        target.add(delColButton);
        target.setComponentZOrder(delColButton, 0);
        delColButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressDelColButton(inputDataTable);
            }
        });
    }

    private void pressDelColButton(JTable target) {
        DefaultTableModel m = (DefaultTableModel) target.getModel();
        int currentCount = m.getColumnCount();

        if (currentCount > 0)
            m.setColumnCount(currentCount - 1);
    }

    private void addColButtonInit(JPanel target) {
        JButton addColButton = new JButton(TEXT_ADD_COL_BUTTON);
        addColButton.setSize(PADDING_MAIN_PANEL_X + 10, BUTTON_HEIGHT / 2 );
        addColButton.setLocation(0, MARGIN_TOP_INPUT_PANEL );

        target.add(addColButton);
        target.setComponentZOrder(addColButton, 0);
        addColButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressDeldColButton(inputDataTable);
            }
        });
    }

    private void pressDeldColButton(JTable target) {
        DefaultTableModel m = (DefaultTableModel) target.getModel();
        m.addColumn(m.getColumnCount());
    }

    private void extraParameterPanelInit(JPanel target) {
        EPPanel = new JPanel();
        EPPanel.setSize(BUTTON_WIDTH *3 / 2, BUTTON_HEIGHT);
        EPPanel.setLocation(0, MARGIN_TOP_BUTTON);

        labelInit(EPPanel, TEXT_EXTRA_PARAMETER_PANEL);
        textFieldInit(EPPanel, NUMBER_TEXT_FIELDS_COLUMNS);
        textFieldIntoEPPanelIndex = 1;
        target.add(EPPanel);
    }

    private void dataTablesInit(){
        Object[] columnNames = task.getColumnNames();
        TableModel inputTableModel = new DefaultTableModel( null, columnNames),
                outputTableModel = new DefaultTableModel( null, columnNames);
        inputDataTable = new JTable(inputTableModel);

        if (task.getOutputDataInTableFormat())
            outputDataTable = new JTable(outputTableModel);
    }

    private void readTableButtonInit(JPanel target){
        Button readTableButton = new Button(TEXT_READ_TABLE_BUTTON);
        readTableButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        readTableButton.setLocation(FIRST_BUTTON_MARGIN_LEFT, MARGIN_TOP_BUTTON);

        target.add(readTableButton);
        readTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressReadTableButton();
            }
        });
    }

    private void readFileButtonInit(JPanel target) {
        Button readFileButton = new Button(TEXT_READ_FILE_BUTTON);
        readFileButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        readFileButton.setLocation(FIRST_BUTTON_MARGIN_LEFT + BUTTON_WIDTH + PADDING_MAIN_PANEL_X, MARGIN_TOP_BUTTON);

        target.add(readFileButton);
        readFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickReadFileButton();
            }
        });
    }

    private void cleanTableButtonInit(JPanel target) {
        JButton cleanTableButton = new JButton();
        cleanTableButton.setSize(PADDING_MAIN_PANEL_X / 2, BUTTON_HEIGHT * 4);
        cleanTableButton.setLocation(PADDING_MAIN_PANEL_X / 4, MARGIN_TOP_INPUT_PANEL + BUTTON_HEIGHT);

        TextIcon text = new TextIcon(cleanTableButton, TEXT_CLEAN_TABLE_BUTTON, TextIcon.Layout.HORIZONTAL);
        RotatedIcon rotatedText = new RotatedIcon(text, RotatedIcon.Rotate.DOWN);
        cleanTableButton.setIcon(rotatedText);

        target.add(cleanTableButton);
        cleanTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressCleanTableButton(inputDataTable);
            }
        });
    }

    private void addRowButtonInit(JPanel target) {
        JButton addRowButton = new JButton();
        addRowButton.setSize(PADDING_MAIN_PANEL_X / 2, BUTTON_HEIGHT * 4);
        addRowButton.setLocation(MAIN_PANEL_WIDTH - PADDING_MAIN_PANEL_X / 4 * 3, MARGIN_TOP_INPUT_PANEL + BUTTON_HEIGHT);

        TextIcon text = new TextIcon(addRowButton, TEXT_ADD_ROW_BUTTON, TextIcon.Layout.HORIZONTAL);
        RotatedIcon rotatedText = new RotatedIcon(text, RotatedIcon.Rotate.DOWN);
        addRowButton.setIcon(rotatedText);

        target.add(addRowButton);
        addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressAddRowButton(inputDataTable);
            }
        });
    }

    private void pressAddRowButton(JTable target) {
        DefaultTableModel m = (DefaultTableModel) target.getModel();
        m.setRowCount(m.getRowCount() + 1);
    }

    private void pressCleanTableButton(JTable target) {
        DefaultTableModel m = (DefaultTableModel) target.getModel();
        m.setRowCount(0);
    }

    private void pressReadTableButton() {
        TaskTest test = null;
        String secondParam = readSecondParam();

        if (taskTestInstance.getClass() == Utils.CLASS_TWO_PARAMETER_TEST){
            Integer firstParam = readFirstParam();
            if (secondParam == null || firstParam == null){
                JOptionPane.showMessageDialog(null, TEXT_SOLUTION_NOT_FOUND);
                return;
            }
            test = new TwoParameterTest(firstParam, secondParam);
        }

        if (taskTestInstance.getClass() == Utils.CLASS_ONE_PARAMETER_TEST){
            if (secondParam == null){
                JOptionPane.showMessageDialog(null, TEXT_SOLUTION_NOT_FOUND);
                return;
            }
            test = new OneParameterTest(secondParam);
        }

        String report = task.findSolution(test);
        Utils.writeToFile(task.getTemp(), report);

        if (task.getOutputDataInTableFormat())
            outputTableReport(outputDataTable, task.getOutputData());
        else
            outputTextReport(reportArea, report);
    }

    private Integer readFirstParam() {
        String almostFP = scanText(EPPanel, textFieldIntoEPPanelIndex).trim();
        return Utils.isTestNumber(almostFP) ? Integer.valueOf(almostFP) : null;
    }

    private String readSecondParam() {
        StringBuilder builder = new StringBuilder();
        String listItemSeparator = task.LIST_ITEM_SEPARATOR,
                itemSeparator = String.valueOf(Utils.SEPARATOR) ;
        int columnCount = inputDataTable.getColumnCount(),
                rowCount = inputDataTable.getRowCount();

        for (int row = 0; row < rowCount ; row++) {
            for (int col = 0; col < columnCount; col++) {
                Object valueAt = (inputDataTable.getValueAt(row, col));
                if (valueAt == null)
                    break;
                builder.append(valueAt);
                if (col < columnCount - 1)
                    builder.append(itemSeparator);
            }
            if (row < rowCount - 1)
                builder.append(listItemSeparator);
        }
        return builder.length() > 0 ? builder.toString() : null;
    }

    private void clickReadFileButton() {
        cleanTables();
        the.setTaskFilePath(scanText(taskFileInput, 0));

        if ( ! Utils.isFilePath(the.getTaskFilePath()) ){
            taskFileInput.setBackground(Color.RED);
            outputPanel.setBackground(null);
            JOptionPane.showMessageDialog(taskFileInput, ERR_TASKFILEPATH_SELECTION);
        }
        else if ( ! Utils.isFilePath(the.getTemp()) ){
            outputPanel.setBackground(Color.RED);
            taskFileInput.setBackground(Color.GREEN);
            JOptionPane.showMessageDialog(null, String.format("%s %s %s",
                    ERR_REPORTFILEPATH_SELECTION, "path: ", the.getTemp()));
            fileChooserInit(TEXT_CHOOSER_REPORT_FILE, 1);
        }
        else {
            outputPanel.setBackground(Color.GREEN);
            taskFileInput.setBackground(Color.GREEN);
            int testsCount = 0;

            try{
                testsCount = Utils.countTestsIntoFile(the.getTaskFilePath());
            }
            catch (FileNotFoundException e){}

            if (testsCount > 0)
                parseTestsPanelInit(mainPanel, testsCount);
            else
                JOptionPane.showMessageDialog(null, HardGUIAbstractTask.TEST_NOT_FOUND);
        }
    }

    private void parseTestsPanelInit(JPanel target, int testsCount) {
        getContentPane().remove(parseTestsPanel);
        parseTestsPanel.invalidate();

        parseTestsPanel = new JPanel();
        parseTestsPanel.setSize(BUTTON_WIDTH * 3, BUTTON_HEIGHT);
        parseTestsPanel.setLocation(FIRST_BUTTON_MARGIN_LEFT + BUTTON_WIDTH  + PADDING_MAIN_PANEL_X * 2 , MARGIN_TOP_BUTTON);

        labelInit(parseTestsPanel, TEXT_PARSE_TESTS_PANEL_LABEL);
        textFieldInit(parseTestsPanel, NUMBER_TEXT_FIELDS_COLUMNS / 3);
        textFieldIntoParseTestsPanelIndex = 1;
        outputTextReport((JTextComponent)parseTestsPanel.getComponent(textFieldIntoParseTestsPanelIndex),String.valueOf(testsCount));
        scanNextTestButtonInit(parseTestsPanel, testsCount);

        target.add(parseTestsPanel);
        setContentPane(target);
        repaint();
    }

    private void scanNextTestButtonInit(JPanel target, int testsCount) {
        JButton scanNextTestButton = new JButton(TEXT_SCAN_NEXT_TEST_BUTTON);
        scanNextTestButton.setSize(BUTTON_WIDTH / 10, BUTTON_HEIGHT / 3);
        target.add(scanNextTestButton);

        scanNextTestButton.addMouseListener(new MouseAdapter() {
            int count = 0, balance = testsCount;
            @Override
            public void mousePressed(MouseEvent e) {
                if (count >= testsCount){
                    JOptionPane.showMessageDialog(null, TEXT_END_TESTS);
                    getContentPane().remove(parseTestsPanel);
                    parseTestsPanel.invalidate();
                    repaint();
                }
                else{
                    scanNextTest(++count);
                    outputTextReport((JTextComponent)parseTestsPanel.getComponent(textFieldIntoParseTestsPanelIndex),String.valueOf(--balance));
                }
            }
        });
    }

    private void scanNextTest(int testNumber) {
        cleanTables();
        TaskTest test = Utils.readTestFromFile(task.getTaskFilePath(), testNumber, taskTestInstance);
        String report = task.findSolution(test);
        Utils.writeToFile(task.getTemp(), report);

        boolean res = outputTableReport(inputDataTable,  task.getInputData());
        if (taskTestInstance.getClass() == Utils.CLASS_TWO_PARAMETER_TEST)
            outputTextReport((JTextComponent) EPPanel.getComponent(textFieldIntoEPPanelIndex), String.valueOf(task.getExtraParameter()));
        if (res)
            if (task.getOutputDataInTableFormat())
                outputTableReport(outputDataTable, task.getOutputData());
            else
                outputTextReport(reportArea, report);;
    }

    private void cleanTables() {
        pressCleanTableButton(inputDataTable);
        if (task.getOutputDataInTableFormat())
            pressCleanTableButton(outputDataTable);
        else
            outputTextReport(reportArea, null);
    }

    private boolean outputTableReport(JTable out, Object [][] data) {
        if (data == null) {
            JOptionPane.showMessageDialog(null, TEXT_SOLUTION_NOT_FOUND);
            return false;
        }
        if (data.length == 0) {
            JOptionPane.showMessageDialog(null, task.getExtraReport());
            return false;
        }
        DefaultTableModel m = (DefaultTableModel) out.getModel();
        m.setRowCount(0);

        if ( !task.getOutputDataInTableFormat())
            m.setColumnIdentifiers(task.getColumnNames());

        for (Object[] item : data)
            m.addRow(item);

        return true;
    }
}
