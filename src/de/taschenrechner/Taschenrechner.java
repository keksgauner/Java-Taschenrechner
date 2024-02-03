package de.taschenrechner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Taschenrechner {
    // Start Basic
    protected String name = "Taschenrechner";
    protected JPanel rootPanel;
    JTextField output;
    JButton eins;
    JButton zwei;
    JButton drei;
    JButton vier;
    JButton fuenf;
    JButton sechs;
    JButton sieben;
    JButton acht;
    JButton neun;
    JButton nil;
    JButton plus;
    JButton minus;
    JButton mal;
    JButton geteiltdurch;
    JButton kommer;
    JButton istgleich;
    JButton clear;

    // Calculation
    StringBuilder input = new StringBuilder();
    
    public Taschenrechner(){
        initButtonListener();
        initKeyListener();

        // Textfeld größe mit Schriftgröße anpassen
        this.output.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JTextField field = (JTextField) e.getComponent();
                int size = Math.max(12, (int) (field.getWidth() * 0.1));
                field.setFont(new Font(field.getFont().getName(), field.getFont().getStyle(), size));
            }
        });
    }

    private void initKeyListener() {
        // Damit die key inputs immer gehen
        this.output.requestFocusInWindow();
        this.output.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0 -> nil.doClick();
                    case KeyEvent.VK_1 -> eins.doClick();
                    case KeyEvent.VK_2 -> zwei.doClick();
                    case KeyEvent.VK_3 -> drei.doClick();
                    case KeyEvent.VK_4 -> vier.doClick();
                    case KeyEvent.VK_5 -> fuenf.doClick();
                    case KeyEvent.VK_6 -> sechs.doClick();
                    case KeyEvent.VK_7 -> sieben.doClick();
                    case KeyEvent.VK_8 -> acht.doClick();
                    case KeyEvent.VK_9 -> neun.doClick();

                    case KeyEvent.VK_PLUS -> plus.doClick();
                    case KeyEvent.VK_MINUS -> minus.doClick();
                    case KeyEvent.VK_MULTIPLY -> mal.doClick();
                    case KeyEvent.VK_SLASH -> geteiltdurch.doClick();
                    case KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD -> kommer.doClick();
                    case KeyEvent.VK_BACK_SPACE -> clear.doClick();

                    case KeyEvent.VK_ENTER -> istgleich.doClick();
                    default -> System.out.println("Ungültiger Key Eingabe: " + e.getKeyChar());
                }
            }
        });
    }

    void initButtonListener() {
        List<JButton> buttonList = List.of(
                // Zahlen
                this.eins, this.zwei, this.drei, 
                this.vier, this.fuenf, this.sechs, 
                this.sieben, this.acht, this.neun,
                this.nil,
                // Sonderzeichen
                this.plus, this.minus, this.mal, this.geteiltdurch);

        for(JButton button : buttonList) {
            button.addActionListener(e -> {
                this.input.append(e.getActionCommand());
                // Print Input
                this.output.setText(this.input.toString());

                // Damit die key inputs immer gehen
                this.output.requestFocusInWindow();
            });
        }

        // Calcualtion
        this.istgleich.addActionListener(e -> {
            String result = String.valueOf(calculate(this.input.toString()));

            // Print Result
            this.output.setText(result);
            this.input = new StringBuilder(result);

            // Damit die key inputs immer gehen
            this.output.requestFocusInWindow();
        });

        this.clear.addActionListener(e -> {
            this.input = new StringBuilder();
            this.output.setText(this.input.toString());

            // Damit die key inputs immer gehen
            this.output.requestFocusInWindow();
        });
    }

    double calculate(String inputString) {
        String[] parts = inputString.split("[+\\-x/]");
        String[] operators = inputString.split("[\\d.]+");

        double result = Double.parseDouble(parts[0]);
        int operatorIndex = 1;

        for (int i = 1; i < parts.length; i++) {
            double nextNumber = Double.parseDouble(parts[i]);
            String operator = operators[operatorIndex++];

            switch (operator) {
                case "+" -> result += nextNumber;
                case "-" -> result -= nextNumber;
                case "x" -> result *= nextNumber;
                case "/" -> result /= nextNumber;
                default -> System.out.println("Ungültiger Operator: " + operator);
            }
        }
        return result;
    }

}
