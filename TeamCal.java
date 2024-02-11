import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TeamCal implements ActionListener {
    JPanel panel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JButton button;
    JTextField textField;
    double wage = 0;
    boolean isNewNumber = true;
    String operator = "";

    public TeamCal(String msg) {
        JFrame jf = new JFrame(msg);
        jf.setTitle("calculation team2");
        jf.setSize(600, 600);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label1 = new JLabel("< 알바비 계산기 사용법 >");
        label2 = new JLabel("1.숫자패드 부분을 이용해 주 근무시간을 입력한다.");
        label3 = new JLabel("2.돈 모양 아이콘을 눌러 최저시급을 입력한다.");
        label4 = new JLabel("3.세금,보험 버튼을 눌러서 해당사항을 적용한다.");
        label5 = new JLabel("4.'='버튼을 눌러서 결과를 확인한다.");
        button = new JButton("확인");
        button.addActionListener(this);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(button);

        jf.add(panel);
        jf.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("확인")) {
            JFrame frame = new JFrame("Paycheck Calculator: team2");
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;

            textField = new JTextField();
            textField.setFont(new Font("Arial", Font.PLAIN, 18));
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.gridheight = 1;
            constraints.insets = new Insets(0, 10, 10, 10);
            panel.add(textField, constraints);

            addButton(panel, "+", 0, 1);
            addButton(panel, "-", 1, 1);
            addButton(panel, "x", 2, 1);
            addButton(panel, "%", 3, 1);

            addButton(panel, "1", 0, 2);
            addButton(panel, "2", 1, 2);
            addButton(panel, "3", 2, 2);
            addButton(panel, "4", 0, 3);

            addButton(panel, "5", 1, 3);
            addButton(panel, "6", 2, 3);
            addButton(panel, "7", 0, 4);
            addButton(panel, "8", 1, 4);

            addButton(panel, "9", 2, 4);
            addButton(panel, "0", 0, 5);
            addButton(panel, "=", 2, 5, 2, 1);
            addButtonAndImage(panel, "src/image/money.PNG", "min wages", 3, 2);

            addInsuranceButton(panel, "insurance 9.32%", 3, 4);
            addButton(panel, "tax 3.3%", 3, 3);
            addButton(panel, "C", 1, 5);

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } else {
            handleButtonCommand(command);
        }
    }

    private void handleButtonCommand(String command) {
        if (command.equals("+") || command.equals("-") || command.equals("x") || command.equals("%")) {
            operator = command;
            isNewNumber = true;
            wage = Double.parseDouble(textField.getText());
            textField.setText("");
        } else if (command.equals("=")) {
            double secondNumber = Double.parseDouble(textField.getText());
            double result = 0;

            if (operator.equals("+")) {
                result = wage + secondNumber;
            } else if (operator.equals("-")) {
                result = wage - secondNumber;
            } else if (operator.equals("x")) {
                result = wage * secondNumber;
            } else if (operator.equals("%")) {
                result = wage / secondNumber;
            }

            if (wage >= 15) {
                result = result + (wage * 4.0) / 20.0 * 9620;
                result = result * 4;
                String message = "근로기준법 제55조에 따라 주휴수당을 지급받으실 수 있습니다.\n주휴수당을 더해서 계산해드리겠습니다.";
                JOptionPane.showMessageDialog(null, message, "주휴수당", JOptionPane.INFORMATION_MESSAGE);
            }

            textField.setText(String.valueOf(result));
        } else if (command.equals("tax 3.3%")) {
            double number = Double.parseDouble(textField.getText());
            double result = number * (1 - 0.033);
            textField.setText(String.valueOf(result));
        } else if (command.equals("insurance 9.32%")) {
            double number = Double.parseDouble(textField.getText());
            double result = number * (1 - 0.0932);
            textField.setText(String.valueOf(result));
        } else if (command.equals("C")) {
            textField.setText("");
        } else if (command.equals("min wages")) {
            textField.setText("9620");
        } else {
            if (isNewNumber) {
                textField.setText(command);
                isNewNumber = false;
            } else {
                textField.setText(textField.getText() + command);
            }
        }
    }

    private void addButton(JPanel panel, String label, int gridX, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(this);
        panel.add(button, constraints);
    }

    private void addButton(JPanel panel, String label, int gridX, int gridY, int gridWidth, int gridHeight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(this);
        panel.add(button, constraints);
    }

    private void addButtonAndImage(JPanel panel, String imagePath, String label, int gridX, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 40));

        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(button.getPreferredSize().width, 
        		button.getPreferredSize().height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        button.setIcon(resizedIcon);

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setText(label);
        button.addActionListener(this);

        panel.add(button, constraints);
    }

    private void addInsuranceButton(JPanel panel, String label, int gridX, int gridY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(this);

        panel.add(button, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	TeamCal t = new TeamCal("calculation team2");
            }
        });
    }
}