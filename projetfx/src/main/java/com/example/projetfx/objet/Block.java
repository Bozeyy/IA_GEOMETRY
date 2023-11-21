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

    /**
     * détermine si le joueur et en collision avec un block
     *
     * @param joueur
     * @return
     */
    public boolean isInside(Joueur joueur) {
        if (this.isPointInside(joueur.x, joueur.y)) {
            return true;
        } else if (this.isPointInside(joueur.x+util.METRE, joueur.y)) {
            return true;
        } else if (this.isPointInside(joueur.x, joueur.y+util.METRE)) {
            return true;
        } else if (this.isPointInside(joueur.x+util.METRE, joueur.y+util.METRE)) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(){
        this.setWidth(util.METRE);
        this.setHeight(util.METRE);
        this.setX(this.x);
        this.setY(this.y);
        this.setFill(Color.GRAY);
    }

    public double getPoseX(){
        return this.x;
    }

    public double getPoseY(){
        return this.y;
    }

    /**
     * méthode rentreDansBlock
     * retourne vrai si le joueur rentre dans un block
     * @param joueur le joueur
     * @param velocityX la velocité en x
     */
    public boolean rentrerDansBlock(Joueur joueur, double velocityX) {
        Joueur newJ = new Joueur(joueur.x + velocityX, joueur.y);
        return this.isInside(newJ);
    }

    /**
     * méthode tomberSurBlock
     * permet d'arrêter la gravité du joueur quand il tombe sur un block
     * @param joueur le joueur
     * @param velocityY la velocité en y
     */
    public boolean tomberSurBlock(Joueur joueur, double velocityY) {
        Joueur newJ = new Joueur(joueur.x, joueur.y + velocityY);
        return this.isInside(newJ);
    }

    public boolean isPointInside(double x, double y) {
        if (x >= this.x && x <= this.x + util.METRE && y >= this.y && y <= this.y + util.METRE) {
            return true;
        } else {
            return false;
        }
    }
}
