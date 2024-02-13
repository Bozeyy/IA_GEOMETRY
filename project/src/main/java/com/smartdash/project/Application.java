package com.smartdash.project;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueCommande;
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
    private final int longueurFenetre = 800;
    private final int hauteurVueInformation = 190;
    private final int hauteurVueCommande = 15;
    private int hauteurFenetre;

    final int hauteurVueCommande = 15;
    int hauteurFenetre;

//    src/main/resources/enregistrement/30-01-2024_16-36-43_id446/generation_0.txt
//    src/main/resources/enregistrement/meilleurs/generation_2056.txt
    /**
     * Méthode qui permet de lancer l'application
     * @param stage représente la page à afficher
     * @throws Exception exception s'il y'a des erreurs
     */
    @Override
    public void start(Stage stage) throws Exception {


//        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 0);
//        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain13.txt"), joueur.getReseau());

//        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 0);
//        Jeu jeu = new Jeu(new Terrain("src/main/resources/Terrains/terrain_test.txt"), joueur.getReseau());

        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/12-02-2024_14-36-39_id747/generation_714.txt", 0);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/test_apprentissage/terrain_1.txt"), joueur.getReseau());

        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());
        borderPane.setPrefSize(longueurFenetre, jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        //VueCommande
        /*VueCommande vueCommande = new VueCommande(jeu, (int) borderPane.getPrefWidth(), hauteurVueCommande);
        jeu.enregistrerObservateur(vueCommande);
        borderPane.setTop(vueCommande);
        vueCommande.init();*/

        //Vue Jeu
        VueJeu vueJeu = new VueJeu(jeu);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();


        //Vue Info
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