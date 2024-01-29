package com.smartdash.project.IA;

import java.util.ArrayList;
import java.util.List;

public class Reseau implements Cloneable {
    private List<Module> modules;
    
    public Reseau() {
        this.modules = new ArrayList<>(Constantes.NB_MODULES_PAR_RESEAU);
    }

    public boolean isActive() {
        return this.modules.stream().anyMatch(Module::isActive);
    }

    public void setActive(int x, int y, String type) {
        modules.forEach(module -> module.setActive(x, y, type));
    }
    
    public void addModule(Module module) {
        if (this.modules.size() < Constantes.NB_MODULES_PAR_RESEAU) {
            this.modules.add(module);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Reseau{\n");
        for (Module module : this.modules) {
            res.append("\t").append(module.toString()).append("\n");
        }
        res.append("}");
        return res.toString();
    }

    public List<Module> getModules() {
        return modules;
    }

    public int getNbNeurone() {
        return this.modules.stream().mapToInt(module -> module.getNeurones().size()).sum();
    }

    public void renitialiser() {
        modules.forEach(Module::renitialiser);

    }

    @Override
    public Reseau clone() {
        try {
            Reseau clone = (Reseau) super.clone();
            List<Module> clonedModules = new ArrayList<>();

            for (Module module : modules) {
                clonedModules.add(module.clone());
            }

            clone.modules = clonedModules;  // Remplacez l'ancienne liste par la nouvelle
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public int nbModulesActifs() {
        return (int) this.modules.stream().filter(Module::isActive).count();
    }

}
