package com.smartdash.project.IA;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Vide;

import java.util.List;
import java.util.Optional;

public class NeuroneVide extends Neurone{
    public NeuroneVide(int x, int y) {
        super(x, y);
    }

    public boolean isActive(List<Objet> objets) {
        Optional<Objet> obj = objets.stream().filter(objet -> (objet.getX() == this.x  && objet.getY() == this.y)).findFirst();
        if (obj.isPresent()) {
            this.active = obj.get() instanceof Vide;
        } else {
            throw new Error("Coordonnées du neuronne non trouvé dans la liste des cases envoyé : (" + this.x + ", " + this.y + ")");
        }
        return this.active;
    }
}
