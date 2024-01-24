package com.smartdash.project.mvc.modele.objet;

public class Pique extends Objet
{
    public Pique(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "pique";
    }
}
