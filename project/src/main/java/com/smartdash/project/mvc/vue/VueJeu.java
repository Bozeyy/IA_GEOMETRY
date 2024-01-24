package com.smartdash.project.mvc.vue;


import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.Pique;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class VueJeu extends Pane implements Observateur {

    private Jeu donnees;

    public VueJeu(Jeu donnees) {
        this.donnees = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(donnees.getTerrain().getLongueur() * this.donnees.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Taille maximale de la fenêtre de jeu
        setMaxSize(donnees.getTerrain().getLongueur() * this.donnees.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Ajout du background en Image
        setBackground(new Background(new BackgroundImage(new Image("background2.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));


    }

    public void init(){
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

    @Override
    public void actualiser(Sujet sujet) {
        //On met à jour la position du joueur
        VueJoueur joueur = (VueJoueur) getChildren().getFirst();
        joueur.setX(donnees.getJoueur().getX() * donnees.getTailleCase());
    }
}