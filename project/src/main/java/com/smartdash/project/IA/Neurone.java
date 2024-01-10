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

    /**
     * permet de retourner l'etat du neurone (activé ou désactivé)
     * @return
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * permet de mettre a jour l etat du neurone à partir des donnés d une case (l'active ou le désactive si la case correspond)
     * @param x
     * @param y
     * @param type
     */
    public abstract void setActive(int x, int y, String type);

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
        return "{x=" + x + ", y=" + y + ", active=" + active + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
