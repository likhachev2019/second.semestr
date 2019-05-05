package topic4.task2;

import javax.swing.*;
import java.util.Timer;
import java.awt.*;
import java.util.List;
import java.util.TimerTask;

class StatePlayer extends JFrame {

    private static final int width = 800;
    private static final int height = 500;

    private static final String textStartButton = "Start";
    private static final String textPauseButton = "Pause";
    private static final String textContinueButton = "Cont-e";

    private boolean playActive = false;
    private JButton mainButton;
    private List<SortState> states;
    private int currStateIndex = -1;

    private JTextArea textArea = new JTextArea();

    {
        setSize(width, height);
        setTitle("StatePlayer 1.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initMainPanel();
    }

    StatePlayer(List<SortState> states) {
        this.states = states;
    }

    private void initMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(getNewToolBar(), BorderLayout.NORTH);
        panel.add(getNewFramePanel(), BorderLayout.CENTER);
        add(panel);
    }

    private JToolBar getNewToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.addSeparator();
        toolBar.add(getNewButton(0));
        toolBar.addSeparator();
        mainButton = getNewButton(1);
        toolBar.add(mainButton);
        toolBar.addSeparator();
        toolBar.add(getNewButton(2));
        toolBar.addSeparator();
        return toolBar;
    }

    private JPanel getNewFramePanel() {
        JPanel panel = new JPanel();
        panel.add(textArea);
        return panel;
    }

    private JButton getNewButton(int number) {
        switch (number) {
            case 0: {
                JButton button = new JButton("Prev");
                button.addActionListener((e -> prev()));
                return button;
            }
            case 1: {
                JButton button = new JButton(textStartButton);
                button.addActionListener((e) -> {
                    swapPlayActive();play();});
                return button;
            }
            case 2: {
                JButton button = new JButton("Next");
                button.addActionListener((e -> next()));
                return button;
            }
            default: return null;
        }
    }

    private void prev() {
        if (playActive){
            mainButton.setText(textContinueButton);
            playActive = false;
        }
        if (currStateIndex >= 1){
            currStateIndex--;
            showState();
        }
    }

    private void next() {
        if (playActive){
            mainButton.setText(textContinueButton);
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
            mainButton.setText(mainButton.getText().equals(textPauseButton)?textContinueButton:textPauseButton);
        else
            mainButton.setText(mainButton.getText().equals(textStartButton)?textPauseButton:textStartButton);
    }

    private void play() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (currStateIndex + 1 < states.size() && mainButton.getText().equals(textPauseButton) && playActive){
                    play();
                    currStateIndex++;
                    showState();
                }
                else if (states.size() -1 <= currStateIndex && !mainButton.getText().equals(textStartButton))
                    swapPlayActive();
            }
        },1000);
    }

    private void showState() {
        textArea.setText(states.get(currStateIndex).getCurrentArray());
    }
}
