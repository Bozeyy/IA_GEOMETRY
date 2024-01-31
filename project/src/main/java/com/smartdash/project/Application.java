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
import com.smartdash.project.mvc.vue.VueReseau;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    final int longueurFenetre = 800;
    final int hauteurVueInformation = 200;
    int hauteurFenetre;

    @Override
    public void start(Stage stage) throws Exception {
        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_apprentissage_8-5.txt", 0);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain9.txt"), joueur.getReseau());
        //Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain4.txt"), new Reseau());

        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());
        borderPane.setPrefSize(800, jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        VueJeu vueJeu = new VueJeu(jeu);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();



        VueInformationApp vueInformationApp = new VueInformationApp(jeu, (int) vueJeu.getPrefWidth(), hauteurVueInformation);
        jeu.enregistrerObservateur(vueInformationApp);
        borderPane.setBottom(vueInformationApp);
        vueInformationApp.init();


        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(new ControllerClavier(jeu));
        scene.setOnMouseClicked(new ControllerSouris(jeu));

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        //stage.setMinWidth(longueurFenetre);
        //stage.setMinHeight(hauteurFenetre);

        stage.setMinWidth(bounds.getWidth());
        stage.setMinHeight(bounds.getHeight());


        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}