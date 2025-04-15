package GUI;

import DAO.PrescriptionDAO;
import Models.Prescription;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrescriptionPage extends JFrame {
    public JPanel prescriptionPanel;
    private JLabel prescriptionLabel;
    private JTextField txtPrescriptionID;
    private JTextField txtDatePrescribed;
    private JTextField txtDosage;
    private JTextField txtDuration;
    private JTextField txtComment;
    private JTextField txtDrugID;
    private JTextField txtDoctorID;
    private JTextField txtPatientID;
    private JTable PrescriptionTable;
    private JButton updatePrescriptionButton;
    private JButton addPrescriptionButton;
    private JButton deletePrescriptionButton;
    private JButton prescriptionReturnButton;
    private JButton searchPrescriptionsButton;
    private JTextField txtPrescriptionSearchField;
    private JScrollPane prescriptionScrollPane;

    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    public PrescriptionPage() {
        // Listener for updating a prescription
        updatePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long prescriptionID = Long.parseLong(txtPrescriptionID.getText());
                    Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionID);

                    if (prescription != null) {
                        prescription.setDatePrescribed(java.sql.Date.valueOf(txtDatePrescribed.getText()));
                        prescription.setDosage(txtDosage.getText());
                        prescription.setDuration(txtDuration.getText());
                        prescription.setComment(txtComment.getText());
                        prescription.setDrugId(Long.parseLong(txtDrugID.getText()));
                        prescription.setDoctorId(Long.parseLong(txtDoctorID.getText()));
                        prescription.setPatientID(txtPatientID.getText());

                        prescriptionDAO.updatePrescription(prescription);
                        JOptionPane.showMessageDialog(null, "Prescription updated successfully.");
                        populatePrescriptionTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Prescription not found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating prescription: " + ex.getMessage());
                }
            }
        });

        // Listener for adding a prescription
addPrescriptionButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Validate Prescription ID
            if (txtPrescriptionID.getText().isEmpty() || !txtPrescriptionID.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric Prescription ID.");
                return;
            }
            long prescriptionID = Long.parseLong(txtPrescriptionID.getText());

            // Validate Date Prescribed
            if (txtDatePrescribed.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date (yyyy-MM-dd).");
                return;
            }
            java.sql.Date datePrescribed = java.sql.Date.valueOf(txtDatePrescribed.getText());

            // Validate other fields
            String dosage = txtDosage.getText();
            String duration = txtDuration.getText();
            String comment = txtComment.getText();
            if (dosage.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Dosage and Duration cannot be empty.");
                return;
            }

            // Validate Drug ID, Doctor ID, and Patient ID
            if (txtDrugID.getText().isEmpty() || !txtDrugID.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric Drug ID.");
                return;
            }
            long drugID = Long.parseLong(txtDrugID.getText());

            if (txtDoctorID.getText().isEmpty() || !txtDoctorID.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric Doctor ID.");
                return;
            }
            long doctorID = Long.parseLong(txtDoctorID.getText());

            String patientID = txtPatientID.getText();
            if (patientID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.");
                return;
            }

            // Create Prescription object
            Prescription prescription = new Prescription(prescriptionID, datePrescribed, dosage, duration, comment, drugID, doctorID, patientID);

            // Add Prescription to the database
            prescriptionDAO.addPrescription(prescription);
            JOptionPane.showMessageDialog(null, "Prescription added successfully.");
            populatePrescriptionTable(); // Refresh the table
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error adding prescription: " + ex.getMessage());
        }
    }
});

        // Listener for deleting a prescription
        deletePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long prescriptionID = Long.parseLong(txtPrescriptionID.getText());
                    prescriptionDAO.deletePrescription(prescriptionID);
                    JOptionPane.showMessageDialog(null, "Prescription deleted successfully.");
                    populatePrescriptionTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting prescription: " + ex.getMessage());
                }
            }
        });

        // Listener for returning to the main page
        prescriptionReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Logic to return to the main page can be added here
            }
        });

        // Listener for searching prescriptions
        searchPrescriptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchText = txtPrescriptionSearchField.getText();
                    List<Prescription> prescriptions = prescriptionDAO.searchPrescriptions(searchText);
                    String[] columnNames = {"Prescription ID", "Date Prescribed", "Dosage", "Duration", "Comment", "Drug ID", "Doctor ID", "Patient ID"};

                    if (prescriptions == null || prescriptions.isEmpty()) {
                        PrescriptionTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                        JOptionPane.showMessageDialog(null, "No prescriptions found.");
                        return;
                    }

                    Object[][] data = new Object[prescriptions.size()][columnNames.length];
                    for (int i = 0; i < prescriptions.size(); i++) {
                        Prescription prescription = prescriptions.get(i);
                        data[i][0] = prescription.getPrescriptionId();
                        data[i][1] = prescription.getDatePrescribed();
                        data[i][2] = prescription.getDosage();
                        data[i][3] = prescription.getDuration();
                        data[i][4] = prescription.getComment();
                        data[i][5] = prescription.getDrugId();
                        data[i][6] = prescription.getDoctorId();
                        data[i][7] = prescription.getPatientID();
                    }

                    PrescriptionTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    prescriptionScrollPane.setViewportView(PrescriptionTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error searching prescriptions: " + ex.getMessage());
                }
            }
        });

        // Add ListSelectionListener to the PrescriptionTable
        PrescriptionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && PrescriptionTable.getSelectedRow() != -1) {
                    int selectedRow = PrescriptionTable.getSelectedRow();

                    txtPrescriptionID.setText(PrescriptionTable.getValueAt(selectedRow, 0).toString());
                    txtDatePrescribed.setText(PrescriptionTable.getValueAt(selectedRow, 1).toString());
                    txtDosage.setText(PrescriptionTable.getValueAt(selectedRow, 2).toString());
                    txtDuration.setText(PrescriptionTable.getValueAt(selectedRow, 3).toString());
                    txtComment.setText(PrescriptionTable.getValueAt(selectedRow, 4).toString());
                    txtDrugID.setText(PrescriptionTable.getValueAt(selectedRow, 5).toString());
                    txtDoctorID.setText(PrescriptionTable.getValueAt(selectedRow, 6).toString());
                    txtPatientID.setText(PrescriptionTable.getValueAt(selectedRow, 7).toString());
                }
            }
        });

        populatePrescriptionTable();
        prescriptionReturnButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the MainPage frame
                JFrame mainPageFrame = new JFrame("Main Page");
                mainPageFrame.setContentPane(new MainPage().mainPagePanel);
                mainPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
                mainPageFrame.pack();
                mainPageFrame.setVisible(true);
                mainPageFrame.setSize(1200, 600); // Set the size of the frame

            }
        });
    }

    private void populatePrescriptionTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Prescription> prescriptions = prescriptionDAO.getAllPrescriptions();
                    String[] columnNames = {"Prescription ID", "Date Prescribed", "Dosage", "Duration", "Comment", "Drug ID", "Doctor ID", "Patient ID"};

                    Object[][] data = new Object[prescriptions.size()][columnNames.length];
                    for (int i = 0; i < prescriptions.size(); i++) {
                        Prescription prescription = prescriptions.get(i);
                        data[i][0] = prescription.getPrescriptionId();
                        data[i][1] = prescription.getDatePrescribed();
                        data[i][2] = prescription.getDosage();
                        data[i][3] = prescription.getDuration();
                        data[i][4] = prescription.getComment();
                        data[i][5] = prescription.getDrugId();
                        data[i][6] = prescription.getDoctorId();
                        data[i][7] = prescription.getPatientID();
                    }

                    PrescriptionTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    prescriptionScrollPane.setViewportView(PrescriptionTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating prescription table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PrescriptionPage");
        frame.setContentPane(new PrescriptionPage().prescriptionPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}