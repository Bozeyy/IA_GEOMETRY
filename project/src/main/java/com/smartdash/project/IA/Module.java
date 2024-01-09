package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Objet;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private List<Neurone> neurones;

    public Module() {
        this.neurones = new ArrayList<>(Constantes.NB_NEURONES_PAR_MODULES);
    }

    public boolean isActive() {
        return this.neurones.stream().allMatch(Neurone::isActive);
    }

    public void setActive(int x, int y, String type) {
        this.neurones.forEach(neurone -> neurone.setActive(x, y, type));
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

    public List<Neurone> getNeurones() {
        return neurones;
    }
}
