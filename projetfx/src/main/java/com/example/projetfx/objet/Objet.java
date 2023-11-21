package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;

public interface Objet {
    public boolean isInside(Joueur joueur);
    public void draw();
    public double getPoseX();
    public double getPoseY();
}
