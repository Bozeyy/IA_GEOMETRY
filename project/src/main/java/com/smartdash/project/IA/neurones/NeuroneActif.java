package com.smartdash.project.IA.neurones;

public class NeuroneActif extends Neurone{
    public NeuroneActif(int x, int y) {
        super(x, y);
    }

    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = true;
        }
    }

    public String toString() {
        return "Neurone Actif" + super.toString();
    }

    @Override
    public char getType() {
        return 'a';
    }

}
