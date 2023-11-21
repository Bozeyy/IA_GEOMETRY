package com.dash.game.Object;

import com.dash.game.Modele.Joueur;

public class Pique implements Objet {

    public double x;
    public double y;

    public Pique(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isInside(Joueur joueur) {
        return false;
        // TODO
    }

    public void draw(){
        // TODO
    }

}
