package com.smartdash.project.modele.objet;

import com.smartdash.project.modele.Joueur;

import java.util.Objects;

public class Bloc extends Objet{
    public Bloc(int x, int y){
        super(x,y);
    }


    @Override
    public String getType() {
        return "bloc";
    }
}
