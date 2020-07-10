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
