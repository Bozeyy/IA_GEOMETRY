package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Objet;

import java.util.List;

public abstract class Neurone {
    protected int x;
    protected int y;

    protected boolean active;

    public Neurone(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    public abstract boolean isActive(List<Objet> objets);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Neurone) {
            Neurone neurone = (Neurone) obj;
            return this.x == neurone.x && this.y == neurone.y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Neurone{x=" + x + ", y=" + y + ", active=" + active + '}';
    }

}
