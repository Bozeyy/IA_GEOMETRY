package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NeatAmelioration extends Neat
{
    public NeatAmelioration()
    {
        super();
    }

    public NeatAmelioration(int maxGenerations)
    {
        super();
        this.maxGenerations = maxGenerations;
    }

    @Override
    public void lancerApprentissage() throws Exception {
        //Début de l'enregistrement, on récupère le chemin du dossier
        //String pathname = Enregistrement.debutEnregistrement();

        // initialisation de la population
        int nbIndividu = 1000;
        List<Joueur> population = new ArrayList<>(nbIndividu);

        // initialisation de la population pour la generation initiale
        for (int i = 0; i < nbIndividu; i++) {
            Reseau r = ReseauFabrique.genererReseau();
            population.add(new Joueur(r));
        }

        int generation = 0;
        int nbParents = 32;
        List<Joueur> parents;
        List<Joueur> enfants = new ArrayList<>();

        List<Terrain> listesTerrains = new ArrayList<>();

        for(int i = 0; i<5; i++){
            Terrain terrainAleatoire = new Terrain(3);
            listesTerrains.add(terrainAleatoire);
        }

        while (generation < maxGenerations) {

            // calcul du score des individus
            for (Joueur j : population) {
                for (Terrain terrain : listesTerrains)
                {
                    evaluerPerformance(j,terrain);
                }
            }

            // enregistrement de la population
            //Enregistrement.generationEnregistrement(pathname, generation, population);

            // On calcule la moyenne des 10 meilleurs
            double moyenneGeneration = Statistique.calculerMoyenne10MeilleursScoreMoyen(population);
            System.out.println("Moyenne de la population " + generation + " : " + moyenneGeneration);

            // On sélectionne les parents
            parents = selectionnerParents(population);

            for (int i =0; i < nbParents; i++) {
                for (int j = i+1; j < nbParents; j++) {
                    Joueur parent1 = parents.get(i);
                    Joueur parent2 = parents.get(j);

                    // 2 enfants par couple
                    Joueur enfant1 = croisement(parent1, parent2);
                    mutation(enfant1);
                    Joueur enfant2 = croisement(parent1, parent2);
                    mutation(enfant2);

                    enfants.add(enfant1);
                    enfants.add(enfant2);
                }
            }

            int indiceParent = 0;
            System.out.println("Le meilleur parent : " + parents.get(0).getScoreMoyen());
            while (enfants.size() < nbIndividu) {
                enfants.add(parents.get(indiceParent));
                indiceParent++;
            }

            population = enfants;
            enfants = new ArrayList<>();

            generation++;

            System.out.println("-------");
        }
        System.out.println("fini");
    }

    @Override
    public void evaluerPerformance(Joueur joueur, Terrain terrain)
    {
        joueur.setMap(terrain);
        Jeu jeu = new Jeu(joueur, terrain);
        jeu.evaluationPlusieurs();
    }


    @Override
    public List<Joueur> selectionnerParents(List<Joueur> population){
        List<Joueur> nouvellePopulation = new ArrayList<>();

        List<Joueur> copiePopulation = new ArrayList<>(population);
        // On tri la population
        copiePopulation.sort(Comparator.comparingDouble(Joueur::getScoreMoyen).reversed());

        if (population.size() != 1000)
        {
            throw new IllegalStateException("La taille de la population n'est pas de 1000");
        }

        // On initialise
        List<Joueur> huitMeilleurs = new ArrayList<>(copiePopulation.subList(0, 8));

        List<Joueur> partie2 = prendreAleatoire(new ArrayList<>(copiePopulation.subList(8,57)), 12);
        List<Joueur> partie3 = prendreAleatoire(new ArrayList<>(copiePopulation.subList(57,407)), 7);
        List<Joueur> partie4 = prendreAleatoire(new ArrayList<>(copiePopulation.subList(407,907)), 3);
        List<Joueur> partie5 = prendreAleatoire(new ArrayList<>(copiePopulation.subList(907,999)), 2);

        nouvellePopulation.addAll(huitMeilleurs);
        nouvellePopulation.addAll(partie2);
        nouvellePopulation.addAll(partie3);
        nouvellePopulation.addAll(partie4);
        nouvellePopulation.addAll(partie5);

        return nouvellePopulation;
    }

}
