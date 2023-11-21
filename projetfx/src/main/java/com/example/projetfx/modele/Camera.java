package com.example.projetfx.modele;

import com.example.projetfx.util.util;

public class Camera {

    public double x;
    public double y;

    public Camera(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * méthode update positionne la camera sur le joueur
     * si le joueur est au dessus de 40*util.METRE en y la camera ne bouge pas
     * @param joueur le joueur
     */
    public void update(Joueur joueur){
        this.x = joueur.getPoseX();
        if(joueur.getPoseY() < 0){
            this.y = joueur.getPoseY();
        }
    }

}
