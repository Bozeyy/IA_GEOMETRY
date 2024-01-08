package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Objet;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private List<Neurone> neurones;

    public Module() {
        this.neurones = new ArrayList<>(Constantes.NB_NEURONES_PAR_MODULES);
    }

    public boolean isActive(List<Objet> objets) {
        return this.neurones.stream().allMatch(neurone -> neurone.isActive(objets));
    }

    public void addNeurone(Neurone neurone) {
        if (this.neurones.size() < Constantes.NB_NEURONES_PAR_MODULES && !this.neurones.contains(neurone)) {
            this.neurones.add(neurone);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Module && this.neurones.size() == ((Module) obj).neurones.size()) {
            Module module = (Module) obj;
            return module.neurones.stream().allMatch(neurone -> neurone.equals(((Module) obj).neurones.get(module.neurones.indexOf(neurone))));
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Module{\n");
        for (Neurone neurone : this.neurones) {
            res.append("\t\t").append(neurone.toString()).append("\n");
        }
        res.append("\t}");

        return res.toString();
    }

}
