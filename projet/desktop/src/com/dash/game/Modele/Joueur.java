package com.dash.game.Modele;

import com.dash.game.util.util;

public class Joueur {

    public double x;
    public double y;

    public double vitesseX;

    public double vitesseY = util.GRAVITE;

    public Joueur(double x, double y){
        this.x = x;
        this.y = y;
    }

    /////////////////////////////GETTER/////////////////////////////

    /**
     * Retourne la position en x du joueur
     * @return la position en x du joueur
     */
    public double getX(){
        return this.x;
    }

    /**
     * Retourne la position en y du joueur
     * @return la position en y du joueur
     */
    public double getY(){
        return this.y;
    }

    /////////////////////////////SETTER/////////////////////////////

    /**
     * Modifie la position en x du joueur
     * @param x la nouvelle position en x du joueur
     */
    public void setX(double x){
        this.x = x;
    }

    /**
     * Modifie la position en y du joueur
     * @param y la nouvelle position en y du joueur
     */
    public void setY(double y){
        this.y = y;
    }

    /////////////////////////////METHODE/////////////////////////////

    /**
     * méthode draw
     */
    public void draw(){
        // TODO
    }
}
