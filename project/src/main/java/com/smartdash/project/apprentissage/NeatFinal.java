package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.IA.ReseauFabrique;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.terrainAleatoire.GenerateurTerrainAleatoire;

import java.util.ArrayList;
import java.util.List;

public class NeatFinal extends NeatVariation
{
    /**
     * Constructeur avec un max de génération et un nb de terrain à générer
     *
     * @param maxGenerations max de génération
     * @param nbTerrains     nombre de terrains max
     */
    public NeatFinal(int maxGenerations, int nbTerrains) {
        super(maxGenerations, nbTerrains);
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
        double moyenneTest;

        int generation = 0;
        int nbParents = 32;

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

            // on effectue des tests toute les 50 generations
            if (generation % 10 == 0) {
                // ON copie la population
                List<Joueur> populationCopie = new ArrayList<>();
                for(Joueur joueur : population) {
                    populationCopie.add(new Joueur(joueur.getReseau().clone()));
                }

                // On fait jouer alors tout les joueurs sur 10 terrains aléatoires
                for(Joueur joueur : populationCopie)
                {
                    moyenneScoreDonneeTest(joueur);
                }

                // On en calcule ensuite les moyennes
                stat.addMoyennesTests(populationCopie);

                moyenneTest = stat.calculerMoyenne10Meilleurs(populationCopie);
                System.out.println("Moyenne des test de la population : " + generation + " : " + moyenneTest);
            }

            // On sélectionne les parents
            parents = selectionnerParents(population);

            for (int i =0; i < nbParents; i++) {
                for (int j = i+1; j < nbParents; j++) {
                    Joueur parent1 = parents.get(i);
                    Joueur parent2 = parents.get(j);

                    // 2 enfants par couple
                    Joueur enfant1 = croisement(parent1, parent2);
                        mutationAll(enfant1);

                    Joueur enfant2 = croisement(parent1, parent2);
                    mutationAll(enfant2);

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
     * Méthode qui permet de calculer la moyenne sur 100 terrains des joueurs sur des terrains aléatoires
     * @param joueur joueur qui joue sur les 100 terrains.
     * @throws Exception exception pour la génération de terrain
     */
    public void moyenneScoreDonneeTest(Joueur joueur) throws Exception {
        List<Terrain> listesTerrain = new ArrayList<>();
        GenerateurTerrainAleatoire generateurTerrainAleatoire = new GenerateurTerrainAleatoire();


        for(int i = 0; i<100; i++)
        {
            listesTerrain.add(generateurTerrainAleatoire.genererTerrainAleatoire());
        }

        double scoreMoyenne = 0;
        for (Terrain terrain : listesTerrain)
        {
            evaluerPerformance(joueur, terrain);
            scoreMoyenne += joueur.getScorePartie();
        }
        joueur.setScorePartie((scoreMoyenne/ nbTerrains));
    }
}
