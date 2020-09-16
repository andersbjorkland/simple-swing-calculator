import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends JFrame {
    private double sum;
    private List<Double> memory;
    private double inputValue1;
    private double inputValue2;
    JTextField sumDisplay;
    private final int INPUT1 = 1;
    private final int INPUT2 = 2;
    private int latestInputTo = INPUT2;
    private boolean firstComboBoxUpdate = true;

    // GUI Components
    private final Container contentPane;
    JTextArea input1;
    JTextArea input2;
    JLabel operationLabel;
    JButton additionButton;
    JButton subtractionButton;
    JButton multiplicationButton;
    JButton divisionButton;
    JButton equalsButton;
    JComboBox<Double> memoryBox;

    public Calculator() throws HeadlessException {
        super("Simple Swing Calculator");

        // Initialize our fields
        sum = 0.0;
        memory = new ArrayList<>();
        contentPane = getContentPane();
        inputValue1 = 0;
        inputValue2 = 0;

        // Create components
        input1 = new JTextArea();
        input2 = new JTextArea();
        operationLabel = new JLabel("+");
        sumDisplay = new JTextField(String.valueOf(sum));
        additionButton = new JButton("+");
        subtractionButton = new JButton("-");
        multiplicationButton = new JButton("×");
        divisionButton = new JButton("÷");
        equalsButton = new JButton("=");
        memoryBox = new JComboBox<>();


        // Add actions to components
        additionButton.addActionListener(event -> {
            operationLabel.setText("+");
        });
        subtractionButton.addActionListener(event -> {
            operationLabel.setText("-");
        });
        multiplicationButton.addActionListener(event -> {
            operationLabel.setText("×");
        });
        divisionButton.addActionListener(event -> {
            operationLabel.setText("÷");
        });
        equalsButton.addActionListener(event -> {
            if (parseInputs()) {
                sum = calculateResult(operationLabel.getText(), inputValue1, inputValue2);
                sumDisplay.setText(String.valueOf(sum));
                memoryBox.addItem(sum);
                //memoryBox.setSelectedIndex(memoryBox.getItemCount()-1);
            }
        });

        memoryBox.addActionListener(event -> {
            if (!firstComboBoxUpdate && memoryBox.getItemCount() > 0) {
                double selectedValue = (Double) memoryBox.getSelectedItem();
                JTextArea targetArea;
                if (input1.getText().length() == 0) {
                    targetArea = input1;
                } else if (input2.getText().length() == 0) {
                    targetArea = input2;
                } else {
                    if (latestInputTo == INPUT1) {
                        targetArea = input2;
                        latestInputTo = INPUT2;
                    } else {
                        targetArea = input1;
                        latestInputTo = INPUT1;
                    }
                }
                targetArea.setText(String.valueOf(selectedValue));
            }
            firstComboBoxUpdate = false;
        });

        // Adjust components presentation
        input1.setColumns(5);
        input2.setColumns(5);
        sumDisplay.setColumns(5);
        memoryBox.setPrototypeDisplayValue(0.00001);
        memoryBox.setEditable(false);

        // Add components to contentPane
        contentPane.add(input1);
        contentPane.add(operationLabel);
        contentPane.add(input2);
        contentPane.add(sumDisplay);
        contentPane.add(additionButton);
        contentPane.add(subtractionButton);
        contentPane.add(multiplicationButton);
        contentPane.add(divisionButton);
        contentPane.add(equalsButton);
        contentPane.add(memoryBox);

        // Set some basic window behavior
        contentPane.setLayout(new FlowLayout());
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected boolean parseInputs() {
        boolean hasInput = (input1.getText().length() > 0 && input2.getText().length() > 0);
        boolean hasCorrectInput = false;

        if (hasInput) {
            try {
                inputValue1 = Double.parseDouble(input1.getText());
                inputValue2 = Double.parseDouble(input2.getText());
                hasCorrectInput = true;
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return hasCorrectInput;
    }

    protected void resetInputs() {
        input1.setText("");
        input2.setText("");
    }

    protected double calculateResult(String operation, double value1, double value2) {
        double sum = 0;
        switch (operation) {
            case "-" -> sum = value1 - value2;
            case "×" -> sum = value1 * value2;
            case "÷" -> sum = value1 / value2;
            default -> sum = value1 + value2;
        }
        return sum;
    }

}
