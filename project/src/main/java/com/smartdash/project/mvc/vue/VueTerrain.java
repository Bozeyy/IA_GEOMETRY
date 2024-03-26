package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.piques.PiqueDroit;
import com.smartdash.project.mvc.modele.objet.piques.PiqueGauche;
import com.smartdash.project.mvc.modele.objet.piques.PiqueRetourne;
import com.smartdash.project.mvc.vue.VuePique.VuePique;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueDroit;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueGauche;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueRetourne;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class VueTerrain extends Pane {

    Jeu modele;
    double tailleCase;

    Color couleurNiveau;

    public VueTerrain(Jeu modele, double longueur, double hauteur, Color couleurNiveau){
        this.modele = modele;
        setPrefSize(longueur,hauteur);
        tailleCase = longueur / modele.getTerrain().getLongueur();
        this.couleurNiveau = couleurNiveau;
        setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
        init();
    }

    public void init(){
        getChildren().clear();
        this.modele.getTerrain().getMap().forEach(objet -> {
            if (objet instanceof Bloc) {
                getChildren().add(new VueBloc(modele, objet.getX(), objet.getY(), this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueRetourne) {
                getChildren().add(new VuePiqueRetourne(modele, objet.getX(), objet.getY(), this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueGauche) {
                getChildren().add(new VuePiqueGauche(modele, objet.getX(), objet.getY(), this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueDroit) {
                getChildren().add(new VuePiqueDroit(modele, objet.getX(), objet.getY(), this.couleurNiveau,tailleCase));
            } else if (objet instanceof Pique) {

                getChildren().add(new VuePique(modele, objet.getX(), objet.getY(), this.couleurNiveau,tailleCase));

            }
        });
    }
}
