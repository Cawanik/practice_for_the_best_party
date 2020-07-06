package com.company;

import java.io.File;

public class SaveFile {
    private String path = "";
    SaveFile(String a){
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
