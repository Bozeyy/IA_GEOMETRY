package com.smartdash.project.mvc.modele;

public class Camera
{
    private int x;
    private int y;
    private final int METRE = 40;

    public Camera(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void update(Joueur joueur)
    {
        this.x = joueur.x * METRE - 5 * METRE;
        if (joueur.y < 0) {
            this.y = joueur.y;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
