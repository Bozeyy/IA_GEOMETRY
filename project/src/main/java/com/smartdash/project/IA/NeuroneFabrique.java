package com.smartdash.project.IA;

import com.smartdash.project.IA.neurones.*;

import java.util.Random;

public class NeuroneFabrique {
    /**
     * Méthode qui permet de générer des neurones aléatoirement
     * @param x position x
     * @param y position y
     * @return retourne un neurone
     */
    public static Neurone genererNeuronne(int x, int y) {
        String[] types = {"Pique", "NonPique", "Bloc", "NonBloc", "Vide", "NonVide", "Actif"};
        Random random = new Random();
        String type = types[random.nextInt(types.length)];
        return switch (type) {
            case "Pique" -> new NeuronePique(x, y);
            case "NonPique" -> new NeuroneNonPique(x, y);
            case "Bloc" -> new NeuroneBloc(x, y);
            case "NonBloc" -> new NeuroneNonBloc(x, y);
            case "Vide" -> new NeuroneVide(x, y);
            case "NonVide" -> new NeuroneNonVide(x, y);
            case "Actif" -> new NeuroneActif(x, y);
            default -> null;
        };
    }

    /**
     * Méthode qui permet de générer un neurone avec un type précis
     * @param x position x
     * @param y position y
     * @param type type de neurone précis
     * @return retourne un Neurone
     */
    public static Neurone genererNeuronneType(int x, int y, char type) {
        return switch (type) {
            case 'p' -> new NeuronePique(x, y);
            case 'q' -> new NeuroneNonPique(x, y);
            case 'b' -> new NeuroneBloc(x, y);
            case 'd' -> new NeuroneNonBloc(x, y);
            case 'v' -> new NeuroneVide(x, y);
            case 'w' -> new NeuroneNonVide(x, y);
            case 'a' -> new NeuroneActif(x, y);
            default -> null;
        };
    }

    /**
     * Méthode qui permet de générer des neurones à des positions aléatoires
     * @return retourne un Neurone
     */
    public static Neurone genererNeuronnePositionAleatoire() {
        Random rand = new Random();

        int x = rand.nextInt((Constantes.X_NEURONES_MAX - Constantes.X_NEURONES_MIN) + 1) + Constantes.X_NEURONES_MIN;
        int y = rand.nextInt((Constantes.Y_NEURONES_MAX - Constantes.Y_NEURONES_MIN) + 1) + Constantes.Y_NEURONES_MIN;

        return genererNeuronne(x,y);
    }
}
