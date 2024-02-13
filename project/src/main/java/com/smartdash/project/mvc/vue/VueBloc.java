package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class VueBloc extends Rectangle implements Observateur {
    private Jeu donnees;

    private Color couleur;
    private static final int NUM_SEGMENTS = 10; // Nombre de segments verticaux dans le bloc

    public VueBloc(Jeu donnees, int x, int y, Color c) {
        this.donnees = donnees;

        // dégradé linéaire pour la partie inférieure du bloc
        Stop[] stops = new Stop[] {
                new Stop(0, Color.rgb(0, 0, 0, 1)),
                new Stop(0.6, Color.rgb(0, 0, 0, 0.8)),
                new Stop(1, Color.rgb((int)(c.getRed() * 255), (int)(c.getGreen() * 255), (int)(c.getBlue() * 255), 0.3))

        };
        LinearGradient couleurGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);


        setFill(couleurGradient);
        setWidth(this.donnees.getTailleCase());
        setHeight(this.donnees.getTailleCase());
        setX(x * this.donnees.getTailleCase());
        setY(y * this.donnees.getTailleCase());

        // Ajouter une bordure blanche autour du rectangle
        setStroke(Color.WHITE);
        setStrokeWidth(2); // Ajuster la largeur de la bordure selon vos besoins

        // Ajouter un effet de flou gaussien
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        setEffect(blur);

        // Ajuster l'opacité de la partie inférieure du rectangle
        setOpacity(0.8);
    }

    @Override
    public void actualiser(Sujet sujet) {
        // Mettre à jour le bloc si nécessaire
    }
}