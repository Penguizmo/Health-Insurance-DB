package GUI;

import DAO.PrescriptionDAO;
import Models.Prescription;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

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
    private JButton prescriptionClearFieldsButton;

    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    public PrescriptionPage() {
        if (prescriptionDAO == null) {
            JOptionPane.showMessageDialog(null, "Error: PrescriptionDAO is not initialized.");
            return;
        }

        updatePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtPrescriptionID.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Prescription ID is required.");
                        return;
                    }

                    long prescriptionID = Long.parseLong(txtPrescriptionID.getText());
                    Prescription prescription = prescriptionDAO.getPrescriptionById(prescriptionID);

                    if (prescription == null) {
                        JOptionPane.showMessageDialog(null, "Prescription not found.");
                        return;
                    }

                    try {
                        prescription.setDatePrescribed(Date.valueOf(txtDatePrescribed.getText()));
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                        return;
                    }

                    prescription.setDosage(txtDosage.getText().trim());
                    prescription.setDuration(txtDuration.getText().trim());
                    prescription.setComment(txtComment.getText().trim());

                    try {
                        prescription.setDrugId(Long.parseLong(txtDrugID.getText()));
                        prescription.setDoctorId(Long.parseLong(txtDoctorID.getText()));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Drug ID and Doctor ID must be numeric.");
                        return;
                    }

                    prescription.setPatientID(txtPatientID.getText().trim());

                    prescriptionDAO.updatePrescription(prescription);
                    JOptionPane.showMessageDialog(null, "Prescription updated successfully.");
                    populatePrescriptionTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prescription ID must be numeric.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating prescription: " + ex.getMessage());
                }
            }
        });

        addPrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtPrescriptionID.getText().isEmpty() || txtDatePrescribed.getText().isEmpty() ||
                        txtDosage.getText().isEmpty() || txtDuration.getText().isEmpty() ||
                        txtDrugID.getText().isEmpty() || txtDoctorID.getText().isEmpty() ||
                        txtPatientID.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields are required.");
                        return;
                    }

                    long prescriptionID = Long.parseLong(txtPrescriptionID.getText());
                    Date datePrescribed = Date.valueOf(txtDatePrescribed.getText());
                    String dosage = txtDosage.getText().trim();
                    String duration = txtDuration.getText().trim();
                    String comment = txtComment.getText().trim();
                    long drugID = Long.parseLong(txtDrugID.getText());
                    long doctorID = Long.parseLong(txtDoctorID.getText());
                    String patientID = txtPatientID.getText().trim();

                    // Validate foreign key values
                    if (!prescriptionDAO.isDrugIdValid(drugID)) {
                        JOptionPane.showMessageDialog(null, "Invalid Drug ID. Please ensure the Drug ID exists.");
                        return;
                    }
                    if (!prescriptionDAO.isDoctorIdValid(doctorID)) {
                        JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please ensure the Doctor ID exists.");
                        return;
                    }
                    if (!prescriptionDAO.isPatientIdValid(patientID)) {
                        JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please ensure the Patient ID exists.");
                        return;
                    }

                    Prescription prescription = new Prescription(prescriptionID, datePrescribed, dosage, duration, comment, drugID, doctorID, patientID);
                    prescriptionDAO.addPrescription(prescription);
                    JOptionPane.showMessageDialog(null, "Prescription added successfully.");
                    populatePrescriptionTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prescription ID, Drug ID, and Doctor ID must be numeric.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding prescription: " + ex.getMessage());
                }
            }
        });

        deletePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtPrescriptionID.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Prescription ID is required.");
                        return;
                    }

                    long prescriptionID = Long.parseLong(txtPrescriptionID.getText());
                    prescriptionDAO.deletePrescription(prescriptionID);
                    JOptionPane.showMessageDialog(null, "Prescription deleted successfully.");
                    populatePrescriptionTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Prescription ID must be numeric.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting prescription: " + ex.getMessage());
                }
            }
        });

        searchPrescriptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchText = txtPrescriptionSearchField.getText().trim();
                    if (searchText.isEmpty()) {
                        // If the search field is empty, display all prescriptions
                        populatePrescriptionTable();
                        return;
                    }

                    // Perform search if searchText is not empty
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

        PrescriptionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (PrescriptionTable.getSelectedRow() != -1) {
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



        prescriptionClearFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPrescriptionID.setText("");
                txtDatePrescribed.setText("");
                txtDosage.setText("");
                txtDuration.setText("");
                txtComment.setText("");
                txtDrugID.setText("");
                txtDoctorID.setText("");
                txtPatientID.setText("");
            }
        });


        prescriptionReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current PrescriptionPage
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(prescriptionPanel);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }

                // Open the MainPage
                JFrame mainPageFrame = new JFrame("Main Page");
                mainPageFrame.setContentPane(new MainPage().mainPagePanel);
                mainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainPageFrame.pack();
                mainPageFrame.setVisible(true);
                mainPageFrame.setSize(800, 600);
            }
        });

        populatePrescriptionTable();
        PrescriptionTable.addComponentListener(new ComponentAdapter() {
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