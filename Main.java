import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BMI App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel introPanel = new JPanel();
        introPanel.setLayout(new GridBagLayout());
        introPanel.setBackground(new Color(182, 146, 253));

        GridBagConstraints gbcIntro = new GridBagConstraints();
        gbcIntro.gridwidth = GridBagConstraints.REMAINDER;
        gbcIntro.fill = GridBagConstraints.HORIZONTAL;
        gbcIntro.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to the BMI calculator", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(22f));

        JLabel nameLabel = new JLabel("Designed by Morfi", SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(14f));

        JButton startButton = new JButton("Start");
        startButton.setFont(startButton.getFont().deriveFont(15f));
        startButton.setMargin(new Insets(5, 50, 5, 50));
        startButton.setBackground(new Color(43, 70, 89));
        startButton.setForeground(Color.WHITE);

        introPanel.add(welcomeLabel, gbcIntro);
        introPanel.add(nameLabel, gbcIntro);

        gbcIntro.fill = GridBagConstraints.NONE;
        gbcIntro.insets = new Insets(30, 10, 10, 10);
        introPanel.add(startButton, gbcIntro);

        JPanel calculatorPanel = new JPanel();
        calculatorPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightField = new JTextField(15);

        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField(15);

        JButton calcButton = new JButton("Calculate BMI");
        calcButton.setBackground(new Color(49, 87, 179));
        calcButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");

        JLabel resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(resultLabel.getFont().deriveFont(12f));

        JButton adviceButton = new JButton("Advice");

        gbc.gridx = 0;
        gbc.gridy = 0;
        calculatorPanel.add(weightLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        calculatorPanel.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        calculatorPanel.add(heightLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        calculatorPanel.add(heightField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        calculatorPanel.add(calcButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        calculatorPanel.add(backButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        calculatorPanel.add(resultLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        calculatorPanel.add(adviceButton, gbc);

        mainPanel.add(introPanel, "Intro");
        mainPanel.add(calculatorPanel, "Calc");

        frame.add(mainPanel);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Calc");
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Intro");
                weightField.setText("");
                heightField.setText("");
                resultLabel.setText("");
                resultLabel.setForeground(Color.BLACK);
            }
        });

        calcButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String weightText = weightField.getText();
                    String heightText = heightField.getText();

                    if(weightText.isEmpty() || heightText.isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double weight = Double.parseDouble(weightText);
                    double heightCm = Double.parseDouble(heightText);

                    double heightM = heightCm / 100.0;
                    double bmi = weight / (heightM * heightM);

                    String status = "";
                    Color statusColor = Color.BLACK;

                    if (bmi < 18.5) {
                        status = "Underweight";
                        statusColor = Color.BLUE;
                    } else if (bmi >= 18.5 && bmi < 24.9) {
                        status = "Normal";
                        statusColor = new Color(0, 128, 0);
                    } else if (bmi >= 25 && bmi < 29.9) {
                        status = "Overweight";
                        statusColor = new Color(255, 140, 0);
                    } else {
                        status = "Obese";
                        statusColor = Color.RED;
                    }

                    String resultText = String.format("Your BMI: %.2f | Status: %s", bmi, status);
                    resultLabel.setText(resultText);
                    resultLabel.setForeground(statusColor);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String weightText = weightField.getText();
                    String heightText = heightField.getText();

                    if (weightText.isEmpty() || heightText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please calculate BMI first", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    double weight = Double.parseDouble(weightText);
                    double heightCm = Double.parseDouble(heightText);
                    double heightM = heightCm / 100.0;

                    double bmi = weight / (heightM * heightM);

                    double idealWeight = 22 * (heightM * heightM);

                    String advice = "";

                    if (bmi < 18.5) {
                        advice = "You are underweight.\n"
                                + "* Increase calorie intake\n"
                                + "* Strength training is recommended";
                    } else if (bmi < 24.9) {
                        advice = "You have a normal BMI.\n"
                                + "* Maintain balanced diet\n"
                                + "* Keep regular physical activity";
                    } else if (bmi < 29.9) {
                        advice = "You are overweight.\n"
                                + "* Reduce sugar & fast food\n"
                                + "* Cardio exercises are helpful";
                    } else {
                        advice = "You are obese.\n"
                                + "* Consult a nutrition specialist\n"
                                + "* Start low-impact activities like walking";
                    }

                    String message = String.format(
                            "Ideal Weight: %.1f kg\n\nHealth Advice:\n%s",
                            idealWeight,
                            advice
                    );

                    JOptionPane.showMessageDialog(frame, message, "BMI Advice", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input values", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}