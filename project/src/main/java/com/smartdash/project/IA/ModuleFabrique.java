package com.smartdash.project.IA;

import java.util.List;

public class ModuleFabrique {

    /**
     * genere un module à partir de neurone aleatoire placés aux coordonnées souhaitées
     * @param numero
     * @return
     */
    public static Module genererModule(int numero) {
        List<Integer> numeros;
        Module module = new Module();
        switch (numero) {
            case 1 -> numeros = List.of(0,-1,1,0,2,0);
            case 2 -> numeros = List.of(3,0,3,1,1,1);
            case 3 -> numeros = List.of(2,-2,1,1,2,0);
            default -> throw new IllegalArgumentException("Numero de module invalide");
        }
        for (int i = 0; i < numeros.size(); i+=2) {
            module.addNeurone(NeuroneFabrique.genererNeuronne(numeros.get(i), numeros.get(i+1)));
        }
        return module;
    }

    /**
     * genere un Module a partir de neurones definis
     * @param neurone
     * @return
     */
    public static Module genererModule (Neurone[] neurone) {
        Module module = new Module();
        for (Neurone n : neurone) {
            module.addNeurone(n);
        }
        return module;
    }
}
