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
        setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null)));
    }

    public void init(){
        //On vide la fenêtre de jeu
        getChildren().clear();

        //On ajoute le joueur
        VueJoueur joueur = new VueJoueur(donnees, donnees.getJoueur().getX(), donnees.getJoueur().getY());
        getChildren().add(joueur);

        //On ajoute les blocs
        this.donnees.getTerrain().getMap().forEach(objet -> {

            if (objet instanceof Bloc) {

                getChildren().add(new VueBloc(donnees, objet.getX(), objet.getY()));

            } else if(objet instanceof Pique){

                getChildren().add(new VuePique(donnees, objet.getX(), objet.getY()));

            }
        });

        //On met à jour la caméra
        joueur.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            System.out.println();
            if (offset > 400 && offset < (donnees.getTerrain().getLongueur() * donnees.getTailleCase()) - 400) {
                setTranslateX(-(offset - 400));
            } else if (offset < 400) {
                setTranslateX(0);
            } else {
                setTranslateX(-(donnees.getTerrain().getLongueur() * donnees.getTailleCase() - 800));
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
        joueur.setTranslateX(donnees.getJoueur().getX() * donnees.getTailleCase());
        joueur.setY(donnees.getJoueur().getY() * donnees.getTailleCase());
        if(!donnees.isJouer()){
            joueur.setRotate(0);
        }
    }
}