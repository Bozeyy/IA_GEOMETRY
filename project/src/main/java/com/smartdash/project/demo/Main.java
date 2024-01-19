package com.smartdash.project.demo;

import com.smartdash.project.apprentissage.Neat;
import com.smartdash.project.apprentissage.NeatAmelioration;
import com.smartdash.project.modele.Terrain;

public class Main
{
    public static void main(String[] args) {
        Neat neat = new Neat(100, new Terrain(3));
        NeatAmelioration neatAmelioration = new NeatAmelioration(100, 5);

        try {
            neatAmelioration.lancerApprentissage();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
