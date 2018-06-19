package com.company.Gui;

import com.company.Game;
import com.company.Saver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class MainFrame extends Application {

    private Parent root;
    private static Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        this.stage = primaryStage;
        stage.setOnCloseRequest(e -> quit());
        primaryStage.setTitle("Dante's Inferno");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        root = loader.load();

        initializeStyle();
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root, 600,400));
        primaryStage.show();

    }

    private void initializeStyle(){
        //File f = new File("src\\com\\company\\Gui\\styles.css");
        root.getStylesheets().clear();
        root.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

    }

    public void newGame() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_window.fxml"));
        root =  loader.load();
        initializeStyle();
        Controller controller = loader.getController();
        controller.setMain(this);
        Game game = new Game();
        game.setController(controller);
        Saver.clearSavings();
        controller.setGame(game);
        stage.setScene(new Scene(root));
        root.requestFocus();

        game.printStart();
    }

    public void loadGame() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_window.fxml"));
        root =  loader.load();
        initializeStyle();
        Controller controller = loader.getController();
        controller.setMain(this);
        Game game = new Game();
        game.setController(controller);
        game.load(Saver.load());
        controller.setGame(game);

        stage.setScene(new Scene(root));
    }

    public void quit(){
        Platform.exit();
        System.exit(0);
    }

    public void loadInitialScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        root =  loader.load();
        stage.setScene(new Scene(root));
    }


}
