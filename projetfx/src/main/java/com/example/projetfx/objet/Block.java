package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;
import com.example.projetfx.util.util;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle implements Objet {

    public double x;
    public double y;

    public Block(double x, double y){
        super();
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
        this.setFill(Color.GRAY);
    }
}
