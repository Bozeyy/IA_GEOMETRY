package com.smartdash.project.demo;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.apprentissage.Neat;
import com.smartdash.project.apprentissage.NeatAmelioration;
import com.smartdash.project.modele.Joueur;

public class Main
{
    public static void main(String[] args) {
        Neat neat = new Neat();

        try {
            neat.lancerApprentissage();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
