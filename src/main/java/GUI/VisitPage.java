package GUI;

import DAO.VisitDAO;
import Models.Visit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VisitPage extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(VisitPage.class.getName());

    public JPanel visitPanel;
    private JLabel visitLabel;
    private JTextField txtPatientID;
    private JTextField txtDoctorID;
    private JTextField txtDateOfVisit;
    private JTextField txtSymptoms;
    private JTextField txtDiagnosisID;
    private JTable visitTable;
    private JButton updateVisitRecordsButton;
    private JButton addVisitRecordsButton;
    private JButton deleteVisitRecordsButton;
    private JButton visitReturnButton;
    private JButton searchVisitRecordsButton;
    private JTextField txtVisitSearchField;
    private JScrollPane visitScrollPane;
    private JButton visitClearFieldsButton;

    private VisitDAO visitDAO = new VisitDAO();

    public VisitPage() {
        // Listener for updating visit records
        updateVisitRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    LocalDate dateOfVisit = LocalDate.parse(txtDateOfVisit.getText());

                    Visit visit = visitDAO.getVisit(patientID, doctorID, dateOfVisit);

                    if (visit != null) {
                        visit.setSymptoms(txtSymptoms.getText());
                        visit.setDiagnosisid(txtDiagnosisID.getText());

                        visitDAO.updateVisit(visit);
                        JOptionPane.showMessageDialog(null, "Visit record updated successfully.");
                        populateVisitTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Visit record not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please enter a valid number.");
                    LOGGER.log(Level.SEVERE, "Invalid Doctor ID input", ex);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Date format. Please use YYYY-MM-DD.");
                    LOGGER.log(Level.SEVERE, "Invalid Date input", ex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating visit record: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "Error updating visit record", ex);
                }
            }
        });

        // Listener for adding visit records
        addVisitRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    LocalDate dateOfVisit = LocalDate.parse(txtDateOfVisit.getText());
                    String symptoms = txtSymptoms.getText();
                    String diagnosisID = txtDiagnosisID.getText();

                    if (patientID.isEmpty() || txtDoctorID.getText().isEmpty() || txtDateOfVisit.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Patient ID, Doctor ID, and Date of Visit are required.");
                        return;
                    }

                    // Validate PatientID
                    if (!visitDAO.isPatientIDValid(patientID)) {
                        JOptionPane.showMessageDialog(null, "Invalid Patient ID. Please ensure the patient exists.");
                        return;
                    }

                    // Validate DoctorID
                    if (!visitDAO.isDoctorIDValid(doctorID)) {
                        JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please ensure the doctor exists.");
                        return;
                    }

                    Visit visit = new Visit(patientID, doctorID, dateOfVisit, symptoms, diagnosisID);
                    visitDAO.addVisit(visit);
                    JOptionPane.showMessageDialog(null, "Visit record added successfully.");
                    populateVisitTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please enter a valid number.");
                    LOGGER.log(Level.SEVERE, "Invalid Doctor ID input", ex);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Date format. Please use YYYY-MM-DD.");
                    LOGGER.log(Level.SEVERE, "Invalid Date input", ex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding visit record: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "Error adding visit record", ex);
                }
            }
        });

        // Listener for deleting visit records
        deleteVisitRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String patientID = txtPatientID.getText();
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    LocalDate dateOfVisit = LocalDate.parse(txtDateOfVisit.getText());

                    visitDAO.deleteVisit(patientID, doctorID, dateOfVisit);
                    JOptionPane.showMessageDialog(null, "Visit record deleted successfully.");
                    populateVisitTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID. Please enter a valid number.");
                    LOGGER.log(Level.SEVERE, "Invalid Doctor ID input", ex);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Date format. Please use YYYY-MM-DD.");
                    LOGGER.log(Level.SEVERE, "Invalid Date input", ex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting visit record: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "Error deleting visit record", ex);
                }
            }
        });

        // Listener for searching visit records
        searchVisitRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchText = txtVisitSearchField.getText();
                    List<Visit> visits = visitDAO.searchVisits(searchText);
                    String[] columnNames = {"Patient ID", "Doctor ID", "Date of Visit", "Symptoms", "Diagnosis ID"};

                    if (visits == null || visits.isEmpty()) {
                        visitTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                        JOptionPane.showMessageDialog(null, "No visit records found.");
                        return;
                    }

                    Object[][] data = new Object[visits.size()][columnNames.length];
                    for (int i = 0; i < visits.size(); i++) {
                        Visit visit = visits.get(i);
                        data[i][0] = visit.getPatientID();
                        data[i][1] = visit.getDoctorid();
                        data[i][2] = visit.getDateofvisit();
                        data[i][3] = visit.getSymptoms();
                        data[i][4] = visit.getDiagnosisid();
                    }

                    visitTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    visitScrollPane.setViewportView(visitTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error searching visit records: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "Error searching visit records", ex);
                }
            }
        });

        // Add ListSelectionListener to the visitTable
        visitTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && visitTable.getSelectedRow() != -1) {
                    int selectedRow = visitTable.getSelectedRow();

                    txtPatientID.setText(visitTable.getValueAt(selectedRow, 0).toString());
                    txtDoctorID.setText(visitTable.getValueAt(selectedRow, 1).toString());
                    txtDateOfVisit.setText(visitTable.getValueAt(selectedRow, 2).toString());
                    txtSymptoms.setText(visitTable.getValueAt(selectedRow, 3).toString());
                    txtDiagnosisID.setText(visitTable.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        populateVisitTable();
        visitClearFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all text fields
                txtPatientID.setText("");
                txtDoctorID.setText("");
                txtDateOfVisit.setText("");
                txtSymptoms.setText("");
                txtDiagnosisID.setText("");
            }
        });
        visitReturnButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // close the current frame
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(visitPanel);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }

                // Open the MainPage
                JFrame mainFrame = new JFrame("MainPage");
                mainFrame.setContentPane(new MainPage().mainPagePanel);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.pack();
                mainFrame.setVisible(true);
                mainFrame.setSize(800, 600);

            }
        });
    }

    private void populateVisitTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Visit> visits = visitDAO.getAllVisits();
                    String[] columnNames = {"Patient ID", "Doctor ID", "Date of Visit", "Symptoms", "Diagnosis ID"};

                    Object[][] data = new Object[visits.size()][columnNames.length];
                    for (int i = 0; i < visits.size(); i++) {
                        Visit visit = visits.get(i);
                        data[i][0] = visit.getPatientID();
                        data[i][1] = visit.getDoctorid();
                        data[i][2] = visit.getDateofvisit();
                        data[i][3] = visit.getSymptoms();
                        data[i][4] = visit.getDiagnosisid();
                    }

                    visitTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    visitScrollPane.setViewportView(visitTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating visit table: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "Error populating visit table", ex);
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VisitPage");
        frame.setContentPane(new VisitPage().visitPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}