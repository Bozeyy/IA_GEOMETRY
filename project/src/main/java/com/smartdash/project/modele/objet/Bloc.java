package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

import java.util.Objects;

public class Bloc extends Objet{
    public Bloc(double x, double y){
        super(x,y);
    }

    @Override
    public boolean isInside(Joueur joueur) {
        return Objects.equals(joueur.getX(), this.x) && Objects.equals(joueur.getY(), this.y);
    }
}
