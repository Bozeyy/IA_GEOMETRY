package com.smartdash.project.mvc.modele.objet;

public class ObjetTrajectoire extends Objet
{
    private boolean saut;
    private boolean descent;

    public ObjetTrajectoire(int x, int y) {
        super(x, y);
        this.saut = false;
        this.descent = false;
    }

    @Override
    public String getType() {
        return "trajectoire";
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

    public void setSaut(boolean b) {
        saut = b;
    }
}
