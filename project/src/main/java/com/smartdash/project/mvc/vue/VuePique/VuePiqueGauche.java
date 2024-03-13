package com.smartdash.project.mvc.vue.VuePique;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class VuePiqueGauche extends Polygon implements Observateur
{

    private Jeu donnees;

    public VuePiqueGauche(Jeu donnees, int x, int y, Color c, double tailleCase){
        this.donnees = donnees;
        if(tailleCase == 0)
            tailleCase = this.donnees.getTailleCase();

        // Coordonnées des points du pique
        double demiLargeur = tailleCase / 2.0;
        double hauteur = tailleCase * Math.sqrt(3) / 2.0;

        double x1 = x * tailleCase + tailleCase;
        double y1 = (y + 1) * tailleCase;

        double x2 = x1 - hauteur;
        double y2 = y1 - demiLargeur;

        double x3 = x1;
        double y3 = y1 - tailleCase;

        getPoints().addAll(x1, y1,
                x2, y2,
                x3, y3);

        // dégradé
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(0, 0, 0, 1)),
                new Stop(0.6, Color.rgb(0, 0, 0, 0.8)),
                new Stop(1, Color.rgb((int)(c.getRed() * 255), (int)(c.getGreen() * 255), (int)(c.getBlue() * 255), 0.3))
        );

        setFill(gradient);

        setStroke(Color.WHITE);
        setStrokeWidth(1);
        setStrokeType(StrokeType.INSIDE);

        // effet de flou
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        setEffect(blur);
    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
