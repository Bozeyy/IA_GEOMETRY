package com.smartdash.project.mvc.vue.VuePique;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VuePiqueRetourne extends Rectangle implements Observateur
{
    private Jeu donnees;

    public VuePiqueRetourne(Jeu donnees, int x, int y){
        this.donnees = donnees;
        setFill(new ImagePattern(new Image("pique_retourne.png")));
        setWidth(this.donnees.getTailleCase());
        setHeight(this.donnees.getTailleCase());
        setX(x * this.donnees.getTailleCase());
        setY(y * this.donnees.getTailleCase());
    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
