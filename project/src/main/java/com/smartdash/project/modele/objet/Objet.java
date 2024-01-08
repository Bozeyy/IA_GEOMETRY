package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

public abstract class Objet {
    protected Double x;
    protected Double y;

    public Objet(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isInside(Joueur joueur);

    public double getX()
    {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
