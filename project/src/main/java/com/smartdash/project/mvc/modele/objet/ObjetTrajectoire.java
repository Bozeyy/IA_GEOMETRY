package com.smartdash.project.mvc.modele.objet;

public class ObjetTrajectoire extends Vide
{
    private boolean saut;
    private boolean descent;

    public ObjetTrajectoire(int x, int y) {
        super(x, y);
        this.saut = false;
        this.descent = false;
    }

    public ObjetTrajectoire(int x, int y, boolean descent, boolean saut) {
        super(x, y);
        this.saut = saut;
        this.descent = descent;
    }

    public boolean isDescent() {
        return descent;
    }

    public boolean isSaut()
    {
        return this.saut;
    }
}
