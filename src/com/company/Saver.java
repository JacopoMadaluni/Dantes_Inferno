package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Saver {

    private File savings;
    public Saver(){
        try{
            savings = new File("savings");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void save(String history){
        try {
            Files.write(Paths.get("savings.txt"), history.getBytes(), StandardOpenOption.APPEND);
            System.out.println("---- Game Saved ----");
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void clearSavings(){
        try {
            File file = new File("savings.txt");
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        }catch(IOException e){
            System.out.println(e);
        }

    }
}
