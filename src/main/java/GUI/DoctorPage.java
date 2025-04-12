package GUI;

import DAO.DoctorDAO;
import Models.Doctor;
import Models.Specialist;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorPage extends JFrame {
    private JPanel doctorPanel;
    private JLabel doctorLabel;

    // Buttons
    private JButton doctorUpdateButton;
    private JButton doctorDeleteButton;
    private JButton doctorAddButton;
    private JButton doctorReturnButton;

    // Text Fields
    private JTextField txtDoctorID;
    private JTextField txtDoctorFname;
    private JTextField txtDoctorSname;
    private JTextField txtDoctorEmail;
    private JTextField txtDoctorHospital;
    private JTextField txtDoctorSpecialization;
    private JTextField txtDoctorExp;
    private JTextField txtDoctorAddress;
    private JTextField txtDoctorSearchField;

    private JTable doctorTable;
    private JScrollPane doctorScrollPane;
    private JButton searchDoctorsButton;

    private DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorPage() {
        doctorUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    Doctor doctor = doctorDAO.getDoctorById(doctorID);

                    if (doctor != null) {
                        txtDoctorID.setText(String.valueOf(doctor.getDoctorId()));
                        txtDoctorFname.setText(doctor.getFirstName());
                        txtDoctorSname.setText(doctor.getSurname());
                        txtDoctorAddress.setText(doctor.getAddress());
                        txtDoctorEmail.setText(doctor.getEmail());
                        txtDoctorHospital.setText(doctor.getHospital());

                        if (doctor instanceof Specialist) {
                            txtDoctorSpecialization.setText(((Specialist) doctor).getSpecialization());
                            txtDoctorExp.setText(((Specialist) doctor).getExperience());
                            txtDoctorSpecialization.setEditable(true);
                            txtDoctorExp.setEditable(true);
                        } else {
                            txtDoctorSpecialization.setText("");
                            txtDoctorExp.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found");
                    }

                    populateDoctorTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error fetching doctor information: " + ex.getMessage());
                }
            }
        });

        doctorAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String doctorIDText = txtDoctorID.getText();
                    if (doctorIDText.isEmpty() || !doctorIDText.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numeric Doctor ID.");
                        return;
                    }
                    int doctorID = Integer.parseInt(doctorIDText);
                    String firstName = txtDoctorFname.getText();
                    String surname = txtDoctorSname.getText();
                    String address = txtDoctorAddress.getText();
                    String email = txtDoctorEmail.getText();
                    String hospital = txtDoctorHospital.getText();
                    String specialization = txtDoctorSpecialization.getText();
                    String experience = txtDoctorExp.getText();

                    if (firstName.isEmpty() || surname.isEmpty() || address.isEmpty() || email.isEmpty() || hospital.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                        return;
                    }

                    Doctor doctor;
                    if (!specialization.isEmpty() && !experience.isEmpty()) {
                        doctor = new Specialist(doctorID, firstName, surname, address, email, hospital, specialization, experience);
                    } else {
                        doctor = new Doctor(doctorID, firstName, surname, address, email, hospital);
                    }

                    doctorDAO.addDoctor(doctor);
                    JOptionPane.showMessageDialog(null, "Doctor added successfully");
                    populateDoctorTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding doctor: " + ex.getMessage());
                }
            }
        });

        doctorDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String doctorIDText = txtDoctorID.getText();
                    if (doctorIDText.isEmpty() || !doctorIDText.matches("\\d+")) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numeric Doctor ID.");
                        return;
                    }
                    int doctorID = Integer.parseInt(doctorIDText);
                    doctorDAO.deleteDoctor(doctorID);
                    JOptionPane.showMessageDialog(null, "Doctor deleted successfully");
                    populateDoctorTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting doctor: " + ex.getMessage());
                }
            }
        });

        searchDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String searchText = txtDoctorSearchField.getText();
                            List<Doctor> doctors = doctorDAO.searchDoctors(searchText);
                            String[] columnNames = {"Doctor ID", "First Name", "Surname", "Address", "Email", "Hospital", "Specialization", "Experience"};

                            if (doctors == null || doctors.isEmpty()) {
                                doctorTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                                JOptionPane.showMessageDialog(null, "No doctors found.");
                                return null;
                            }

                            Object[][] data = new Object[doctors.size()][columnNames.length];
                            for (int i = 0; i < doctors.size(); i++) {
                                Doctor doctor = doctors.get(i);
                                data[i][0] = doctor.getDoctorId();
                                data[i][1] = doctor.getFirstName();
                                data[i][2] = doctor.getSurname();
                                data[i][3] = doctor.getAddress();
                                data[i][4] = doctor.getEmail();
                                data[i][5] = doctor.getHospital();
                                if (doctor instanceof Specialist) {
                                    data[i][6] = ((Specialist) doctor).getSpecialization();
                                    data[i][7] = ((Specialist) doctor).getExperience();
                                } else {
                                    data[i][6] = "";
                                    data[i][7] = "";
                                }
                            }

                            doctorTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                            doctorScrollPane.setViewportView(doctorTable);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error searching doctors: " + ex.getMessage());
                        }
                        return null;
                    }
                }.execute();
            }
        });

        // Add ListSelectionListener to the doctorTable
        doctorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && doctorTable.getSelectedRow() != -1) {
                    int selectedRow = doctorTable.getSelectedRow();

                    Object doctorIdValue = doctorTable.getValueAt(selectedRow, 0);
                    txtDoctorID.setText(doctorIdValue != null ? doctorIdValue.toString() : "");

                    Object firstNameValue = doctorTable.getValueAt(selectedRow, 1);
                    txtDoctorFname.setText(firstNameValue != null ? firstNameValue.toString() : "");

                    Object surnameValue = doctorTable.getValueAt(selectedRow, 2);
                    txtDoctorSname.setText(surnameValue != null ? surnameValue.toString() : "");

                    Object addressValue = doctorTable.getValueAt(selectedRow, 3);
                    txtDoctorAddress.setText(addressValue != null ? addressValue.toString() : "");

                    Object emailValue = doctorTable.getValueAt(selectedRow, 4);
                    txtDoctorEmail.setText(emailValue != null ? emailValue.toString() : "");

                    Object hospitalValue = doctorTable.getValueAt(selectedRow, 5);
                    txtDoctorHospital.setText(hospitalValue != null ? hospitalValue.toString() : "");

                    Object specializationValue = doctorTable.getValueAt(selectedRow, 6);
                    txtDoctorSpecialization.setText(specializationValue != null ? specializationValue.toString() : "");

                    Object experienceValue = doctorTable.getValueAt(selectedRow, 7);
                    txtDoctorExp.setText(experienceValue != null ? experienceValue.toString() : "");
                }
            }
        });

        populateDoctorTable();
    }

    private void populateDoctorTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Doctor> doctors = doctorDAO.getAllDoctors();
                    String[] columnNames = {"Doctor ID", "First Name", "Surname", "Address", "Email", "Hospital", "Specialization", "Experience"};

                    Object[][] data = new Object[doctors.size()][columnNames.length];
                    for (int i = 0; i < doctors.size(); i++) {
                        Doctor doctor = doctors.get(i);
                        data[i][0] = doctor.getDoctorId();
                        data[i][1] = doctor.getFirstName();
                        data[i][2] = doctor.getSurname();
                        data[i][3] = doctor.getAddress();
                        data[i][4] = doctor.getEmail();
                        data[i][5] = doctor.getHospital();
                        if (doctor instanceof Specialist) {
                            data[i][6] = ((Specialist) doctor).getSpecialization();
                            data[i][7] = ((Specialist) doctor).getExperience();
                        } else {
                            data[i][6] = "";
                            data[i][7] = "";
                        }
                    }

                    doctorTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    doctorScrollPane.setViewportView(doctorTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating doctor table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DoctorPage");
        frame.setContentPane(new DoctorPage().doctorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}