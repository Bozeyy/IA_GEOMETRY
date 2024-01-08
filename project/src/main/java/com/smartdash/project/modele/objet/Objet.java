package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

import java.util.Objects;

public class Objet {
    protected Double x;
    protected Double y;

    public Objet(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isInside(Joueur joueur)
    {
        return Objects.equals(joueur.getX(), this.x) && Objects.equals(joueur.getY(), this.y);
    }

    public double getX()
    {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
