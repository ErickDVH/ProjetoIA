package gui;

import showSolution.SolutionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public Main(){
        MainFrame frame = new MainFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if(frameSize.height > screenSize.height){
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width){
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) /2,(screenSize.height - frameSize.height)/2);


        frame.setVisible(true);

    }



    public static void main(String[] args) {
        new Main();
    }
}
