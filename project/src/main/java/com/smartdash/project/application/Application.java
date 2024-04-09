package com.smartdash.project.application;

import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueInformationApp;
import com.smartdash.project.mvc.vue.VueJeu;
import com.smartdash.project.terrainAleatoire.GenerateurTerrainAleatoire;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.media.Media;

import java.io.File;


public class Application extends javafx.application.Application {
    private MediaPlayer mediaPlayer;


    /**
     * Méthode qui permet de lancer l'application
     * @param stage représente la page à afficher
     * @throws Exception exception s'il y'a des erreurs
     */
    @Override
    public void start(Stage stage) throws Exception {
        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/ia_flexible.txt", 0);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        GenerateurTerrainAleatoire gta = new GenerateurTerrainAleatoire();
        Terrain t = gta.genererTerrainAleatoire();

        //Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain10.txt"), joueur.getReseau());
        Jeu jeu = new Jeu(t, joueur.getReseau());


        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        //VueCommande
        /*VueCommande vueCommande = new VueCommande(jeu, (int) borderPane.getPrefWidth(), hauteurVueCommande);
        jeu.enregistrerObservateur(vueCommande);
        borderPane.setTop(vueCommande);
        vueCommande.init();*/

        //Vue Jeu
        VueJeu vueJeu = new VueJeu(jeu,null);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();


        //Vue Info
        VueInformationApp vueInformationApp = new VueInformationApp(jeu, (int) bounds.getWidth(), (int) (bounds.getHeight() - vueJeu.getPrefHeight() - 25), vueJeu.getCouleurNiveau());
        jeu.enregistrerObservateur(vueInformationApp);
        borderPane.setBottom(vueInformationApp);
        vueInformationApp.init();


        // Ajout de la musique

//        this.setSong("src/main/resources/song.mp3");
//
//        this.playSong();
//
//        stage.setOnCloseRequest(event -> {
//            this.stopSong();
//        });

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(new ControllerClavier(jeu, stage));
        scene.setOnMouseClicked(new ControllerSouris(jeu));

        stage.setMaximized(true);
        stage.setMinWidth(bounds.getWidth());
        stage.setMinHeight(bounds.getHeight());


        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();
    }

    
    private void setSong(String song)
    {
        String fileSong = new File(song).toURI().toString();

        Media media = new Media(fileSong);

        mediaPlayer = new MediaPlayer(media);
    }

    private void playSong()
    {
        mediaPlayer.play();
    }

    private void stopSong()
    {
        mediaPlayer.stop();
    }

    public static void main(String[] args) {
        launch();
    }

}