package com.sri;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Transactions extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    Transactions(String pin) {
        this.pin = pin;

        // Set frame properties
        setTitle("ATM Transactions");
        setSize(960, 700);
        setLocation(300, 50);
        setLayout(null);
        setUndecorated(true);

        // Load ATM image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 700);
        add(background);

        // Label
        l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 18));
        l1.setBounds(220, 230, 300, 20);
        background.add(l1);

        // Buttons
        b1 = new JButton("DEPOSIT");
        b2 = new JButton("CASH WITHDRAWAL");
        b3 = new JButton("FAST CASH");
        b4 = new JButton("MINI STATEMENT");
        b5 = new JButton("PIN CHANGE");
        b6 = new JButton("BALANCE ENQUIRY");
        b7 = new JButton("EXIT");

        // Set bounds
        b1.setBounds(170, 280, 150, 35);
        b2.setBounds(390, 280, 150, 35);
        b3.setBounds(170, 320, 150, 35);
        b4.setBounds(390, 320, 150, 35);
        b5.setBounds(170, 360, 150, 35);
        b6.setBounds(390, 360, 150, 35);
        b7.setBounds(390, 390, 150, 35);

        // Add to background
        background.add(b1);
        background.add(b2);
        background.add(b3);
        background.add(b4);
        background.add(b5);
        background.add(b6);
        background.add(b7);

        // Add action listeners
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            setVisible(false);
            new Deposit(pin).setVisible(true);
        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Withdrawl(pin).setVisible(true);
        } else if (ae.getSource() == b3) {
            setVisible(false);
            new FastCash(pin).setVisible(true);
        } else if (ae.getSource() == b4) {
            new MiniStatement(pin).setVisible(true);
        } else if (ae.getSource() == b5) {
            setVisible(false);
            new Pin(pin).setVisible(true);
        } else if (ae.getSource() == b6) {
            setVisible(false);
            new BalanceEnquiry(pin).setVisible(true);
        } else if (ae.getSource() == b7) {
            System.exit(0);
        }
    }

}
