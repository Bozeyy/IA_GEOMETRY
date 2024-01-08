package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;

import java.util.List;
import java.util.Optional;

public class NeuroneNonBloc extends Neurone{
    public NeuroneNonBloc(int x, int y) {
        super(x, y);
    }

    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = !type.equals("bloc");
        }
    }
}
