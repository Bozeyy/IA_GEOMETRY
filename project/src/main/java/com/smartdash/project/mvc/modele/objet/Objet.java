package com.smartdash.project.mvc.modele.objet;

import com.smartdash.project.mvc.modele.Joueur;

import java.util.Objects;

public abstract class Objet {
    protected int x;
    protected int y;

    public Objet(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode qui permet de détecter une collision
     * @param joueur joueur qui se prend la collision
     * @return retourne s'il y a une collision
     */
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

    /**
     * Méthode qui retourne le type de l'objet
     * @return le type de l'objet
     */
    public abstract String getType();

    @Override
    public boolean equals(Object objet) {
        if (objet.getClass() != this.getClass()) {
            return false;
        }
        return this.x == ((Objet)objet).x && this.y == ((Objet)objet).y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
