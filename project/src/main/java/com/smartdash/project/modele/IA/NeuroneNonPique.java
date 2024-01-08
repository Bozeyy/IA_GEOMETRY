package com.smartdash.project.modele.IA;

public class NeuroneNonPique extends Neurone{
    public NeuroneNonPique(int x, int y) {
        super(x, y);
    }

    public boolean isActive(Object object) {
        return true;
    }
}
