package IA;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private boolean active;
    private List<Neurone> neurones;

    public Module() {
        this.neurones = new ArrayList<>(Constantes.NB_NEURONES_PAR_MODULES);
        this.active = false;
    }

    public boolean isActive(Object object) {
        this.active = this.neurones.stream().allMatch(neurone -> neurone.isActive(object));
        return this.active;
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
        StringBuilder res = new StringBuilder();
        res.append("Module{ ").append("\n");
        for (int i = 0; i < neurones.size(); i++) {
            res.append("\t\t").append(neurones.get(i).toString()).append("\n");
        }
        res.append("\t}");
        return res.toString();
    }
}
