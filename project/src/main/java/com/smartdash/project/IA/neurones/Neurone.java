package com.smartdash.project.IA.neurones;

public abstract class Neurone implements Cloneable {
    protected int x;
    protected int y;
    public boolean active;


    /**
     * Constructeur neurone
     * @param x position x
     * @param y position y
     */
    public Neurone(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    /**
     * Constructeur par copie
     * @param n neurone
     */
    public Neurone(Neurone n) {
        this.x = n.x;
        this.y = n.y;
        this.active = n.active;
    }


    /**
     * Permet de retourner l'état du neurone (activé ou désactivé)
     * @return l'état
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Permet de mettre à jour l'état du neurone à partir des données d'une case (l'active ou le désactive si la case correspond)
     * @param x position x
     * @param y position y
     * @param type type du neurone
     */
    public abstract void setActive(int x, int y, String type);

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + ", active=" + active + '}';
    }

    @Override
    public Neurone clone() {
        try {
            Neurone clone = (Neurone) super.clone();
            clone.active = false;
            clone.x = this.x;
            clone.y = this.y;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char getType();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
