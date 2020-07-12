package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Dialog extends JFrame{
    private JPanel DialogPanel;
    private JSpinner spinner1;
    private JButton loadGraph;
    private JButton OKButton;
    private JLabel Lbl1;
    private JTextField TextLabelDisabled;
    private JButton infoButton;
    private JButton сказатьСпасибоButton;

    Dialog(String a) {

        JFrame dia = new JFrame(a);
        dia.setContentPane(DialogPanel);
        dia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dia.pack();
        dia.setLocationRelativeTo(null);
        dia.setResizable(false);
        dia.setVisible(true);
        dia.getRootPane().setDefaultButton(OKButton);
        loadGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    TextLabelDisabled.setText(fc.getSelectedFile().toString());

            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(TextLabelDisabled.getText());
                if(TextLabelDisabled.getText().isEmpty()){
                    int c = (Integer) spinner1.getValue();
                    if(c >= 1 && c <= 13) {
                        dia.dispose();
                        Main.createWindow(c);
                    }
                    else {
                        JDialog dialog = createDialog("Ошибка", true);
                    }
                }else{
                    dia.dispose();
                    Main.createWindow(TextLabelDisabled.getText());
                }

            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(dia, new String[] {"Наш репозиторий: https://github.com/Cawanik/practice_for_the_best_party",
                        " - Александр Никитин (Cawanik) - ответственен за визуализацию",
                        " - Анастасия Наконечная (LangerKrieg) - ответственна за функции работы с графом",
                        " - Анастасия Бердникова (Anstberd) - ответственна за документацию и тестирование"},

                        "Информация",
                        JOptionPane.INFORMATION_MESSAGE);

            }
        });
        сказатьСпасибоButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop d=Desktop.getDesktop();

                    d.browse(new URI("https://vk.me/join/AJQ1d3iZGhi7Vs5WGpiv6vjV"));
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (URISyntaxException use) {
                    use.printStackTrace();
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
