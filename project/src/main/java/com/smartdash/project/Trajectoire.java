package com.smartdash.project;

import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.modele.objet.ObjetTrajectoire;

import java.util.List;

public class Trajectoire
{
    private int x;
    private int y;
    private int vy;
    private Terrain terrain;
    private List<ObjetTrajectoire> listesTrajectoire;

    public Trajectoire(Terrain terrain)
    {
        this.x = 0;
        this.y = 0;
        this.vy = 0;
        this.terrain = terrain;
    }

    public List<ObjetTrajectoire> jouer()
    {
        return this.listesTrajectoire;
    }

    public void updateTrajectoire()
    {
        // Boolean qui vérifie si on est sur le sol
        boolean surSol = verifierSurSol();

        // On pioche un random double

        // Si on est sur le sol
        // On pioche alors un random double
        //TODO
        // Soit on a la proba de sauter
        // Si on n'est pas sur le sol
        // Soit on tombe sur sauter
        // Soit on tombe sur tomber

        // On avance dans tous les cas

        // On créer ensuite l'objet trajectoire que l'on ajoute dans notre liste, si il a saute on ajoute le boolean
    }

    private boolean verifierSurSol() {
        return true;
    }
}
