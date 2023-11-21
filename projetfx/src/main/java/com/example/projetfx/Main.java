package com.example.projetfx;

import com.example.projetfx.modele.Jeu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException{
        Jeu jeu = new Jeu();
        Scene scene = new Scene(jeu, 1000, 700);
        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();
        jeu.lancer();
    }

    public static void main(String[] args) {
        launch();
    }
}