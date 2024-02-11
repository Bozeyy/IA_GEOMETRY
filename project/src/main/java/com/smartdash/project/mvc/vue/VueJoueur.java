package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class VueJoueur extends Rectangle {

    private Jeu modele;
    private DropShadow dropShadow; // Déclarer l'effet DropShadow comme un champ

    public VueJoueur(Jeu donnees, int x, int y) {
        this.modele = donnees;

        setWidth(this.modele.getTailleCase());
        setHeight(this.modele.getTailleCase());

        setX(x * this.modele.getTailleCase());
        setY(y * this.modele.getTailleCase());
        setFill(new ImagePattern(new Image("player.png")));

        // Initialiser et configurer l'effet DropShadow
        dropShadow = new DropShadow();
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setRadius(40);
        dropShadow.setOffsetX(-10);
        dropShadow.setOffsetY(-10);

        // Appliquer l'effet DropShadow au rectangle du joueur
        setEffect(dropShadow);
    }

    public void actualiser() {
        setTranslateX(modele.getJoueur().getX() * modele.getTailleCase());
        setY(modele.getJoueur().getY() * modele.getTailleCase());

        // Appeler la méthode pour actualiser l'effet DropShadow
        actualiserEffet();

        if (!modele.isJouer()) {
            setRotate(0);
        }
    }

    public void animationSaut() {
        setRotate(getRotate() + 90);
    }

    // Mettre à jour la position de l'effet DropShadow en fonction du personnage
    private void actualiserEffet() {
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
    }
}