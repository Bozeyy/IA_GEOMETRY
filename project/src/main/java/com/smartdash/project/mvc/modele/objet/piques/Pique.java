package com.smartdash.project.mvc.modele.objet.piques;

import com.smartdash.project.mvc.modele.objet.Objet;

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
