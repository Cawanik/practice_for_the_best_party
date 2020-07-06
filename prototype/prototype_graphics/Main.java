package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }catch(Exception el){
            el.printStackTrace();
        }


        Dialog dia = new Dialog("Введите данные");;


    }
    public static void createWindow(String a){

        MainWindow wind = new MainWindow("Мосты графа");
        wind.setGraph(a);
    }
    public static void createWindow(int b){
        MainWindow wind = new MainWindow("Мосты графа");
        wind.setGraph(b);
    }
}
