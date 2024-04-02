package com.smartdash.project.mvc.scene;

import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueInformationApp;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneJeu extends Scene {

    final int longueurFenetre = 800;
    final int hauteurVueInformation = 200;

    Jeu modele;
    Stage stage;

    SceneInterface sceneInterface;

    String couleur;

    public SceneJeu(Jeu modele,Stage stage,SceneInterface sceneInterface, String couleur) throws Exception {
        super(new BorderPane());
        this.modele = modele;
        this.stage = stage;
        this.sceneInterface = sceneInterface;
        this.couleur = couleur;
        setOnKeyPressed(new ControllerClavier(modele,stage));
        setOnMouseClicked(new ControllerSouris(modele));
        setRoot(init());
    }

    public Parent init() throws Exception {
        Screen screen = Screen.getPrimary();

        BorderPane borderPane = new BorderPane();

        //Vue Jeu
        VueJeu vueJeu = new VueJeu(modele,couleur);
        modele.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();


        //Vue Info
        VueInformationApp vueInformationApp = new VueInformationApp(modele, (int) screen.getVisualBounds().getWidth(), (int) (screen.getVisualBounds().getHeight() - vueJeu.getPrefHeight()), vueJeu.getCouleurNiveau());
        modele.enregistrerObservateur(vueInformationApp);
        borderPane.setBottom(vueInformationApp);
        vueInformationApp.init();

        return borderPane;
    }

    public void setSceneInterface(Jeu modele,Stage stage) throws Exception {
        this.modele = modele;
        this.stage = stage;
        sceneInterface = new SceneInterface(this.modele,this.stage);
        stage.setScene(sceneInterface);
    }

    public Jeu getModele() {
        return modele;
    }

    public void setModele(Jeu modele) {
        this.modele = modele;
    }
}
