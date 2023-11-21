package com.example.projetfx.modele;

import com.example.projetfx.objet.Terrain;
import com.example.projetfx.util.util;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import static java.lang.Thread.sleep;

public class Jeu extends Pane {

    public Joueur joueur;

    public Terrain map;

    public long lastFrameTime;

    public Jeu(){
        this.joueur = new Joueur(0,0);
        this.map = new Terrain();
        this.lastFrameTime = System.nanoTime();
        this.getChildren().add(this.joueur);
    }

    /**
     * Méthode lancer
     */
    public void lancer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastFrameTime = 0;

            @Override
            public void handle(long now) {
                update((now - lastFrameTime) / 1e9);
                lastFrameTime = now;
            }
        };

        animationTimer.start();
    }

    /**
     * Méthode update
     */
    public void update(double delta) {
        System.out.println("delat = " + delta);
    }
}
