package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.*;

public class Dialog extends JFrame{
    private JPanel DialogPanel;
    private JSpinner spinner1;
    private JButton loadGraph;
    private JButton OKButton;
    private JLabel Lbl1;
    private JTextField cUsersUsernameDesktopTextField;

    Dialog(String a) {

        JFrame dia = new JFrame(a);
        dia.setContentPane(DialogPanel);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setVisible(true);
        loadGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOpen a = new FileOpen(cUsersUsernameDesktopTextField.getText());
                if(a.getPath() != "") {
                    dia.dispose();
                    Main.createWindow(a.getPath());
                }
                else {
                    JDialog dialog = createDialog("Ошибка", true);
                }

            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int c = (Integer) spinner1.getValue();
                if(c >= 1 && c <= 10) {
                    dia.dispose();
                    Main.createWindow(c);
                }
                else {
                    JDialog dialog = createDialog("Ошибка", true);
                }
            }
        });
    }
    private JDialog createDialog(String title, boolean modal)
    {
        JDialog dialog = new JDialog(this, title, modal);
        JLabel lblErr = new JLabel("Неправильно введены данные!");
        JButton okButt = new JButton("OK");
        okButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();

            }
        });
        JPanel contents = new JPanel();
        contents.add(lblErr);
        contents.add(okButt);
        dialog.setContentPane(contents);
        dialog.setSize(300, 150);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        return dialog;
    }
}
