package com.company;

import java.io.File;

public class FileOpen {
    private String path = "";
    FileOpen(String a){
        if (new File(a).exists()) {
            System.out.println(a);
            path = a;
        }

    }
    public void setPath(String a){
        if (new File(a).exists()) {
            System.out.println(a);
        }
    }
    public String getPath(){
        return path;
    }
}
