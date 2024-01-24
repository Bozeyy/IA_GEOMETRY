package com.smartdash.project.IA.neurones;

public class NeuroneNonVide extends Neurone{
    public NeuroneNonVide(int x, int y) {
        super(x, y);
    }

    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = !type.equals("vide");
        }
    }

    public String toString() {
        return "Neurone Non Vide" + super.toString();
    }

    @Override
    public char getType() {
        return 'w';
    }
}
