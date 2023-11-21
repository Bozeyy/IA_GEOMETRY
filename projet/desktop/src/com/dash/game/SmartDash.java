package com.dash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SmartDash extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;

    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render () {
        // Efface l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Débute le dessin du rectangle
        shapeRenderer.begin(ShapeType.Filled);

        // Couleur du rectangle (en RGB)
        shapeRenderer.setColor(1, 0, 0, 1);

        // Dessine un rectangle aux coordonnées (x, y) avec une largeur et une hauteur
        shapeRenderer.rect(100, 100, 200, 150);

        // Termine le dessin du rectangle
        shapeRenderer.end();
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
    }
}
