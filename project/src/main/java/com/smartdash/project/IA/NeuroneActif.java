package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Objet;

import java.util.List;

public class NeuroneActif extends Neurone{
    public NeuroneActif(int x, int y) {
        super(x, y);
    }

    public boolean isActive(List<Objet> objets) {
        this.active = true;
        return true;
    }
}
