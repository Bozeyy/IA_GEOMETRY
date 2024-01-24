package com.smartdash.project.vue;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Sujet;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueJoueur extends Rectangle implements Observateur {

    private Jeu donnees;

    public VueJoueur(Jeu donnees, int x, int y) {
        this.donnees = donnees;
        setWidth(this.donnees.getTailleCase());
        setHeight(this.donnees.getTailleCase());
        setX(x * this.donnees.getTailleCase());
        setY(y * this.donnees.getTailleCase());
        setFill(new ImagePattern(new Image("player.png")));
    }


    @Override
    public void actualiser(Sujet sujet) {

    }
}
