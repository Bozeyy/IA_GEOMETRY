package com.smartdash.project.IA;

import java.util.ArrayList;
import java.util.List;

public class Reseau {
    private boolean active;
    private List<Module> modules;

    public Reseau() {
        this.modules = new ArrayList<>(Constantes.NB_MODULES_PAR_RESEAU);
        this.active = false;
    }

    public boolean isActive(Object object) {
        this.active = this.modules.stream().anyMatch(module -> module.isActive(object));
        return this.active;
    }

    public void addModule(Module module) {
        if (this.modules.size() < Constantes.NB_MODULES_PAR_RESEAU) {
            this.modules.add(module);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Reseau{").append("\n");
        int i = 0;
        for (Module module : this.modules) {
            res.append("\t").append(module.toString()).append("\n");
            i++;
        }
        res.append("}");
        return res.toString();
    }



}
