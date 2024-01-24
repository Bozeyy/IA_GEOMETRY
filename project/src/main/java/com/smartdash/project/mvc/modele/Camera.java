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
        
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
