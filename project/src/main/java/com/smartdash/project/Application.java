package com.smartdash.project;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    AnimationTimer timer;

    @Override
    public void start(Stage stage) throws IOException {
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), new Reseau());

        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());
        borderPane.setPrefSize(800, jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        VueJeu vueJeu = new VueJeu(jeu);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(new ControllerClavier(jeu));
        scene.setOnMouseClicked(new ControllerSouris(jeu));

        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();

        jeu.lancerJeu();
    }

    public static void main(String[] args) {
        launch();
    }

}