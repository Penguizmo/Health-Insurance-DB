package GUI;

import javax.swing.*;

public class MainPage extends JFrame {
    /**
     * The MainPage class represents the main GUI of the application.
     * It contains the main frame and initializes the components.
     */

    public static void main(String[] args) {
        // Create an instance of MainPage to display the GUI
        SwingUtilities.invokeLater(() -> {
            new MainPage();
        });
    }

    private JFrame frame;
    private JPanel MainPageJPanel;
    private JPanel welcomeMesagePanel;
    private JPanel DAOpageSelection;
    private JButton doctorButton;
    private JButton drugButton;
    private JButton patientButton;
    private JButton insuranceButton;
    private JButton visitButton;
    private JButton prescriptionButton;
    private JPanel mainPageButtons;
    private JTabbedPane tabbedPane1;

    public MainPage() {
        // Create the main frame
        frame = new JFrame("The Home of Health Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setContentPane(MainPageJPanel);
        frame.setVisible(true);

        // Initilise the tabbed pane
        tabbedPane1 = new JTabbedPane();
    }
}