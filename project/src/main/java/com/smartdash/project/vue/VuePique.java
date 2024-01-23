package com.smartdash.project.vue;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Sujet;
import com.smartdash.project.modele.objet.Pique;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class VuePique extends Rectangle implements Observateur{
    private Jeu donnees;

    public VuePique(Jeu donnees){
        this.donnees = donnees;
        setFill(new ImagePattern(new Image("pique.png")));
    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
