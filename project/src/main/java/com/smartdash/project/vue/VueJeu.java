package com.smartdash.project.vue;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Sujet;
import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Pique;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class VueJeu extends Pane implements Observateur {

    private Jeu donnees;

    public VueJeu(Jeu donnees) {
        this.donnees = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(donnees.getTerrain().getLongueur() * this.donnees.getTailleCase(), 600);

        //Taille maximale de la fenêtre de jeu
        setMaxSize(donnees.getTerrain().getLongueur() * this.donnees.getTailleCase(), 600);

        //setTranslateX(this.getPrefWidth());

        System.out.println(this.getPrefWidth());

        //Ajout du background en Image
        setBackground(new Background(new BackgroundImage(new Image("background2.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

    }

    public void init(){
        //On vide la fenêtre de jeu
        getChildren().clear();

        System.out.println(donnees.getJoueur().getX() + " " + donnees.getJoueur().getY());

        //On ajoute le joueur
        getChildren().add(new VueJoueur(donnees, donnees.getJoueur().getX(), donnees.getJoueur().getY()));

        //On ajoute les blocs
        this.donnees.getTerrain().getMap().forEach(objet -> {

            if (objet instanceof Bloc) {

                getChildren().add(new VueBloc(donnees, objet.getX(), objet.getY()));

            } else if(objet instanceof Pique){

                getChildren().add(new VuePique(donnees, objet.getX(), objet.getY()));

            }
        });
    }

    @Override
    public void actualiser(Sujet sujet) {
        //On vide la fenêtre de jeu
        getChildren().clear();
        //On ajoute le joueur
        getChildren().add(new VueJoueur(donnees, donnees.getJoueur().getX(), donnees.getJoueur().getY()));

        //On ajoute les blocs
        this.donnees.getTerrain().getMap().forEach(objet -> {

            if (objet instanceof Bloc) {

                getChildren().add(new VueBloc(donnees, objet.getX(), objet.getY()));

            } else if(objet instanceof Pique){

                getChildren().add(new VuePique(donnees, objet.getX(), objet.getY()));

            }
        });

    }
}
