package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Neat {

    public static void lancerApprentissage()
    {
        // TODO
    }

    /**
     * Méthode qui permet d'évaluer les performances d'un réseau
     * @param joueur joueur qu'on essaye d'évaluer
     * @return le score
     */
    public static int evaluerPerformance(Joueur joueur, Terrain terrain)
    {
        int score = 0;
        Jeu jeu = new Jeu(joueur, terrain);
        score = jeu.evaluation();

        return score;
    }

    public static Reseau croisement(Reseau parent1, Reseau parent2)
    {
        // TODO
        return null;
    }

    public static Reseau mutation(Reseau reseau)
    {
        // TODO

        return null;
    }

    public static List<Joueur> selectionnerParents(List<Joueur> population)
    {
        // On tri la population
        Collections.sort(population, Comparator.comparingInt(Joueur::getScore));

        // On intialise
        List<Joueur> parents = new ArrayList<>();



        return null;
    }
}
