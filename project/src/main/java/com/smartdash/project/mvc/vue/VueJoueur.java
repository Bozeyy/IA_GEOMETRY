package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import javafx.scene.image.Image;
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
    public void animationSaut() {
        setRotate(getRotate() + 90);
    }
}
