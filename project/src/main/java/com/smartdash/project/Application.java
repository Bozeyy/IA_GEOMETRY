package com.smartdash.project;

import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.controller.ControllerClavier;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueInformationApp;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    final int longueurFenetre = 800;
    final int hauteurVueInformation = 200;
    int hauteurFenetre;

    @Override
    public void start(Stage stage) throws Exception {
        Joueur joueur = Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/24-01-2024_17-57-57/generation_99.txt", 1);
        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), joueur.getReseau());
        hauteurFenetre = jeu.getTailleCase() * jeu.getTerrain().getLargeur() + hauteurVueInformation;

        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());

        borderPane.setPrefSize(longueurFenetre, hauteurFenetre);

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

        stage.setMinWidth(longueurFenetre);
        stage.setMinHeight(hauteurFenetre);

        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();

        //jeu.lancerHumainGraphique();
    }

    public static void main(String[] args) {
        launch();
    }

}