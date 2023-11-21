package com.example.projetfx.modele;

import com.example.projetfx.objet.Objet;
import com.example.projetfx.objet.Terrain;
import com.example.projetfx.util.util;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.sleep;

public class Jeu extends Pane {

    public Joueur joueur;

    public Terrain map;


    public Jeu(){
        this.joueur = new Joueur(0,10);
        this.map = new Terrain();
        for (Objet o : this.map.maps){
            this.getChildren().add((Rectangle) o);
        }
        this.getChildren().add(this.joueur);
    }

    /**
     * Méthode lancer
     */
    public void lancer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastFrameTime = System.nanoTime();

            @Override
            public void handle(long now) {
                long nowT = System.nanoTime();
                double deltaTime = (nowT - lastFrameTime)/1E9;
                this.lastFrameTime = nowT;

                update(deltaTime);
            }
        };

        animationTimer.start();
    }

    /**
     * Méthode update
     */
    public void update(double delta) {
        this.joueur.update(delta);
        System.out.println("x : " + this.joueur.x + " y : " + this.joueur.y);
    }
}
