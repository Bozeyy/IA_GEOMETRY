package com.dash.game.Modele;
import com.dash.game.Object.*;

public class Jeu {

    public Joueur joueur;

    public Map map;

    public long lastFrameTime;

    public Jeu(){
        this.joueur = new Joueur(0,0);
        this.map = new Map();
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
