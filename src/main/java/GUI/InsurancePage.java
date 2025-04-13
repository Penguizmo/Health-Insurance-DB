package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsurancePage extends Component {
    private JPanel insurancePanel;
    private JTable table1;
    private JTextField txtInsuranceSearchField;
    private JTextField toCreateEditOrTextField;
    private JTextField txtInsuranceID;
    private JTextField txtCompany;
    private JTextField txtCompanyAddress;
    private JTextField txtCompanyPhoneNo;
    private JButton updateInsuranceDetailsButton;
    private JButton addInsuranceDetailsButton;
    private JButton deleteInsuranceDetailsButton;
    private JButton insuranceReturnButton;
    private JButton searchInsuranceButton;
    private JScrollPane insuranceScrollPain;

    public InsurancePage() {
        txtInsuranceID.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtCompany.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtCompanyAddress.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtCompanyPhoneNo.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        txtInsuranceSearchField.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
