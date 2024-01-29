package com.smartdash.project.mvc.vue;


import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.piques.PiqueDroit;
import com.smartdash.project.mvc.modele.objet.piques.PiqueGauche;
import com.smartdash.project.mvc.modele.objet.piques.PiqueRetourne;
import com.smartdash.project.mvc.vue.VuePique.VuePique;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueDroit;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueGauche;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueRetourne;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueJeu extends Pane implements Observateur {

    private Jeu modele;

    public VueJeu(Jeu donnees) {
        this.modele = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //setMinSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Taille maximale de la fenêtre de jeu
        setMaxSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Ajout du background en Image
        //setBackground(new Background(new BackgroundImage(new Image("background2.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));


    }

    /**
     * Méthode qui permet de tout initialiser
     */
    public void init(){
        //On vide la fenêtre de jeu
        getChildren().clear();

        Rectangle background = new Rectangle(10000, 10000);
        background.setFill(Color.web("#565656"));

        // Ajouter le Rectangle et l'ImageView à la scène
        getChildren().addAll(background);

        //On ajoute le joueur
        VueJoueur joueur = new VueJoueur(modele, modele.getJoueur().getX(), modele.getJoueur().getY());
        getChildren().add(joueur);

        //On ajoute les blocs
        this.modele.getTerrain().getMap().forEach(objet -> {
            if (objet instanceof Bloc) {
                getChildren().add(new VueBloc(modele, objet.getX(), objet.getY()));
            }
            else if(objet instanceof PiqueRetourne)
            {
                getChildren().add(new VuePiqueRetourne(modele, objet.getX(), objet.getY()));
            } else if (objet instanceof PiqueGauche) {
                getChildren().add(new VuePiqueGauche(modele, objet.getX(), objet.getY()));
            } else if (objet instanceof PiqueDroit) {
                getChildren().add(new VuePiqueDroit(modele, objet.getX(), objet.getY()));
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
        VueJoueur joueur = (VueJoueur) getChildren().get(1);
        joueur.animationSaut();
    }

    /**
     * Méthode qui permet d'actualiser à chaque temps
     * @param sujet le modele
     */
    @Override
    public void actualiser(Sujet sujet) {
        VueJoueur vueJoueur = (VueJoueur) getChildren().get(1);
        vueJoueur.actualiser();
    }
}