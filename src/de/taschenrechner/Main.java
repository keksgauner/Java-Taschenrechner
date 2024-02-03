package de.taschenrechner;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        Taschenrechner taschenrechner = new Taschenrechner();
        JFrame frame = new JFrame(taschenrechner.name);
        frame.setContentPane(taschenrechner.rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
