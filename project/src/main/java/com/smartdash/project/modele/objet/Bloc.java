package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

public class Bloc extends Objet{
    public Bloc(double x, double y){
        super(x,y);
    }

    @Override
    public boolean isInside(Joueur joueur) {
        return false;
    }
}
