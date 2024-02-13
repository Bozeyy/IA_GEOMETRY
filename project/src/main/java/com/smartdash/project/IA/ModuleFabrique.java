package com.smartdash.project.IA;

import com.smartdash.project.IA.neurones.Neurone;

import java.util.List;

public class ModuleFabrique {

    /**
     * Génère un module à partir de neurone aleatoire placés aux coordonnées souhaitées
     * @param numero numéro du module
     * @return retourne un module
     */
    public static Module genererModule(int numero) {
        List<Integer> numeros;
        Module module = new Module();
        switch (numero) {
            case 1 -> numeros = List.of(1,0,2,0,3,0,4,0,0,-1);
            case 2 -> numeros = List.of(3,0,1,-1,2,-2,1,0,2,0);
            case 3 -> numeros = List.of(1,0,2,0,3,0,4,0,0,-1);
            case 4 -> numeros = List.of(1,0,2,-2,2,-3,3,2,1,-1);
            case 5 -> numeros = List.of(1,-1,2,-1,1,1,2,2,3,3);
            default -> throw new IllegalArgumentException("Numero de module invalide");
        }
        for (int i = 0; i < numeros.size(); i+=2) {
            module.addNeurone(NeuroneFabrique.genererNeuronne(numeros.get(i), numeros.get(i+1)));
        }
        return module;
    }

    /**
     * Génère un Module à partir de neurones définis
     * @param neurone neurone précis
     * @return retourne un module
     */
    public static Module genererModule (Neurone[] neurone) {
        Module module = new Module();
        for (Neurone n : neurone) {
            module.addNeurone(n);
        }
        return module;
    }

    /**
     * Méthode qui permet de générer un module avec des positions aléatoires de neurone
     * @return un Module
     */
    public static Module genererModulePosAleatoire() {
        Module module = new Module();
        for (int i = 1; i <= Constantes.NB_NEURONES_PAR_MODULES; i++) {
            Neurone neurone = NeuroneFabrique.genererNeuronnePositionAleatoire();
            module.addNeurone(neurone);
        }
        return module;
    }
}
