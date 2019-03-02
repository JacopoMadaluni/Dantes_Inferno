package com.company.Gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.PriorityQueue;

public class Printer {
    private Controller controller;
    private boolean printing;
    private static Printer instance;


    public Printer (Controller controller){
        this.controller = controller;
        printing = false;
        instance = this;
    }

    public static Printer getPrinter(){
        return instance;
    }

    public boolean isPrinting(){
        return printing;
    }

    public void dynamicPrint(String text){
        final int oldSpeed = controller.getTextSpeed();
        Task task = new Task<Void>() {
            @Override public Void call() {
                printing = true;
                char[] characters = text.toCharArray();
                final int max = characters.length;
                int i = 0;
                for (char c : characters) {
                    Platform.runLater(() -> controller.getTerminal().appendText("" +c));
                    updateProgress(i, max);
                    i++;
                    try{
                        Thread.sleep(controller.getTextSpeed());
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                controller.setGameTextSpeed(oldSpeed);
                printing = false;
                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private PriorityQueue<Character> makeQueue(String text){
        PriorityQueue<Character> queue = new PriorityQueue<>();
        char[] characters = text.toCharArray();
        for (char c : characters){
            queue.add(c);
        }
        return queue;
    }
}
