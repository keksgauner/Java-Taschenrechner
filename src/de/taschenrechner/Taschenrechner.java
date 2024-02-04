package de.taschenrechner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;

class Taschenrechner {
    protected String name = "Taschenrechner";
    protected JPanel rootPanel;
    // Das hier wird genutzt damit aus dem Textfeld die Eingabe nicht geholt werden muss
    StringBuilder input = new StringBuilder();
    JTextField textFieldOutput;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JButton button7;
    JButton button8;
    JButton button9;
    JButton button0;
    JButton buttonPlus;
    JButton buttonMinus;
    JButton buttonMultiply;
    JButton buttonDivide;
    JButton buttonDot;
    JButton buttonEqual;
    JButton buttonClear;
    
    public Taschenrechner(){
        buttonListener();
        keyListener();
        textGrow();
    }

    /**
     * Diese funktion sorgt dafür das die Schriftgröße der Buttons und des Textfeldes sich anpasst
     */
    void textGrow() {
        List<JButton> list = List.of(
                // Numbers
                this.button1, this.button2, this.button3,
                this.button4, this.button5, this.button6,
                this.button7, this.button8, this.button9,
                this.button0,
                // Special Characters
                this.buttonPlus, this.buttonMinus, this.buttonMultiply, this.buttonDivide,
                // Other
                this.buttonEqual, this.buttonClear
        );

        for(JButton button : list) {
            button.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    JButton field = (JButton) e.getComponent();
                    int size = Math.max(12, (int) (field.getWidth() * 0.1));
                    field.setFont(new Font(field.getFont().getName(), field.getFont().getStyle(), size));
                }
            });
        }

        // Textfeld größe mit Schriftgröße anpassen
        this.textFieldOutput.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JTextField field = (JTextField) e.getComponent();
                int size = Math.max(12, (int) (field.getWidth() * 0.1));
                field.setFont(new Font(field.getFont().getName(), field.getFont().getStyle(), size));
            }
        });
    }

    /**
     * Diese Funktion sorgt dafür das die Tastatureingaben nuztbar sind
     */
    void keyListener() {
        // Focus auf das Textfeld setzen
        this.textFieldOutput.requestFocusInWindow();
        this.textFieldOutput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0 -> button0.doClick();
                    case KeyEvent.VK_1 -> button1.doClick();
                    case KeyEvent.VK_2 -> button2.doClick();
                    case KeyEvent.VK_3 -> button3.doClick();
                    case KeyEvent.VK_4 -> button4.doClick();
                    case KeyEvent.VK_5 -> button5.doClick();
                    case KeyEvent.VK_6 -> button6.doClick();
                    case KeyEvent.VK_7 -> button7.doClick();
                    case KeyEvent.VK_8 -> button8.doClick();
                    case KeyEvent.VK_9 -> button9.doClick();

                    case KeyEvent.VK_PLUS -> buttonPlus.doClick();
                    case KeyEvent.VK_MINUS -> buttonMinus.doClick();
                    case KeyEvent.VK_MULTIPLY -> buttonMultiply.doClick();
                    case KeyEvent.VK_SLASH -> buttonDivide.doClick();
                    case KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD -> buttonDot.doClick();
                    case KeyEvent.VK_BACK_SPACE -> buttonClear.doClick();

                    case KeyEvent.VK_ENTER -> buttonEqual.doClick();
                    default -> System.out.println("Ungültiger Key Eingabe: " + e.getKeyChar());
                }
            }
        });
    }

    /**
     * Diese Funktion sorgt dafür das die Buttons funktionieren
     */
    void buttonListener() {
        List<JButton> buttonList = List.of(
                // Numbers
                this.button1, this.button2, this.button3,
                this.button4, this.button5, this.button6,
                this.button7, this.button8, this.button9,
                this.button0,
                // Special Characters
                this.buttonPlus, this.buttonMinus, this.buttonMultiply, this.buttonDivide,
                this.buttonDot);

        for(JButton button : buttonList) {
            button.addActionListener(e -> {
                this.input.append(e.getActionCommand());
                // Print Input
                this.setOutput(this.input.toString());
            });
        }

        // Calculate
        this.buttonEqual.addActionListener(e -> {
            double result = calculate(this.input.toString());
            DecimalFormat formatPattern = new DecimalFormat("#.#####");
            String formattedResult = formatPattern.format(result);

            // Print Result
            this.setOutput(formattedResult);
            this.input = new StringBuilder(formattedResult);
        });

        this.buttonClear.addActionListener(e -> {
            this.input = new StringBuilder();
            this.setOutput(this.input.toString());
        });
    }

    /**
     * Diese Funktion sorgt dafür das die Rechnung durchgeführt wird
     * @param input Die Eingabe die gerechnet werden soll z.B. "1+1"
     * @return result in double
     */
    double calculate(String input) {
        String[] parts = input.split("[+\\-x/]");
        String[] operators = input.split("[\\d.]+");

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

    void setOutput(String output) {
        this.textFieldOutput.setText(output);
        this.textFieldOutput.requestFocusInWindow();
    }
}
