package com.smartdash.project.IA.neurones;

public class NeuroneVide extends Neurone{
    public NeuroneVide(int x, int y) {
        super(x, y);
    }


    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = type.equals("vide");
        }
    }

    public String toString() {
        return "Neurone Vide" + super.toString();
    }


    @Override
    public char getType() {
        return 'v';
    }
}
