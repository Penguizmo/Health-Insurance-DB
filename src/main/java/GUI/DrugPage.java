package GUI;

import DAO.DrugDAO;
import Models.Drug;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DrugPage extends JFrame {
    private JPanel drugPanel;
    private JLabel drugLabel;
    private JTable drugTable;
    private JScrollPane drugScrollPane;

    private JTextField txtDrugID;
    private JTextField txtDrugName;
    private JTextField txtSideEffect;
    private JTextField txtBenefit;
    private JTextField txtDrugSearchField;

    private JButton updateDrugButton;
    private JButton addDrugButton;
    private JButton deleteDrugButton;
    private JButton drugReturnButton;
    private JButton searchDrugButton;

    private DrugDAO drugDAO = new DrugDAO();

    public DrugPage() {
        updateDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int drugID = Integer.parseInt(txtDrugID.getText());
                    Drug drug = drugDAO.getDrugById(drugID);

                    if (drug != null) {
                        drug.setDrugName(txtDrugName.getText());
                        drug.setSideEffects(txtSideEffect.getText());
                        drug.setBenefits(txtBenefit.getText());
                        drugDAO.updateDrug(drug);
                        JOptionPane.showMessageDialog(null, "Drug updated successfully");
                        populateDrugTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Drug not found");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Drug ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating drug: " + ex.getMessage());
                }
            }
        });

        addDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int drugID = Integer.parseInt(txtDrugID.getText());
                    String drugName = txtDrugName.getText();
                    String sideEffects = txtSideEffect.getText();
                    String benefits = txtBenefit.getText();

                    if (drugName.isEmpty() || sideEffects.isEmpty() || benefits.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                        return;
                    }

                    Drug drug = new Drug(drugID, drugName, sideEffects, benefits);
                    drugDAO.addDrug(drug);
                    JOptionPane.showMessageDialog(null, "Drug added successfully");
                    populateDrugTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Drug ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding drug: " + ex.getMessage());
                }
            }
        });

        deleteDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int drugID = Integer.parseInt(txtDrugID.getText());
                    drugDAO.deleteDrug(drugID);
                    JOptionPane.showMessageDialog(null, "Drug deleted successfully");
                    populateDrugTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Drug ID: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting drug: " + ex.getMessage());
                }
            }
        });

        searchDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            String searchText = txtDrugSearchField.getText();
                            List<Drug> drugs = drugDAO.searchDrugs(searchText);
                            String[] columnNames = {"Drug ID", "Drug Name", "Side Effects", "Benefits"};

                            if (drugs == null || drugs.isEmpty()) {
                                drugTable.setModel(new javax.swing.table.DefaultTableModel(new Object[0][0], columnNames));
                                JOptionPane.showMessageDialog(null, "No drugs found.");
                                return null;
                            }

                            Object[][] data = new Object[drugs.size()][columnNames.length];
                            for (int i = 0; i < drugs.size(); i++) {
                                Drug drug = drugs.get(i);
                                data[i][0] = drug.getDrugId();
                                data[i][1] = drug.getDrugName();
                                data[i][2] = drug.getSideEffects();
                                data[i][3] = drug.getBenefits();
                            }

                            drugTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                            drugScrollPane.setViewportView(drugTable);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error searching drugs: " + ex.getMessage());
                        }
                        return null;
                    }
                }.execute();
            }
        });

        drugTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && drugTable.getSelectedRow() != -1) {
                    int selectedRow = drugTable.getSelectedRow();

                    txtDrugID.setText(drugTable.getValueAt(selectedRow, 0).toString());
                    txtDrugName.setText(drugTable.getValueAt(selectedRow, 1).toString());
                    txtSideEffect.setText(drugTable.getValueAt(selectedRow, 2).toString());
                    txtBenefit.setText(drugTable.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        populateDrugTable();
    }

    private void populateDrugTable() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<Drug> drugs = drugDAO.getAllDrugs();
                    String[] columnNames = {"Drug ID", "Drug Name", "Side Effects", "Benefits"};

                    Object[][] data = new Object[drugs.size()][columnNames.length];
                    for (int i = 0; i < drugs.size(); i++) {
                        Drug drug = drugs.get(i);
                        data[i][0] = drug.getDrugId();
                        data[i][1] = drug.getDrugName();
                        data[i][2] = drug.getSideEffects();
                        data[i][3] = drug.getBenefits();
                    }

                    drugTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                    drugScrollPane.setViewportView(drugTable);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error populating drug table: " + ex.getMessage());
                }
                return null;
            }
        }.execute();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DrugPage");
        frame.setContentPane(new DrugPage().drugPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 600);
    }
}