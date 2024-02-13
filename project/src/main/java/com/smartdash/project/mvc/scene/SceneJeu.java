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
import javafx.stage.Stage;

public class SceneJeu extends Scene {

    final int longueurFenetre = 800;
    final int hauteurVueInformation = 200;

    Jeu modele;
    Stage stage;

    public SceneJeu(Jeu modele,Stage stage) throws Exception {
        super(new BorderPane());
        this.modele = modele;
        this.stage = stage;
        setRoot(init());
        setOnKeyPressed(new ControllerClavier(modele,stage));
        setOnMouseClicked(new ControllerSouris(modele));
    }

    public Parent init() throws Exception {


        BorderPane borderPane = new BorderPane();
        //borderPane.setPrefSize(jeu.getTerrain().getLongueur() * jeu.getTailleCase(), jeu.getTailleCase() * jeu.getTerrain().getLargeur());
        borderPane.setPrefSize(longueurFenetre, modele.getTailleCase() * modele.getTerrain().getLargeur());

        //VueCommande
        /*VueCommande vueCommande = new VueCommande(jeu, (int) borderPane.getPrefWidth(), hauteurVueCommande);
        jeu.enregistrerObservateur(vueCommande);
        borderPane.setTop(vueCommande);
        vueCommande.init();*/

        //Vue Jeu
        VueJeu vueJeu = new VueJeu(modele);
        modele.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);
        vueJeu.init();


        //Vue Info
        VueInformationApp vueInformationApp = new VueInformationApp(modele, (int) vueJeu.getPrefWidth(), hauteurVueInformation, vueJeu.getCouleurNiveau());
        modele.enregistrerObservateur(vueInformationApp);
        borderPane.setBottom(vueInformationApp);
        vueInformationApp.init();

        return borderPane;
    }

}
