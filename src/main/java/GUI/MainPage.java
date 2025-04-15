package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JButton doctorInformationButton;
    private JButton drugInformationButton;
    private JButton insuranceInformationButton;
    private JButton patientInformationButton;
    private JButton prescriptionInformationButton;
    private JButton visitInformationButton;
    public JPanel mainPagePanel;
    private JPanel mainPageButtonPanel;

    public MainPage() {
        doctorInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the DoctorPage frame
                JFrame doctorPageFrame = new JFrame("Doctor Page");
                doctorPageFrame.setContentPane(new DoctorPage().doctorPanel);
                doctorPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                doctorPageFrame.pack();
                doctorPageFrame.setVisible(true);
                doctorPageFrame.setSize(1200, 600); // Set the size of the frame
            }
        });
        drugInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the DrugPage frame
                JFrame drugPageFrame = new JFrame("Drug Page");
                drugPageFrame.setContentPane(new DrugPage().drugPanel);
                drugPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                drugPageFrame.pack();
                drugPageFrame.setVisible(true);
                drugPageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
        insuranceInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the InsurancePage frame
                JFrame insurancePageFrame = new JFrame("Insurance Page");
                insurancePageFrame.setContentPane(new InsurancePage().insurancePanel);
                insurancePageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                insurancePageFrame.pack();
                insurancePageFrame.setVisible(true);
                insurancePageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
        patientInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the PatientPage frame
                JFrame patientPageFrame = new JFrame("Patient Page");
                patientPageFrame.setContentPane(new PatientPage().patientPanel);
                patientPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                patientPageFrame.pack();
                patientPageFrame.setVisible(true);
                patientPageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
        prescriptionInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the PrescriptionPage frame
                JFrame prescriptionPageFrame = new JFrame("Prescription Page");
                prescriptionPageFrame.setContentPane(new PrescriptionPage().prescriptionPanel);
                prescriptionPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                prescriptionPageFrame.pack();
                prescriptionPageFrame.setVisible(true);
                prescriptionPageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
        visitInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the VisitPage frame
                JFrame visitPageFrame = new JFrame("Visit Page");
                visitPageFrame.setContentPane(new VisitPage().visitPanel);
                visitPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                visitPageFrame.pack();
                visitPageFrame.setVisible(true);
                visitPageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Page");
        frame.setSize(800, 600);
        frame.setContentPane(new MainPage().mainPagePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
