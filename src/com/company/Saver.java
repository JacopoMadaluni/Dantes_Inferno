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
            e.printStackTrace();
        }
    }

    public static boolean save(String history){
        try {
            Files.write(Paths.get("savings.txt"), history.getBytes(), StandardOpenOption.APPEND);
            return true;
        }catch(IOException e){
            return false;
        }
    }

    public static void clearSavings(){
        try {
            File file = new File("savings.txt");
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public static String load(){
        String toLoad = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("savings.txt")));
            toLoad = reader.readLine();
        }catch(IOException e){
            System.out.println(e);
        }
        return toLoad;
    }
}
