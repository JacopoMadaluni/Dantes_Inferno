package com.company.Gui;

import com.company.Game;
import com.company.Saver;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void setGame(Game game){
        this.game = game;
    }

    public void setMain(MainFrame main){
        this.main = main;
    }


    public void saveGame(){
        game.parseCommand("save");
    }

    public void quit() throws IOException{
        main.loadInitialScreen();
    }

    public void dinamicPrint(String text){
        char[] characters = text.toCharArray();
        for (char c : characters){
            appendChar(c);
        }
        terminal.setText(terminal.getText() + "\n");
        terminal.selectPositionCaret(terminal.getLength());
        terminal.deselect();
    }

    public void appendChar(char c){
        terminal.setText(terminal.getText() + c);
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
