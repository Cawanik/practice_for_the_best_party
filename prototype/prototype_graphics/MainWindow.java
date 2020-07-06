package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow {
    private JPanel fld;
    private JButton addEdge;
    private JButton delEdge;
    private JButton delBridges;
    private JButton findBridges;
    private JButton saveAs;
    private JLabel numOfCon;
    private JLabel numOfBridges;
    private JPanel panelGraph;

    MainWindow(String a) {
        Font b = new Font("Arial", Font.PLAIN, 12);
        JFrame dia = new JFrame(a);

        dia.setContentPane(fld);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);

    }
    public void setGraph(String a){

    }
    public void setGraph(int a){

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
