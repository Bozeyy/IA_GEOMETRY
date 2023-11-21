package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;
import javafx.scene.layout.Pane;

public class Pique extends Pane implements Objet {

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
