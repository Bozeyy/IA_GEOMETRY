package com.smartdash.project.modele.objet;

public class Vide extends Objet
{
    public Vide(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "vide";
    }
}
