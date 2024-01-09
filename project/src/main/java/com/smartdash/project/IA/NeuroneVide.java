package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Vide;

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

}
