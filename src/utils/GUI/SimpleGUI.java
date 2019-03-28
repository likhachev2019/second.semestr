package utils.GUI;

import utils.Utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleGUI<Task extends utils.tasks.Task> extends JFrame {

    static final String FIRST_INPUT_TEXT = "Choose the path for the task: ",
                                TEXT_READ_FILE_BUTTON = "READ FILE",
                                TEXT_READ_DATA_IN_BUTTON = "READ 'Data-in'",
                                TEXT_OUTPUT_PANEL = "Output data: ",
                                TEXT_INPUT_PANEL = "Data-in: ",
                                ERR_TASKFILEPATH_SELECTION = "Task file not found!",
                                ERR_REPORTFILEPATH_SELECTION = "Report file not found!",
                                TEXT_CHOOSER_REPORT_FILE = "Select a temporary report path: ";

    static final int MAIN_PANEL_WIDTH = 800, MAIN_PANEL_HEIGHT = 700,
            BUTTON_HEIGHT = MAIN_PANEL_HEIGHT / 100 * 6, BUTTON_WIDTH = MAIN_PANEL_WIDTH / 6,
            PADDING_MAIN_PANEL_X = MAIN_PANEL_WIDTH / 100 * 5, PADDING_MAIN_PANEL_Y = MAIN_PANEL_HEIGHT / 100 * 2,
            WIDTH_TASK_FILE_INPUT = MAIN_PANEL_WIDTH / 100 * 90, HEIGHT_TASK_FILE_INPUT = MAIN_PANEL_HEIGHT / 100 * 10,
            WIDTH_INPUT_OUTPUT_PANELS = MAIN_PANEL_WIDTH - PADDING_MAIN_PANEL_X * 2, HEIGHT_INPUT_OUTPUT_PANELS = (MAIN_PANEL_HEIGHT - PADDING_MAIN_PANEL_Y * 5 - BUTTON_HEIGHT - HEIGHT_TASK_FILE_INPUT) / 2,
            MARGIN_TOP_BUTTON = MAIN_PANEL_HEIGHT - PADDING_MAIN_PANEL_Y * 2 - HEIGHT_INPUT_OUTPUT_PANELS - BUTTON_HEIGHT,
            MARGIN_TOP_INPUT_PANEL = PADDING_MAIN_PANEL_Y * 2 + HEIGHT_TASK_FILE_INPUT;

   JTextArea inputArea = new JTextArea(HEIGHT_INPUT_OUTPUT_PANELS / 100 * 7, WIDTH_INPUT_OUTPUT_PANELS / 100 * 9),
            reportArea = new JTextArea(inputArea.getRows(), inputArea.getColumns());
    JPanel taskFileInput, outputPanel, inputPanel;
    Task the;

    public SimpleGUI(Task the){
        super(the.getClass().getSimpleName());
        this.the = the;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelInit();
        pack();
        setLocationRelativeTo(null);
    }

    SimpleGUI() {

    }

    void panelInit(){
        JPanel myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));

        taskFileInputTableInit(myPanel);
        inputPanelInit(myPanel, inputArea);
//        secondInputInit(myPanel);
        readFileButtonInit(myPanel);
        readData_inButtonInit(myPanel);
        outputPanelInit(myPanel, reportArea);
        add(myPanel);
    }

    void taskFileInputTableInit(JPanel target){
        taskFileInput = new JPanel();
        taskFileInput.setSize(WIDTH_TASK_FILE_INPUT, HEIGHT_TASK_FILE_INPUT);
        taskFileInput.setLocation(PADDING_MAIN_PANEL_X, PADDING_MAIN_PANEL_Y);
        taskFileInput.setBackground(Color.WHITE);

        textFieldInit(taskFileInput, WIDTH_TASK_FILE_INPUT / 100 * 7);
        chooseButtonInit(taskFileInput, FIRST_INPUT_TEXT, 0);

        target.add(taskFileInput);
        JTextField tip = (JTextField) taskFileInput.getComponent(0);
        tip.setText(the.getTaskFilePath());
    }

    private void chooseButtonInit(JPanel target, String text, int number) {
        JButton but = new JButton(text);
        but.setSize(125,45);
        but.setLocation(350, 35 + number * 300);
        target.add(but);
        but.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    fileChooserInit(text, number);
                }
            }
        });
    }

    void fileChooserInit(String text, int number) {
        JFileChooser fileOpen = new JFileChooser();
        int ret = fileOpen.showDialog(null, text);
        String filePath = null;
        if (ret == JFileChooser.APPROVE_OPTION){
            filePath = fileOpen.getSelectedFile().getPath();
        }
        if (number == 0){
            JTextField tip = (JTextField) taskFileInput.getComponent(0);
            tip.setText(filePath);}
        if (number == 1){
            the.setTemp(filePath);
        }
    }

    void inputPanelInit(JPanel target, Component inside){
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setSize(WIDTH_INPUT_OUTPUT_PANELS, HEIGHT_INPUT_OUTPUT_PANELS);
        inputPanel.setLocation(PADDING_MAIN_PANEL_X, MARGIN_TOP_INPUT_PANEL);

        labelInit(inputPanel, TEXT_INPUT_PANEL);
        scrollPaneInit(inputPanel, inside);
        target.add(inputPanel);
    }

//    private void secondInputInit(JPanel target){
//        secondInput = new JPanel();
//        secondInput.setSize(700, 60);
//        secondInput.setLocation(60, 310);
//        secondInput.setBackground(Color.WHITE);
//
////        labelInit(secondInput, SECOND_INPUT_TEXT);
//        textFieldInit(secondInput);
//
//        chooseButtonInit(secondInput, SECOND_INPUT_TEXT, 1);
//        JTextField tip = (JTextField) secondInput.getComponent(0);
//        tip.setText(the.getReportFilePath());
//
//        target.add(secondInput);
//    }

    private void readFileButtonInit(JPanel target){
        Button startButton = new Button(TEXT_READ_FILE_BUTTON);
        startButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        startButton.setLocation(MAIN_PANEL_WIDTH / 2 - BUTTON_WIDTH,  MARGIN_TOP_BUTTON);
        target.add(startButton);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    pressReadFileButton();
                }
            }
        });
    }

    private void readData_inButtonInit(JPanel target){
        Button startButton = new Button(TEXT_READ_DATA_IN_BUTTON);
        startButton.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        startButton.setLocation(MAIN_PANEL_WIDTH / 2,  MARGIN_TOP_BUTTON);
        target.add(startButton);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    pressReadData_inButton();
                }
            }
        });
    }

    private void pressReadData_inButton(){
        outputTextReport(reportArea, null);
        the.setTaskFilePath(the.getTemp());

       if ( ! Utils.isFilePath(the.getTemp()) ){
            outputPanel.setBackground(Color.RED);
            taskFileInput.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, String.format("%s %s %s",
                    ERR_REPORTFILEPATH_SELECTION, "path: ", the.getTemp()));
            fileChooserInit(TEXT_CHOOSER_REPORT_FILE, 1);
        }
        else {
            Utils.writeToFile(the.getTemp(), inputArea.getText());
            outputPanel.setBackground(Color.GREEN);
            taskFileInput.setBackground(Color.GREEN);
            outputTextReport(inputArea, Utils.readFromFile(the.getTemp()));
            the.start(the.isExtraExit());
            outputTextReport(reportArea, Utils.readFromFile(the.getTemp()) );
        }
    }


    private void pressReadFileButton(){
        outputTextReport(inputArea, null);
        outputTextReport(reportArea, null);
        the.setTaskFilePath(scanText(taskFileInput, 0));

        if ( ! Utils.isFilePath(the.getTaskFilePath()) ){
            taskFileInput.setBackground(Color.RED);
            outputPanel.setBackground(null);
            JOptionPane.showMessageDialog(taskFileInput, ERR_TASKFILEPATH_SELECTION);
            fileChooserInit(FIRST_INPUT_TEXT, 0);
        }
        else if ( ! Utils.isFilePath(the.getTemp()) ){
            outputPanel.setBackground(Color.RED);
            taskFileInput.setBackground(Color.GREEN);
            outputTextReport(inputArea, Utils.readFromFile(the.getTaskFilePath()));
            JOptionPane.showMessageDialog(null, String.format("%s %s %s",
                    ERR_REPORTFILEPATH_SELECTION, "path: ", the.getTemp()));
            fileChooserInit(TEXT_CHOOSER_REPORT_FILE, 1);
        }
        else {
            outputPanel.setBackground(Color.GREEN);
            taskFileInput.setBackground(Color.GREEN);
            outputTextReport(inputArea, Utils.readFromFile(the.getTaskFilePath()));
            the.start(the.isExtraExit());
            outputTextReport(reportArea, Utils.readFromFile(the.getTemp()) );
        }
    }

    void outputPanelInit(JPanel target, Component inside){
        outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setSize(WIDTH_INPUT_OUTPUT_PANELS, HEIGHT_INPUT_OUTPUT_PANELS);
        outputPanel.setLocation(PADDING_MAIN_PANEL_X, MAIN_PANEL_HEIGHT - PADDING_MAIN_PANEL_Y - HEIGHT_INPUT_OUTPUT_PANELS);

        labelInit(outputPanel, TEXT_OUTPUT_PANEL);
        scrollPaneInit(outputPanel, inside);
        target.add(outputPanel);
    }
//      TextField должен быть добавленным первым в панель.
    String scanText(JPanel target, int indexTextField){
        JTextField text = (JTextField)target.getComponent(indexTextField);
        try{
            return text.getText();
        }
        catch (Exception e){
            text.setText(null);
            return null;
        }
    }

    void outputTextReport(JTextComponent out, String text){
        out.setText(null);
        out.setText(text);
    }

    void labelInit(JPanel target, String text){
        JLabel label = new JLabel(text);
        target.add(label, BorderLayout.WEST);
    }

    void textFieldInit(JPanel target, int columns){
        JTextField textField = new JTextField(columns);
        target.add(textField);
    }

    private void scrollPaneInit(JPanel target, Component inside){
        JScrollPane scroll = new JScrollPane(inside);
        target.add(scroll);
    }
}
