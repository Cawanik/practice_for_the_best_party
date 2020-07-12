package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Main {

    public static void main(String[] args) {


        System.out.println(UIManager.getSystemLookAndFeelClassName());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put(
                    "FileChooser.saveButtonText", "Загрузить");
            UIManager.put(
                    "FileChooser.cancelButtonText", "Отмена");
            UIManager.put(
                    "FileChooser.fileNameLabelText", "Наименование файла");
            UIManager.put(
                    "FileChooser.filesOfTypeLabelText", "Типы файлов");
            UIManager.put(
                    "FileChooser.lookInLabelText", "Директория");
            UIManager.put(
                    "FileChooser.saveInLabelText", "Сохранить в директории");
            UIManager.put(
                    "FileChooser.folderNameLabelText", "Путь директории");
        }catch(Exception el){
            el.printStackTrace();
        }

        Dialog dia = new Dialog("Введите данные");;


    }
    public static void createWindow(String a){
        MainWindow wind = new MainWindow(a);
    }
    public static void createWindow(int b){
        MainWindow wind = new MainWindow(b);
    }
}
