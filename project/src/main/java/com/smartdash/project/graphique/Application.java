package com.smartdash.project.graphique;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Terrain;
import com.smartdash.project.vue.AffichageJeu;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1200, 700);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), new Reseau());
        AffichageJeu affichageJeu = new AffichageJeu(jeu);
        borderPane.setCenter(affichageJeu);
        Scene scene = new Scene(borderPane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}