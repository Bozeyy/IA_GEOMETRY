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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class VueTerrain extends Pane {

    Jeu modele;
    double tailleCase;

    Label titre;

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

        initTitre();

        this.modele.getTerrain().getMap().forEach(objet -> {
            //mettre le terrain au milieu
            int newY = (int) (objet.getY() + getPrefHeight() / tailleCase / 2 - 15 );

            if (objet instanceof Bloc) {
                getChildren().add(new VueBloc(modele, objet.getX(), newY, this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueRetourne) {
                getChildren().add(new VuePiqueRetourne(modele, objet.getX(), newY, this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueGauche) {
                getChildren().add(new VuePiqueGauche(modele, objet.getX(), newY, this.couleurNiveau,tailleCase));
            } else if (objet instanceof PiqueDroit) {
                getChildren().add(new VuePiqueDroit(modele, objet.getX(), newY, this.couleurNiveau,tailleCase));
            } else if (objet instanceof Pique) {

                getChildren().add(new VuePique(modele, objet.getX(), newY, this.couleurNiveau,tailleCase));

            }
        });
    }

    public void initTitre(){
        titre = new Label("Aperçu du terrain");
        titre.setId("apercu");
        titre.setPrefSize(this.getPrefWidth(),50);
        titre.setStyle("-fx-background-color:#debfbf; -fx-font-size: 30px; -fx-alignment: center");
        getChildren().add(titre);
    }
}
