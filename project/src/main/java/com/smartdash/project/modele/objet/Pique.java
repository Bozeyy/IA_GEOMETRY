package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

import java.util.Objects;

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
