package com.company;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class Dialog extends JFrame{
    private JPanel DialogPanel;
    private JSpinner spinner1;
    private JButton loadGraph;
    private JButton OKButton;
    private JLabel Lbl1;
    private JTextField FuckThisShit;

    Dialog(String a) {

        JFrame dia = new JFrame(a);
        dia.setContentPane(DialogPanel);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setResizable(false);
        dia.setVisible(true);
        loadGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[][] FILTERS = {{"docx", "Файлы Word (*.docx)"}, {"pdf" , "Adobe Reader(*.pdf)"}};
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Выберите файл");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                fc.setFileFilter(filter);
                // Определяем фильтры типов файлов
                // Определение режима - только файл
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fc.showSaveDialog(fc);
                // Если файл выбран, покажем его в сообщении
                if (result == JFileChooser.APPROVE_OPTION )
                    FuckThisShit.setText(fc.getSelectedFile().toString());

            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(FuckThisShit.getText());
                if(FuckThisShit.getText().isEmpty()){
                    int c = (Integer) spinner1.getValue();
                    if(c >= 1 && c <= 300) {
                        dia.dispose();
                        Main.createWindow(c);
                    }
                    else {
                        JDialog dialog = createDialog("Ошибка", true);
                    }
                }else{
                    Main.createWindow(FuckThisShit.getText());
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
