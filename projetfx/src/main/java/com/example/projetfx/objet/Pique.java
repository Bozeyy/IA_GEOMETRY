package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;
import com.example.projetfx.util.util;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;

public class Pique extends Rectangle implements Objet {

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
        this.setWidth(util.METRE);
        this.setHeight(util.METRE);
        this.setX(this.x);
        this.setY(this.y);
        this.setFill(javafx.scene.paint.Color.RED);
    }

    public double getPoseX(){
        return this.x;
    }

    public double getPoseY(){
        return this.y;
    }

}
