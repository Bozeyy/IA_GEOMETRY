package com.smartdash.project.IA.neurones;

public class NeuroneNonPique extends Neurone{
    public NeuroneNonPique(int x, int y) {
        super(x, y);
    }


    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = !type.equals("pique");
        }
    }

    public String toString() {
        return "Neurone Non Pique" + super.toString();
    }


    @Override
    public char getType() {
        return 'q';
    }
}
