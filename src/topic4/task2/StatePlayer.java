package topic4.task2;

import utils.Utils;

import javax.swing.*;
import java.util.Timer;
import java.awt.*;
import java.util.List;
import java.util.TimerTask;

class StatePlayer extends JFrame {

    private static final int PREV_BUTTON = 0;
    private static final int STATE_BUTTON = 1;
    private static final int NEXT_BUTTON = 2;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private static final String TEXT_START_BUTTON = "Start";
    private static final String TEXT_PAUSE_BUTTON = "Pause";
    private static final String TEXT_CONTINUE_BUTTON = "Cont-e";
    private static final String TEXT_interval_LABEL = "Playback interval: ";
    private static final String TEXT_DIM_LABEL = " (ms) ";
    private static final String TEXT_ABOUT_VALUE_LABEL = "'ll be about value";
    private static final String TEXT_STATE_LABEL = "'ll be about sort state";
    private static final String TEXT_INPUT_LABEL = "Input: ";
    private static final String TEXT_ENTER_BUTTON = "ENTER";
    private static final String TEXT_SORT_NAME_LABEL = "Sort Inserts";

    private boolean playActive = false;
    private JButton stateButton;
    private JPanel inputPanel;
    private List<SortState> states;
    private int currStateIndex = -1;
    private static int defaultMsPlaybackInterval = 3000; /*in ms*/

    private JTextField playbackIntervalTextField = new JTextField(12);
    private JTextField arrayTextField = new JTextField(50);
    private JTextArea beforeTextArea = new JTextArea();
    private JTextArea inProcessTextArea = new JTextArea();
    private JTextArea resultTextArea = new JTextArea();
    private JLabel beforeLabel = new JLabel("Before sorting: ");
    private JLabel inProcessLabel = new JLabel("in Process: ");
    private JLabel aboutValueLabel = new JLabel(TEXT_ABOUT_VALUE_LABEL);
    private JLabel stateLabel = new JLabel(TEXT_STATE_LABEL);
    private JLabel afterLabel = new JLabel("After sorting: ");

    {
        setSize(WIDTH, HEIGHT);
        setTitle("StatePlayer 1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefault();
        initMainPanel();
    }

    private void setDefault() {
        currStateIndex = -1;
        playActive = false;
    }

    StatePlayer() { }

    StatePlayer(List<SortState> states) {
        this.states = states;
    }

    private void initMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getNewToolBar(), BorderLayout.NORTH);
        panel.add(getNewFramePanel(), BorderLayout.CENTER);
        panel.add(initInputPanel(), BorderLayout.SOUTH);
        add(panel);
    }

    private JPanel initInputPanel() {
        JButton button = new JButton(TEXT_ENTER_BUTTON);
        button.addActionListener(e -> arrayEnter());
        inputPanel = getPanel(new JLabel(TEXT_INPUT_LABEL), arrayTextField,button);
        return inputPanel;
    }

    private void arrayEnter() {
        String input = arrayTextField.getText();
        if (Utils.isArray(input)){
            states = Main.insertSort(Utils.stringToArray(input));
            inputPanel.setBackground(Color.green);
            setDefault();
        }
        else {
            inputPanel.setBackground(Color.red);
        }
    }

    private JToolBar getNewToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.addSeparator();
        toolBar.add(getNewButton(PREV_BUTTON));
        toolBar.addSeparator();
        stateButton = getNewButton(STATE_BUTTON);
        toolBar.add(stateButton);
        toolBar.addSeparator();
        toolBar.add(getNewButton(NEXT_BUTTON));
        toolBar.addSeparator(new Dimension(200,20));
        toolBar.add(new JLabel(TEXT_interval_LABEL));
        toolBar.addSeparator();
        toolBar.add(initIntervalTextField());
        toolBar.addSeparator();
        toolBar.add(new JLabel(TEXT_DIM_LABEL));
        toolBar.addSeparator();
        return toolBar;
    }

    private JTextField initIntervalTextField() {
        playbackIntervalTextField.setText(Integer.toString(defaultMsPlaybackInterval));
        playbackIntervalTextField.addActionListener(e -> playbackIntervalEnter());
        return playbackIntervalTextField;
    }

    private void playbackIntervalEnter() {
        String interval = playbackIntervalTextField.getText();
        if (interval.matches("^\\s*[+-]?\\d+\\s*$")) {
            playbackIntervalTextField.setBackground(Color.green);
            defaultMsPlaybackInterval = Integer.valueOf(interval);
        }
        else {
            playbackIntervalTextField.setBackground(Color.red);
        }
    }

    private JPanel getNewFramePanel() {
        JPanel wrapper = new JPanel();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.orange));
        GridBagConstraints c = new GridBagConstraints();
        panel.add(getSortNameLabel());

        c.gridy = 1;
        c.insets = new Insets(35, 0, 0, 0);
        panel.add(getPanel(beforeLabel, beforeTextArea), c);

        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(55, 0, 0, 0);
        panel.add(getPanel(aboutValueLabel), c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 3;
        c.insets = new Insets(15, 0, 0, 0);
        panel.add(getPanel(inProcessLabel, inProcessTextArea, new JLabel("      "), stateLabel), c);

        c.gridy = 4;
        c.insets = new Insets(65, 0, 0, 0);
        panel.add(getPanel(afterLabel, resultTextArea), c);

        wrapper.add(panel);
        return wrapper;
    }

    private JLabel getSortNameLabel() {
        JLabel label = new JLabel(TEXT_SORT_NAME_LABEL);
        label.setOpaque(true);
        label.setBackground(Color.orange);
        label.setForeground(Color.gray);
        return label;
    }

    private JPanel getPanel(Component... components) {
        JPanel panel = new JPanel();
        for (Component c : components) {
            panel.add(c);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.red));
        return panel;
    }

    private JButton getNewButton(int number) {
        switch (number) {
            case 0: {
                JButton button = new JButton("Prev");
                button.addActionListener(e -> {
                    if (states!= null)
                        prev();
                });
                return button;
            }
            case 1: {
                JButton button = new JButton(TEXT_START_BUTTON);
                button.addActionListener(e -> {
                    if (states!= null)
                        stateButton();
                });
                return button;

            }
            case 2: {
                JButton button = new JButton("Next");
                button.addActionListener(e -> {
                    if (states!= null)
                        next();
                });
                return button;
            }
            default: return null;
        }
    }

    private void stateButton(){
        if (states.size() == 1)
            return;
        swapPlayActive();
        play();
    }

    private void prev() {
        if (states.size() == 1)
            return;
        if (playActive){
            stateButton.setText(TEXT_CONTINUE_BUTTON);
            playActive = false;
        }
        if (currStateIndex >= 1){
            currStateIndex--;
            showState();
        }
    }

    private void next() {
        if (states.size() == 1)
            return;
        if (playActive){
            stateButton.setText(TEXT_CONTINUE_BUTTON);
            playActive = false;
        }
        if (currStateIndex + 1 < states.size()){
            currStateIndex++;
            showState();
        }
    }

    private void swapPlayActive() {
        playActive = !playActive;
        if (currStateIndex > -1 && currStateIndex < states.size() -1 )
            stateButton.setText(stateButton.getText().equals(TEXT_PAUSE_BUTTON)? TEXT_CONTINUE_BUTTON : TEXT_PAUSE_BUTTON);
        else
            stateButton.setText(stateButton.getText().equals(TEXT_START_BUTTON)? TEXT_PAUSE_BUTTON : TEXT_START_BUTTON);
    }

    private void play() {
        if (currStateIndex < 0)
            setOriginalArray();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Если плеер в состоянии авто-воспроизведения и надо показать ещё хотя бы одно состояния
                if (currStateIndex + 1 < states.size() && stateButton.getText().equals(TEXT_PAUSE_BUTTON) && playActive){
                    play();
                    currStateIndex++;
                    showState();
                }
                // если показали все сцены и на кнопке не текст для старта, выключаем авто-воспроизведение
                else if (states.size() -1 <= currStateIndex && !stateButton.getText().equals(TEXT_START_BUTTON))

                    swapPlayActive();
            }
        }, defaultMsPlaybackInterval);
    }

    private void setOriginalArray() {
        // изначально это поле -1
        currStateIndex++;
        beforeTextArea.setText(states.get(currStateIndex).toStringCurrArray());
    }

    private void showState() {
        if (beforeTextArea.getText().equals(""))
            setOriginalArray();
        // если хотим показать состояние исходного массива
        if (currStateIndex == 0){
            SortState state = states.get(currStateIndex);
            inProcessTextArea.setText(state.toStringCurrArray());
            aboutValueLabel.setText(TEXT_ABOUT_VALUE_LABEL);
            stateLabel.setText(TEXT_STATE_LABEL);
        }
        else{
            SortState state = states.get(currStateIndex);
            inProcessTextArea.setText(state.toStringCurrArray());
            aboutValueLabel.setText(String.format("Set: %d (index: %d)", state.getValue(), state.getIndexValue()));
            if (state.getJIndex() > -1)
                stateLabel.setText(String.format("%d <—> %d", states.get(currStateIndex).get(state.getJIndex()), state.getValue()));
//            Если слева стоящий элемент отсутсвует или меньше взятого, то взятый уже на своём месте
            else
                stateLabel.setText(String.format("%d in place", state.getValue()));

        }
        // Если это последняя сцена, выводим результат
        if (currStateIndex + 1 == states.size())
            resultTextArea.setText(states.get(currStateIndex).toStringCurrArray());
    }
}
