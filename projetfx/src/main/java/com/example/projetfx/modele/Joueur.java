package com.example.projetfx.modele;

import com.example.projetfx.objet.Block;
import com.example.projetfx.objet.Objet;
import com.example.projetfx.util.util;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public void update(double delta, ArrayList<Objet> map){
        if (map.size() == 0) {
            this.setFill(Color.BLACK);
            this.vitesseY = util.GRAVITE;
            this.vitesseX = util.VITESSE;
        }
        for (Objet objet : map) {
            if (objet instanceof Block) {
                Block block = (Block) objet;
                if (block.tomberSurBlock(this, this.vitesseY)) {
                    this.setFill(Color.BLUE);
                    ((Block) objet).setFill(Color.BLUE);
                    System.out.println("tombe sur block");
                    this.vitesseY = 0;
                } else {
                    ((Block) objet).setFill(Color.ORANGE);
                }
            }
        }
        this.y += this.vitesseY * delta;
        this.setY(this.y);
        this.x += this.vitesseX * delta;
        this.setX(this.x);
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
