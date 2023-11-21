package com.dash.game.Object;

import com.dash.game.Modele.Joueur;

public interface Objet {
    public boolean isInside(Joueur joueur);
    public void draw();
}
