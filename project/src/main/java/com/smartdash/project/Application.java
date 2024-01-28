package com.smartdash.project;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueInformationApp;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Timer;

public class Application extends javafx.application.Application {
    AnimationTimer timer;

    @Override
    public void start(Stage stage) throws Exception {
        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/27-01-2024_11-52-44_id797/generation_999.txt", 0);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), joueur.getReseau());

        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        //hauteur de la fenetre = hauteur du terrain + hauteur de l'interface du bas
        borderPane.setPrefSize(800, jeu.getTailleCase() * jeu.getTerrain().getLargeur() + 150);

        VueJeu vueJeu = new VueJeu(jeu);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();

        VueInformationApp vueInformationApp = new VueInformationApp(jeu, (int) vueJeu.getPrefWidth(), 150);
        jeu.enregistrerObservateur(vueInformationApp);
        borderPane.setBottom(vueInformationApp);
        vueInformationApp.init();

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(new ControllerClavier(jeu));
        scene.setOnMouseClicked(new ControllerSouris(jeu));

        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();

        //jeu.lancerHumainGraphique();
    }

    public static void main(String[] args) {
        launch();
    }

}