package com.company.Gui;

import com.company.Game;
import com.company.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Controller {

    @FXML TextArea terminal;
    @FXML TextField inputLine;
    private MainFrame main;
    private Game game;
    private Printer printer;
    private boolean printing;


    public Controller(){
        printer = new Printer(this);
    }

    public int getTextSpeed(){
        return game.getTextSpeed();
    }
    public void setGame(Game game){
        this.game = game;
    }

    public void setGameTextSpeed(int i){
        game.setTextSpeed(i);
    }

    public boolean isPrinting(){
        return printer.isPrinting();
    }


    public void setMain(MainFrame main){
        this.main = main;
    }

    public TextArea getTerminal(){
        return terminal;
    }

    public void saveGame(){
        game.parseCommand("save");
    }

    public void quit() throws IOException{
        main.loadInitialScreen();
    }

    public void test(String text){
        printer.dynamicPrint(text);
    }
    public void dynamicPrint(String text){
        Task task = new Task<Void>() {
            @Override public Void call() {
                printing = true;
                char[] characters = text.toCharArray();
                final int max = characters.length;
                int i = 0;
                for (char c : characters) {
                    Platform.runLater(() -> terminal.appendText("" +c));
                    updateProgress(i, max);
                    i++;
                    try{
                        Thread.sleep(game.getTextSpeed());
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                printing = false;
                return null;
            }
        };

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }



    public void appendText(String text){
        terminal.setText(terminal.getText() + "\n" + text);
        terminal.selectPositionCaret(terminal.getLength());
        terminal.deselect();
    }


    @FXML
    public void onEnter(javafx.event.ActionEvent e)
    {
        String command = inputLine.getText();
        game.parseCommand(command);
        inputLine.setText("");
    }

}
