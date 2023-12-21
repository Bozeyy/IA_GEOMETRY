package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

public abstract class Objet {
    private Double x;
    private Double y;

    public Objet(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isInside(Joueur joueur);
}
