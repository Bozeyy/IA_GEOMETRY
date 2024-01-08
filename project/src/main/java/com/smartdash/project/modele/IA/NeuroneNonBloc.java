package com.smartdash.project.modele.IA;

public class NeuroneNonBloc extends Neurone{
    public NeuroneNonBloc(int x, int y) {
        super(x, y);
    }

    public boolean isActive(Object object) {
        return true;
    }
}
