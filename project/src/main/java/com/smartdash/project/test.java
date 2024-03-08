package com.smartdash.project;

import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.scene.SceneInterface;
import com.smartdash.project.mvc.scene.SceneJeu;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class test extends javafx.application.Application {
    final int longueurFenetre = 800;
    final int hauteurVueInformation = 200;

    final int hauteurVueCommande = 15;
    int hauteurFenetre;

    @Override
    public void start(Stage stage) throws Exception {
        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_apprentissage_8-5.txt", 0);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain9.txt"), joueur.getReseau());
        jeu.genererTerrains();

        SceneInterface sceneInterface = new SceneInterface(jeu, stage);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        //stage.setMinWidth(longueurFenetre);
        //stage.setMinHeight(hauteurFenetre);

        stage.setMaximized(true);
        stage.setMinWidth(bounds.getWidth());
        stage.setMinHeight(bounds.getHeight());


        stage.setTitle("SmartDash");
        stage.setScene(sceneInterface);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}