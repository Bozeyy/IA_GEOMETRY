package com.smartdash.project.IA.neurones;

public abstract class Neurone implements Cloneable {
    protected int x;
    protected int y;

    public boolean active;

    public Neurone(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    public Neurone(Neurone n) {
        this.x = n.x;
        this.y = n.y;
        this.active = n.active;
    }

    /**
     * permet de retourner l'etat du neurone (activé ou désactivé)
     * @return
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * permet de mettre a jour l etat du neurone à partir des donnés d une case (l'active ou le désactive si la case correspond)
     * @param x
     * @param y
     * @param type
     */
    public abstract void setActive(int x, int y, String type);

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Neurone) {
//            Neurone neurone = (Neurone) obj;
//            return this.x == neurone.x && this.y == neurone.y;
//        }
//        return false;
//    }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + ", active=" + active + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char getType();


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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
