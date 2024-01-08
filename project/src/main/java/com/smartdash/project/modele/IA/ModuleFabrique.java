package com.smartdash.project.modele.IA;

import java.util.List;

public class ModuleFabrique {
    public static java.lang.Module genererModule(int numero) {
        List<Integer> numeros;
        java.lang.Module module = new java.lang.Module();
        switch (numero) {
            case 1 -> numeros = List.of(0,-1,1,0,1,1);
            case 2 -> numeros = List.of(1,1,2,-2,4,1);
            case 3 -> numeros = List.of(3,2,4,0,0,-1);
            default -> throw new IllegalArgumentException("Numero de module invalide");
        }
        for (int i = 0; i < numeros.size(); i+=2) {
            module.addNeurone(NeuroneFabrique.genererNeuronne(numeros.get(i), numeros.get(i+1)));
        }
        return module;
    }
}
