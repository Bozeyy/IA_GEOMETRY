package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VueParticule extends Pane {
    private final Jeu modele;
    private final List<Rectangle> particules;
    private final Random random = new Random();

    public VueParticule(Jeu modele) {
        this.modele = modele;
        this.particules = new ArrayList<>();

        int NOMBRE_PARTICULES = 5;
        for (int i = 0; i < NOMBRE_PARTICULES; i++) {
            double TAILLE_PARTICULE = 5;
            Rectangle particule = new Rectangle(TAILLE_PARTICULE, TAILLE_PARTICULE, Color.WHITE);
            particule.setOpacity(0);

            PauseTransition pause = new PauseTransition(Duration.seconds(i * 60));
            pause.setOnFinished(event -> {
                particule.setOpacity(1);
            });

            pause.play(); // Démarrer le délai

            particules.add(particule);
            getChildren().add(particule);
        }
    }

    /**
     * Méthode qui permet de déplacer les particules à chaque temps
     */
    public void actualiser() {
        if(!modele.getJoueur().isSaut())
        {

            int DISTANCE_ARRIERE = 2;
            double zoneDebutX = (modele.getJoueur().getX() - DISTANCE_ARRIERE) * modele.getTailleCase();
            double zoneFinX = (modele.getJoueur().getX() * modele.getTailleCase()) - 10;

            // Position du joueur
            double posXJoueur = modele.getJoueur().getX() * modele.getTailleCase();
            double posYJoueur = ((modele.getJoueur().getY()) * modele.getTailleCase()) + 24;

            for (Rectangle particule : particules) {
                // Mise à jour de la position X de chaque particule avec une valeur aléatoire dans la zone définie
                double newX = zoneDebutX + random.nextDouble() * (zoneFinX - zoneDebutX);

                //Faire en sorte que si le X est proche du joueur il soit gros, sinon il devient petit
                double distance = Math.abs(newX - posXJoueur);

                // Ajuster la taille et l'opacité de la particule en fonction de la distance
                double taille = calculerTailleParticule(distance);
                double opacite = calculerOpaciteParticule(distance);

                particule.setWidth(taille);
                particule.setHeight(taille);
                particule.setOpacity(opacite);

                particule.setX(newX);
                particule.setY(posYJoueur);
            }
        }
        else {
            // On rend les particules invisibles quand on saute
            for (Rectangle particule : particules) {
                particule.setOpacity(0);
            }
        }
    }

    /**
     * Méthode qui permet de calculer la taille des particules en fonction de la distance
     * @param distance distance par rapport au joueur
     * @return retourne la taille
     */
    private double calculerTailleParticule(double distance) {
        double tailleMax = 15.0; // Taille maximale de la particule
        double tailleMin = 1.0;  // Taille minimale de la particule
        double distanceMax = 100.0; // Distance maximale d'effet

        // Assurez-vous que la distance n'est pas supérieure à distanceMax
        distance = Math.min(distance, distanceMax);

        // Utiliser une décroissance exponentielle pour une réduction plus rapide de la taille
        double facteur = (distance / distanceMax);
        double taille = tailleMax * Math.pow(1 - facteur, 2);

        taille = Math.max(taille, tailleMin);

        return taille;
    }

    /**
     * Méthode qui permet de calculer l'opacité d'une particule par rapport à la distance
     * @param distance distance par rapport au joueur
     * @return retourne l'opacité
     */
    private double calculerOpaciteParticule(double distance) {
        double opaciteMax = 1.0; // Opacité maximale (complètement opaque)
        double opaciteMin = 0.1; // Opacité minimale (presque transparent)
        double distanceMax = 100.0; // Distance maximale pour l'effet d'opacité

        // Assurez-vous que la distance n'est pas supérieure à distanceMax
        distance = Math.min(distance, distanceMax);

        // Calculer l'opacité de la particule en fonction de la distance
        double opacite = opaciteMax - ((distance / distanceMax) * (opaciteMax - opaciteMin));

        opacite = Math.max(opacite, opaciteMin);

        return opacite;
    }
}
