package com.smartdash.project.IA;

import java.util.Random;

public class NeuroneFabrique {
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

    public static Neurone genererNeuronneAleatoire() {
        Random rand = new Random();

        int x = rand.nextInt((Constantes.X_NEURONES_MAX - Constantes.X_NEURONES_MIN) + 1) + Constantes.X_NEURONES_MIN;
        int y = rand.nextInt((Constantes.Y_NEURONES_MAX - Constantes.Y_NEURONES_MIN) + 1) + Constantes.Y_NEURONES_MIN;

        System.out.println(x);
        System.out.println(y);

        return genererNeuronne(x,y);
    }
}
