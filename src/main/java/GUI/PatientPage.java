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
        // Listener for updating a patient
        updatePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    Patient patient = patientDAO.getPatientById(patientID);

                    if (patient != null) {
                        patient.setFirstName(txtPatientFirstName.getText());
                        patient.setSurname(txtPatientSurname.getText());
                        patient.setPostcode(txtPatientPostCode.getText());
                        patient.setAddress(txtPatientAddress.getText());
                        patient.setPhone(txtPatientPhoneNo.getText());
                        patient.setEmail(txtPatientEmail.getText());

                        if (patient instanceof InsuredPatient) {
                            ((InsuredPatient) patient).setInsuranceId(txtPatientInsuranceID.getText());
                        }

                        patientDAO.updatePatient(patient);
                        JOptionPane.showMessageDialog(null, "Patient updated successfully.");
                        populatePatientTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Patient not found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating patient: " + ex.getMessage());
                }
            }
        });

        // Listener for adding a patient
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    String firstName = txtPatientFirstName.getText();
                    String surname = txtPatientSurname.getText();
                    String postcode = txtPatientPostCode.getText();
                    String address = txtPatientAddress.getText();
                    String phone = txtPatientPhoneNo.getText();
                    String email = txtPatientEmail.getText();
                    String insuranceID = txtPatientInsuranceID.getText();

                    if (firstName.isEmpty() || surname.isEmpty() || postcode.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                        return;
                    }

                    Patient patient;
                    if (!insuranceID.isEmpty()) {
                        patient = new InsuredPatient(patientID, firstName, surname, postcode, address, phone, email, insuranceID);
                    } else {
                        patient = new Patient(patientID, firstName, surname, postcode, address, phone, email);
                    }

                    patientDAO.addPatient(patient);
                    JOptionPane.showMessageDialog(null, "Patient added successfully.");
                    populatePatientTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding patient: " + ex.getMessage());
                }
            }
        });

        // Listener for deleting a patient
        deletePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    if (patientID.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Patient ID.");
                        return;
                    }

                    patientDAO.deletePatient(patientID);
                    JOptionPane.showMessageDialog(null, "Patient deleted successfully.");
                    populatePatientTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting patient: " + ex.getMessage());
                }
            }
        });

        // Listener for returning to the main page
        patientReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Logic to return to the main page can be added here
            }
        });

        // Listener for searching patients
        searchPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchText = txtPatientSearchField.getText();
                    List<Patient> patients = patientDAO.searchPatients(searchText);
                    String[] columnNames = {"Patient ID", "First Name", "Surname", "Postcode", "Address", "Phone", "Email", "Insurance ID"};

                    if (patients == null || patients.isEmpty()) {
                        patientTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                        JOptionPane.showMessageDialog(null, "No patients found.");
                        return;
                    }

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
                        if (patient instanceof InsuredPatient) {
                            data[i][7] = ((InsuredPatient) patient).getInsuranceId();
                        } else {
                            data[i][7] = "";
                        }
                    }

                    patientTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    patientScrollPane.setViewportView(patientTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error searching patients: " + ex.getMessage());
                }
            }
        });

        // Add ListSelectionListener to the patientTable
        patientTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && patientTable.getSelectedRow() != -1) {
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
            }
        });

        populatePatientTable();
    }

    private void populatePatientTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Patient> patients = patientDAO.getAllPatients();
                    String[] columnNames = {"Patient ID", "First Name", "Surname", "Postcode", "Address", "Phone", "Email", "Insurance ID"};

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
                        if (patient instanceof InsuredPatient) {
                            data[i][7] = ((InsuredPatient) patient).getInsuranceId();
                        } else {
                            data[i][7] = "";
                        }
                    }

                    patientTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    patientScrollPane.setViewportView(patientTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating patient table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
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