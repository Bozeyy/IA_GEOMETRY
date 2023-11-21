package com.example.projetfx.modele;

import com.example.projetfx.util.util;
import javafx.scene.shape.Rectangle;

public class Joueur extends Rectangle {

    public double x;
    public double y;

    public double vitesseX;

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
        this.x += this.vitesseX * delta;
        this.y += this.vitesseY * delta;
        this.setX(this.x);
        this.setY(this.y);
    }

    /**
     * méthode draw
     */
    public void dessiner(){
        this.setWidth(util.TAILLE_JOUEUR);
        this.setHeight(util.TAILLE_JOUEUR);
        this.setX(this.x);
        this.setY(this.y);
        this.setFill(javafx.scene.paint.Color.BLACK);
    }
}
