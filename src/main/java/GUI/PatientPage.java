package GUI;

import DAO.PatientDAO;
import Models.Patient;
import Models.InsuredPatient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientPage extends JFrame {
    private JPanel patientPanel;
    private JLabel patientLabel;
    private JTable patientTable;
    private JScrollPane patientScrollPane;

    private JTextField txtPatientID;
    private JTextField txtPatientFirstName;
    private JTextField txtPatientSurname;
    private JTextField txtPatientPostCode;
    private JTextField txtPatientAddress;
    private JTextField txtPatientPhoneNo;
    private JTextField txtPatientEmail;
    private JTextField txtPatientInsuranceID;
    private JTextField txtPatientSearchField;

    private JButton updatePatientButton;
    private JButton addPatientButton;
    private JButton deletePatientButton;
    private JButton patientReturnButton;
    private JButton searchPatientsButton;

    private PatientDAO patientDAO = new PatientDAO();

    public PatientPage() {
        setupListeners();
        populatePatientTable();
    }

    private void setupListeners() {
        deletePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        searchPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });

        patientTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && patientTable.getSelectedRow() != -1) {
                    populatePatientDetails();
                }
            }
        });
    }

    private void deletePatient() {
        try {
            String patientID = txtPatientID.getText();
            if (patientID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a patient to delete.");
                return;
            }
            patientDAO.deletePatient(patientID);
            JOptionPane.showMessageDialog(null, "Patient deleted successfully.");
            populatePatientTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error deleting patient: " + ex.getMessage());
        }
    }

    private void searchPatients() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    String searchText = txtPatientSearchField.getText();
                    List<Patient> patients = patientDAO.searchPatients(searchText);
                    updatePatientTable(patients);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error searching patients: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    private void populatePatientDetails() {
        int selectedRow = patientTable.getSelectedRow();
        txtPatientID.setText(patientTable.getValueAt(selectedRow, 0).toString());
        txtPatientFirstName.setText(patientTable.getValueAt(selectedRow, 1).toString());
        txtPatientSurname.setText(patientTable.getValueAt(selectedRow, 2).toString());
        txtPatientPostCode.setText(patientTable.getValueAt(selectedRow, 3).toString());
        txtPatientAddress.setText(patientTable.getValueAt(selectedRow, 4).toString());
        txtPatientPhoneNo.setText(patientTable.getValueAt(selectedRow, 5).toString());
        txtPatientEmail.setText(patientTable.getValueAt(selectedRow, 6).toString());
        txtPatientInsuranceID.setText(patientTable.getValueAt(selectedRow, 7) != null ? patientTable.getValueAt(selectedRow, 7).toString() : "");
    }

    private void populatePatientTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Patient> patients = patientDAO.getAllPatients();
                    updatePatientTable(patients);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating patient table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    private void updatePatientTable(List<Patient> patients) {
        String[] columnNames = {"Patient ID", "First Name", "Surname", "Post Code", "Address", "Phone", "Email", "Insurance ID"};
        Object[][] data = new Object[patients.size()][columnNames.length];

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            data[i][0] = patient.getPatientId();
            data[i][1] = patient.getFirstName();
            data[i][2] = patient.getSurname();
            data[i][3] = patient.getPostcode();
            data[i][4] = patient.getAddress();
            data[i][5] = patient.getPhone();
            data[i][6] = patient.getEmail();
            data[i][7] = (patient instanceof InsuredPatient) ? ((InsuredPatient) patient).getInsuranceId() : "";
        }

        patientTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        patientScrollPane.setViewportView(patientTable);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PatientPage");
        frame.setContentPane(new PatientPage().patientPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}