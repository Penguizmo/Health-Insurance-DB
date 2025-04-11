package GUI;

import javax.swing.*;

public class MainPage extends JFrame {
    private JPanel WelcomeInformation;
    private JPanel ButtonOptions;
    private JButton DoctorButton;
    private JButton PatientButton;
    private JButton DrugButton;
    private JButton PrescriptionButton;
    private JButton VisitButton;
    private JButton InsuranceButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Page");
        frame.setContentPane(new MainPage().WelcomeInformation);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }
}
