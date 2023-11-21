package com.example.projetfx.modele;

import com.example.projetfx.util.util;
import javafx.scene.shape.Rectangle;

public class Joueur extends Rectangle {

    public double x;
    public double y;

    public double vitesseX = util.VITESSE;

    public double vitesseY = util.GRAVITE;

    public Joueur(double x, double y){
        super();
        this.x = x;
        this.y = y;
        this.setX(x);
        this.setY(y);
        this.dessiner();
    }

    /////////////////////////////GETTER/////////////////////////////

    /**
     * Retourne la position en x du joueur
     * @return la position en x du joueur
     */
    public double getPoseX(){
        return this.x;
    }

    /**
     * Retourne la position en y du joueur
     * @return la position en y du joueur
     */
    public double getPoseY(){
        return this.y;
    }

    /////////////////////////////SETTER/////////////////////////////

    /**
     * Modifie la position en x du joueur
     * @param x la nouvelle position en x du joueur
     */
    public void setPoseX(double x){
        this.x = x;
    }

    /**
     * Modifie la position en y du joueur
     * @param y la nouvelle position en y du joueur
     */
    public void setPoseY(double y){
        this.y = y;
    }

    /////////////////////////////METHODE/////////////////////////////

    /**
     * méthode update
     * @param delta
     */
    public void update(double delta){
        System.out.println("delta : " + delta);
        System.out.println("on se déplace de : " + this.vitesseX * delta + " en x");
        System.out.println("on se déplace de : " + this.vitesseY * delta + " en y");
        this.x += (this.vitesseX * delta);
        this.y += (this.vitesseY * delta);
        this.setX(this.x);
        this.setY(this.y);
    }

    /**
     * méthode draw
     */
    public void dessiner(){
        this.setWidth(util.METRE);
        this.setHeight(util.METRE);
        this.setX(this.x);
        this.setY(this.y);
        this.setFill(javafx.scene.paint.Color.BLACK);
    }
}
