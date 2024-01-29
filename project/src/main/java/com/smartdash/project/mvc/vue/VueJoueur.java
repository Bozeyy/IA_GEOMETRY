package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueJoueur extends Rectangle{

    private Jeu modele;

    public VueJoueur(Jeu donnees, int x, int y) {
        this.modele = donnees;

        setWidth(this.modele.getTailleCase());
        setHeight(this.modele.getTailleCase());

        setX(x * this.modele.getTailleCase());
        setY(y * this.modele.getTailleCase());
        setFill(new ImagePattern(new Image("player.png")));
    }

    public void actualiser() {
        setTranslateX(modele.getJoueur().getX() * modele.getTailleCase());
        setY(modele.getJoueur().getY() * modele.getTailleCase());

        if(!modele.isJouer()){
            setRotate(0);
        }
    }

    public void animationSaut() {
        setRotate(getRotate() + 90);
    }
}
