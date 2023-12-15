package IA;

import java.util.ArrayList;
import java.util.List;

public class Reseau {
    private boolean active;
    private List<Module> modules;

    public Reseau() {
        this.modules = new ArrayList<>(3);
        this.active = false;
    }

    public boolean isActive(Object object) {
        return this.modules.stream().anyMatch(module -> module.isActive(object));
    }



}
