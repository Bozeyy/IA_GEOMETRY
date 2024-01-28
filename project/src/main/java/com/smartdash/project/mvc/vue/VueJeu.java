package com.smartdash.project.mvc.vue;


import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.Pique;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class VueJeu extends Pane implements Observateur {

    private Jeu modele;

    public VueJeu(Jeu donnees) {
        this.modele = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Taille maximale de la fenêtre de jeu
        setMaxSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Ajout du background en Image
        setBackground(new Background(new BackgroundImage(new Image("background2.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));


    }

    public void init(){
        //On vide la fenêtre de jeu
        getChildren().clear();

        //On ajoute le joueur
        VueJoueur joueur = new VueJoueur(modele, modele.getJoueur().getX(), modele.getJoueur().getY());
        getChildren().add(joueur);

        //On ajoute les blocs
        this.modele.getTerrain().getMap().forEach(objet -> {

            if (objet instanceof Bloc) {

                getChildren().add(new VueBloc(modele, objet.getX(), objet.getY()));

            } else if(objet instanceof Pique){

                getChildren().add(new VuePique(modele, objet.getX(), objet.getY()));

            }
        });

        //On met à jour la caméra
        joueur.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            System.out.println();
            if (offset > 400 && offset < (modele.getTerrain().getLongueur() * modele.getTailleCase()) - 400) {
                setTranslateX(-(offset - 400));
            } else if (offset < 400) {
                setTranslateX(0);
            } else {
                setTranslateX(-(modele.getTerrain().getLongueur() * modele.getTailleCase() - 800));
            }
        });

        //On met à jour la rotation du joueur
        joueur.yProperty().addListener((obs, old, newValue) -> {
            animationSaut();
        });
    }

    public void animationSaut(){
        VueJoueur joueur = (VueJoueur) getChildren().getFirst();
        joueur.animationSaut();
    }

    @Override
    public void actualiser(Sujet sujet) {
        //On met à jour la position du joueur
        VueJoueur joueur = (VueJoueur) getChildren().getFirst();
        joueur.setTranslateX(modele.getJoueur().getX() * modele.getTailleCase());
        joueur.setY(modele.getJoueur().getY() * modele.getTailleCase());
        if(!modele.isJouer()){
            joueur.setRotate(0);
        }
        VueJoueur vueJoueur = (VueJoueur) getChildren().getFirst();

        vueJoueur.setX(modele.getJoueur().getX() * modele.getTailleCase());
        vueJoueur.setY(modele.getJoueur().getY() * modele.getTailleCase());

        this.setTranslateX(-this.modele.getCamera().getX());
        this.setTranslateY(-this.modele.getCamera().getY());
    }
}