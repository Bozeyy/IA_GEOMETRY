package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.*;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NeatAmelioration extends NeatPosition
{

    public static void main(String[] args) {
        NeatAmelioration neatAmelioration = new NeatAmelioration(150,1);

        try {
            neatAmelioration.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected int nbTerrains;


    /**
     * Constructeur avec un max de génération et un nb de terrain à générer
     * @param maxGenerations max de génération
     * @param nbTerrains nombre de terrains max
     */
    public NeatAmelioration(int maxGenerations, int nbTerrains)
    {
        super();
        this.maxGenerations = maxGenerations;
        this.nbTerrains = nbTerrains;
    }


    /**
     * Méthode qui permet de lancer l'apprentissage de l'IA grâce à NEAT
     * @throws Exception exception
     */
    @Override
    public void lancerApprentissage() throws Exception {
        //Début de l'enregistrement, on récupère le chemin du dossier
        String pathname = Enregistrement.debutEnregistrement();

        // initialisation de la population
        int nbIndividu = 1000;
        List<Joueur> population = new ArrayList<>(nbIndividu);

        // initialisation de la population pour la generation initiale
        for (int i = 0; i < nbIndividu; i++) {
            Reseau r = ReseauFabrique.genererReseauPosAleatoire();
            population.add(new Joueur(r));
        }

        // Moyenne de la génération (score moyen)
        double moyenneGeneration;

        int generation = 0;
        int nbParents = 32;

        double meilleurScore = 0;
        double meilleurScoreGen;
        List<Joueur> parents;
        List<Joueur> enfants = new ArrayList<>();
        Statistique stat = new Statistique();


        List<Terrain> listesTerrain = new ArrayList<>();
        for(int i = 1; i<=nbTerrains ; i++)
        {
            Terrain terrainAleatoire = new Terrain("src/main/resources/apprentissage/terrain"+i+".txt");
            listesTerrain.add(terrainAleatoire);
        }

        while (generation < maxGenerations) {
            // calcul du score des individus
            for (Joueur joueur : population) {
                moyenneScore(joueur, listesTerrain);
            }

            // enregistrement de la population
            Enregistrement.generationEnregistrement(pathname, generation, population);
            stat.addMoyennes(population);


            // On calcule la moyenne des 10 meilleurs
            moyenneGeneration = stat.calculerMoyenne10Meilleurs(population);
            System.out.println("Moyenne des 10 premiers de la population " + generation + " : " + moyenneGeneration);

            // On sélectionne les parents
            parents = selectionnerParents(population);

            for (int i =0; i < nbParents; i++) {
                for (int j = i+1; j < nbParents; j++) {
                    Joueur parent1 = parents.get(i);
                    Joueur parent2 = parents.get(j);

                    // 2 enfants par couple
                    Joueur enfant1 = croisement(parent1, parent2);
                    mutation(enfant1);
                    mutationPosition(enfant1);

                    Joueur enfant2 = croisement(parent1, parent2);
                    mutation(enfant2);
                    mutationPosition(enfant2);


                    enfants.add(enfant1);
                    enfants.add(enfant2);
                }
            }

            int indiceParent = 0;
            while (enfants.size() < nbIndividu) {
                Joueur parent = parents.get(indiceParent);
                Joueur copieParent = new Joueur(parent.getReseau().clone());
                enfants.add(copieParent);
                indiceParent++;
            }

            population = enfants;
            enfants = new ArrayList<>();

            generation++;

            System.out.println("-------");
        }
        stat.genererPDF(pathname);
        System.out.println("fini");
    }

    /**
     * Méthode qui permet de récupérer le score du meilleur individu
     * @param population liste de Joueur
     * @return retourne le score
     */
    protected double recupererScoreMeilleurIndividu(List<Joueur> population) {
        List<Joueur> copiePopulation = new ArrayList<>(population);
        // On tri la population
        copiePopulation.sort(Comparator.comparingDouble(Joueur::getScorePartie).reversed());
        System.out.println(copiePopulation.get(0).getReseau());

        return copiePopulation.get(0).getScorePartie();
    }

    /**
     * Méthode qui permet de réaliser la moyenne des scores
     * @param joueur joueur
     * @param listesTerrain liste des terrains auxquels il doit jouer
     */
    protected void moyenneScore(Joueur joueur, List<Terrain> listesTerrain) {
        double scoreMoyenne = 0;
        for (Terrain terrain : listesTerrain)
        {
            evaluerPerformance(joueur, terrain);
            scoreMoyenne += joueur.getScorePartie();
        }
        joueur.setScorePartie((scoreMoyenne/ nbTerrains));
    }
}
