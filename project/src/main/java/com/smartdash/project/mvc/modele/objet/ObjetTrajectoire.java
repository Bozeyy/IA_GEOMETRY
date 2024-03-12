package com.smartdash.project.mvc.modele.objet;

public class ObjetTrajectoire extends Vide
{
    private boolean saut;

    public ObjetTrajectoire(int x, int y) {
        super(x, y);
        this.saut = false;
    }

    public boolean isSaut()
    {
        return this.saut;
    }
}
