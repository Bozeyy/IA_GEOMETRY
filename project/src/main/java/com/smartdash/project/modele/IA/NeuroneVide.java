package com.smartdash.project.modele.IA;

public class NeuroneVide extends Neurone{
    public NeuroneVide(int x, int y) {
        super(x, y);
    }

    public boolean isActive(Object object) {
        return true;
    }
}
