package com.smartdash.project.modele;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;

import java.util.ArrayList;

public class Terrain {
    private ArrayList<Objet> map;

    public Terrain(int longueur)
    {

    }

    public Terrain()
    {
        // Générations des blocs de la map
        for(int i=-10; i<100; i++)
        {
            this.map.add((new Bloc(i,11)));
            this.map.add((new Bloc(i,12)));
            this.map.add((new Bloc(i,13)));
            this.map.add((new Bloc(i,14)));
        }
    }

}
