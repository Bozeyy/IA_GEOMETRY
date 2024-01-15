package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

import java.util.Objects;

public abstract class Objet {
    protected int x;
    protected int y;

    public Objet(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isInside(Joueur joueur)
    {
        return Objects.equals(joueur.getX(), this.x) && Objects.equals(joueur.getY(), this.y);
    }

    public int getX()
    {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract String getType();
}
