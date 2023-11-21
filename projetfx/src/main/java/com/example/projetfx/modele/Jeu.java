package com.example.projetfx.modele;

import com.example.projetfx.objet.Terrain;
import com.example.projetfx.util.util;

public class Jeu {

    public Joueur joueur;

    public Terrain map;

    public long lastFrameTime;

    public Jeu(){
        this.joueur = new Joueur(0,0);
        this.map = new Terrain();
        this.lastFrameTime = System.nanoTime();
    }

    /**
     * méthode lancer
     */
    public void lancer(){
        while (true){
            this.update();
        }
    }

    /**
     * méthode update
     */
    public void update(){
        long time = System.nanoTime();
        double delta = (time - this.lastFrameTime) / 1000000000.0;
        this.lastFrameTime = time;
        System.out.println("Update du jeu");
    }
}
