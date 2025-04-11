package GUI;

import DAO.DoctorDAO;
import Models.Doctor;
import Models.Specialist;

import javax.swing.*;
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

    private DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorPage() {
        // Fetch doctor information from the database
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
                        } else {
                            txtDoctorSpecialization.setText("");
                            txtDoctorExp.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Doctor not found");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error fetching doctor information: " + ex.getMessage());
                }
            }
        });

        doctorAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    String firstName = txtDoctorFname.getText();
                    String surname = txtDoctorSname.getText();
                    String address = txtDoctorAddress.getText();
                    String email = txtDoctorEmail.getText();
                    String hospital = txtDoctorHospital.getText();
                    String specialization = txtDoctorSpecialization.getText();
                    String experience = txtDoctorExp.getText();

                    Doctor doctor;
                    if (!specialization.isEmpty() && !experience.isEmpty()) {
                        doctor = new Specialist(doctorID, firstName, surname, address, email, hospital, specialization, experience);
                    } else {
                        doctor = new Doctor(doctorID, firstName, surname, address, email, hospital);
                    }

                    doctorDAO.addDoctor(doctor);
                    JOptionPane.showMessageDialog(null, "Doctor added successfully");
                    populateDoctorTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding doctor: " + ex.getMessage());
                }
            }
        });

        doctorDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int doctorID = Integer.parseInt(txtDoctorID.getText());
                    doctorDAO.deleteDoctor(doctorID);
                    JOptionPane.showMessageDialog(null, "Doctor deleted successfully");
                    populateDoctorTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting doctor: " + ex.getMessage());
                }
            }
        });

        populateDoctorTable();
    }

    private void populateDoctorTable() {
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