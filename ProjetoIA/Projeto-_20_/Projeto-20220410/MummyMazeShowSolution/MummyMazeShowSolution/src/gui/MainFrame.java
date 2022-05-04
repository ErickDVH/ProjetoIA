package gui;


import agent.Heuristic;
import agent.Solution;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.NoSuchElementException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import agent.Agent;
import game.MazeAgent;
import game.MazeState;
import searchalgorithms.BeamSearch;
import searchalgorithms.DepthLimitedSearch;
import searchalgorithms.SearchAlgorithm;
import showSolution.GameArea;

public class MainFrame extends JFrame {

    private JComboBox comboBoxSearchMethods;
    private JComboBox comboBoxHeuristics;
    private JLabel labelSearchParameter = new JLabel("limit/beam size:");
    private JTextField textFieldSearchParameter = new JTextField("0", 5);
    private JButton buttonInitialState = new JButton("Read Initial State");
    private JButton buttonSolve = new JButton("Solve");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonShowSolution = new JButton("Show solution");
    private JButton buttonReset = new JButton("Reset to initial state");
    private String initalState = "             \n . . . . .|. \n     -       \n . . . H . . \n     -       \n . . . .|. . \n       -   - \n . . . . .|. \n   - -       \n . . . M . . \n         -   \n . . . . . . \n S           \n";
    private MazeAgent agent = new MazeAgent(new MazeState(initalState));
    private GameArea gameArea = new GameArea();

    public MainFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void jbInit() throws Exception {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Mummy Maze Solver");

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(buttonInitialState);
        buttonInitialState.addActionListener(new ButtonInitialState_ActionAdapter(this));
        panelButtons.add(buttonSolve);
        buttonSolve.addActionListener(new ButtonSolve_ActionAdapter(this));
        panelButtons.add(buttonStop);
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new ButtonStop_ActionAdapter(this));
        panelButtons.add(buttonShowSolution);
        buttonShowSolution.setEnabled(false);
        buttonShowSolution.addActionListener(new ButtonShowSolution_ActionAdapter(this));
        panelButtons.add(buttonReset);
        buttonReset.setEnabled(false);
        buttonReset.addActionListener(new ButtonReset_ActionAdapter(this));

        JPanel panelSearchMethods = new JPanel(new FlowLayout());
        comboBoxSearchMethods = new JComboBox(agent.getSearchAlgorithmsArray());
        panelSearchMethods.add(comboBoxSearchMethods);
        comboBoxSearchMethods.addActionListener(new ComboBoxSearchMethods_ActionAdapter(this));
        panelSearchMethods.add(labelSearchParameter);
        labelSearchParameter.setEnabled(false);
        panelSearchMethods.add(textFieldSearchParameter);
        textFieldSearchParameter.setEnabled(false);
        textFieldSearchParameter.setHorizontalAlignment(JTextField.RIGHT);
        textFieldSearchParameter.addKeyListener(new TextFieldSearchParameter_KeyAdapter(this));
        comboBoxHeuristics = new JComboBox(agent.getHeuristicsArray());
        panelSearchMethods.add(comboBoxHeuristics);
        comboBoxHeuristics.setEnabled(false);
        comboBoxHeuristics.addActionListener(new ComboBoxHeuristics_ActionAdapter(this));

        JPanel gamePanel = new JPanel(new FlowLayout());
        gameArea.setState(initalState);
        gamePanel.add(gameArea);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelButtons, BorderLayout.NORTH);
        mainPanel.add(panelSearchMethods, BorderLayout.CENTER);
        mainPanel.add(gamePanel, BorderLayout.SOUTH);
        contentPane.add(mainPanel);

        pack();
    }


    public void buttonInitialState_ActionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(new java.io.File("."));
        try {
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                buttonSolve.setEnabled(true);
                buttonShowSolution.setEnabled(false);
                buttonReset.setEnabled(false);
            }
        } catch (NoSuchElementException e2) {
            JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void comboBoxSearchMethods_ActionPerformed(ActionEvent e) {
        int index = comboBoxSearchMethods.getSelectedIndex();
        agent.setSearchAlgorithms((SearchAlgorithm) comboBoxSearchMethods.getItemAt(index));
        buttonSolve.setEnabled(true);
        buttonShowSolution.setEnabled(false);
        buttonReset.setEnabled(false);
        comboBoxHeuristics.setEnabled(index > 4); //Informed serch methods
        textFieldSearchParameter.setEnabled(index == 3 || index == 7); // limited depth or beam search
        labelSearchParameter.setEnabled(index == 3 || index == 7); // limited depth or beam search
    }

    public void comboBoxHeuristics_ActionPerformed(ActionEvent e) {
        int index = comboBoxHeuristics.getSelectedIndex();
        agent.setHeuristic((Heuristic) comboBoxHeuristics.getItemAt(index));
        buttonSolve.setEnabled(true);
        buttonShowSolution.setEnabled(false);
        buttonReset.setEnabled(false);
    }

    public void buttonSolve_ActionPerformed(ActionEvent e) {

        SwingWorker worker = new SwingWorker<Solution, Void>() {
            @Override
            public Solution doInBackground() {
                buttonStop.setEnabled(true);
                buttonSolve.setEnabled(false);
                try {
                    prepareSearchAlgorithm();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                if (!agent.hasBeenStopped()) {
                    if (agent.hasSolution()) {
                        buttonShowSolution.setEnabled(true);
                    }
                }
                buttonSolve.setEnabled(true);
                buttonStop.setEnabled(false);
            }
        };

        worker.execute();
    }

    public void buttonStop_ActionPerformed(ActionEvent e) {
        agent.stop();
        buttonShowSolution.setEnabled(false);
        buttonStop.setEnabled(false);
        buttonSolve.setEnabled(true);
    }

    public void buttonShowSolution_ActionPerformed(ActionEvent e) {
        buttonShowSolution.setEnabled(false);
        buttonStop.setEnabled(false);
        buttonSolve.setEnabled(false);
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                agent.executeSolution();
                buttonReset.setEnabled(true);
                return null;
            }

            @Override
            public void done() {
                buttonShowSolution.setEnabled(true);
                buttonSolve.setEnabled(true);
            }
        };
        worker.execute();
    }

    public void buttonReset_ActionPerformed(ActionEvent e) {
        buttonShowSolution.setEnabled(true);
        buttonReset.setEnabled(false);
    }

    private void prepareSearchAlgorithm() {
        if (agent.getSearchAlgorithm() instanceof DepthLimitedSearch) {
            DepthLimitedSearch searchMethod = (DepthLimitedSearch) agent.getSearchAlgorithm();
            searchMethod.setLimit(Integer.parseInt(textFieldSearchParameter.getText()));
        } else if (agent.getSearchAlgorithm() instanceof BeamSearch) {
            BeamSearch searchMethod = (BeamSearch) agent.getSearchAlgorithm();
            searchMethod.setBeamSize(Integer.parseInt(textFieldSearchParameter.getText()));
        }
    }
}

class ComboBoxSearchMethods_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ComboBoxSearchMethods_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.comboBoxSearchMethods_ActionPerformed(e);
    }
}

class ComboBoxHeuristics_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ComboBoxHeuristics_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.comboBoxHeuristics_ActionPerformed(e);
    }
}

class ButtonInitialState_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ButtonInitialState_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.buttonInitialState_ActionPerformed(e);
    }
}

class ButtonSolve_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ButtonSolve_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.buttonSolve_ActionPerformed(e);
    }
}

class ButtonStop_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ButtonStop_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.buttonStop_ActionPerformed(e);
    }
}

class ButtonShowSolution_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ButtonShowSolution_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.buttonShowSolution_ActionPerformed(e);
    }
}

class ButtonReset_ActionAdapter implements ActionListener {

    private final MainFrame frame;

    ButtonReset_ActionAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.buttonReset_ActionPerformed(e);
    }
}

class TextFieldSearchParameter_KeyAdapter implements KeyListener {

    private final MainFrame frame;

    TextFieldSearchParameter_KeyAdapter(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
            e.consume();
        }
    }
}
