package GUI;

import DAO.InsuranceDAO;
import Models.Insurance;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InsurancePage extends JFrame {
    private JPanel insurancePanel;
    private JLabel insuranceLabel;
    private JTable insuranceTable;
    private JScrollPane insuranceScrollPane;

    private JTextField txtInsuranceID;
    private JTextField txtCompanyName;
    private JTextField txtCompanyAddress;
    private JTextField txtCompanyPhoneNo;
    private JTextField txtInsuranceSearchField;

    private JButton updateInsuranceButton;
    private JButton addInsuranceDetailsButton;
    private JButton deleteInsuranceDetailsButton;
    private JButton insuranceReturnButton;
    private JButton searchInsuranceButton;

    private InsuranceDAO insuranceDAO = new InsuranceDAO();

    public InsurancePage() {
        deleteInsuranceDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String insuranceID = txtInsuranceID.getText();
                    insuranceDAO.deleteInsurance(insuranceID);
                    JOptionPane.showMessageDialog(null, "Insurance deleted successfully");
                    populateInsuranceTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting insurance: " + ex.getMessage());
                }
            }
        });

        searchInsuranceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String searchText = txtInsuranceSearchField.getText();
                            List<Insurance> insurances = insuranceDAO.searchInsurances(searchText);
                            String[] columnNames = {"Insurance ID", "Company", "Address", "Phone"};

                            if (insurances == null || insurances.isEmpty()) {
                                insuranceTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                                JOptionPane.showMessageDialog(null, "No insurance records found.");
                                return null;
                            }

                            Object[][] data = new Object[insurances.size()][columnNames.length];
                            for (int i = 0; i < insurances.size(); i++) {
                                Insurance insurance = insurances.get(i);
                                data[i][0] = insurance.getInsuranceId();
                                data[i][1] = insurance.getCompany();
                                data[i][2] = insurance.getAddress();
                                data[i][3] = insurance.getPhone();
                            }

                            insuranceTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                            insuranceScrollPane.setViewportView(insuranceTable);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error searching insurance records: " + ex.getMessage());
                        }
                        return null;
                    }
                }.execute();
            }
        });

        insuranceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && insuranceTable.getSelectedRow() != -1) {
                    int selectedRow = insuranceTable.getSelectedRow();

                    txtInsuranceID.setText(insuranceTable.getValueAt(selectedRow, 0).toString());
                    txtCompanyName.setText(insuranceTable.getValueAt(selectedRow, 1).toString());
                    txtCompanyAddress.setText(insuranceTable.getValueAt(selectedRow, 2).toString());
                    txtCompanyPhoneNo.setText(insuranceTable.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        populateInsuranceTable();
        updateInsuranceButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String insuranceID = txtInsuranceID.getText();
                    String companyName = txtCompanyName.getText();
                    String companyAddress = txtCompanyAddress.getText();
                    String companyPhoneNo = txtCompanyPhoneNo.getText();

                    Insurance insurance = new Insurance(insuranceID, companyName, companyAddress, companyPhoneNo);
                    insuranceDAO.updateInsurance(insurance);
                    JOptionPane.showMessageDialog(null, "Insurance updated successfully");
                    populateInsuranceTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating insurance: " + ex.getMessage());
                }

            }
        });
        addInsuranceDetailsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String insuranceID = txtInsuranceID.getText();
                    String companyName = txtCompanyName.getText();
                    String companyAddress = txtCompanyAddress.getText();
                    String companyPhoneNo = txtCompanyPhoneNo.getText();

                    Insurance insurance = new Insurance(insuranceID, companyName, companyAddress, companyPhoneNo);
                    insuranceDAO.addInsurance(insurance);
                    JOptionPane.showMessageDialog(null, "Insurance added successfully");
                    populateInsuranceTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding insurance: " + ex.getMessage());
                }

            }
        });
    }

    private void populateInsuranceTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Insurance> insurances = insuranceDAO.getAllInsurances();
                    String[] columnNames = {"Insurance ID", "Company", "Address", "Phone"};

                    Object[][] data = new Object[insurances.size()][columnNames.length];
                    for (int i = 0; i < insurances.size(); i++) {
                        Insurance insurance = insurances.get(i);
                        data[i][0] = insurance.getInsuranceId();
                        data[i][1] = insurance.getCompany();
                        data[i][2] = insurance.getAddress();
                        data[i][3] = insurance.getPhone();
                    }

                    insuranceTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    insuranceScrollPane.setViewportView(insuranceTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating insurance table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("InsurancePage");
        frame.setContentPane(new InsurancePage().insurancePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}