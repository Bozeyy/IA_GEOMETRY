package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;

import java.util.ArrayList;
import java.util.List;

public class NeatPosition extends Neat{

    public static void main(String[] args) throws Exception {
        NeatPosition np = new NeatPosition();
        np.lancerApprentissage();
    }

    public void lancerApprentissage() throws Exception {
        //Début de l'enregistrement, on récupère le chemin du dossier
        String pathname = Enregistrement.debutEnregistrement();

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
        Statistique stat = new Statistique();

        Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain9.txt");

        while (generation < maxGenerations) {
            // calcul du score des individus
            for (Joueur j : population) {
                evaluerPerformance(j, terrain);
            }

            // enregistrement de la population
            Enregistrement.generationEnregistrement(pathname, generation, population);
            stat.addGeneration(population);

            // On calcule la moyenne des 10 meilleurs
            double moyenneGeneration = Statistique.calculerMoyenne10Meilleurs(population);
            System.out.println("Moyenne de la population " + generation + " : " + moyenneGeneration);

            // On sélectionne les parents
            parents = selectionnerParents(population);

            for (int i =0; i < nbParents; i++) {
                for (int j = i+1; j < nbParents; j++) {
                    Joueur parent1 = parents.get(i);
                    Joueur parent2 = parents.get(j);

                    // 2 enfants par couple
                    Joueur enfant1 = croisement(parent1, parent2);
                    mutationParModule(enfant1);
                    mutationPosition(enfant1);
                    Joueur enfant2 = croisement(parent1, parent2);
                    mutationParModule(enfant2);
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
        stat.genererPDF();
        System.out.println("fini");
    }

    public void mutationPosition(Joueur joueur) {

            Reseau res = joueur.getReseau();

            int probaMutation;
            int nouvelleValeurX;
            int nouvelleValeurY;

            List<Module> modules = res.getModules();
            try {
                for (Module module : modules) {
                    for (Neurone neurone : module.getNeurones()) {
                        probaMutation = random.nextInt(res.getNbNeurone());
                        if (probaMutation == 0) {
                            // Mutation des coordonnées du neurone
                            nouvelleValeurX = random.nextInt((Constantes.X_NEURONES_MAX - Constantes.X_NEURONES_MIN) + 1) + Constantes.X_NEURONES_MIN;
                            nouvelleValeurY = random.nextInt((Constantes.Y_NEURONES_MAX - Constantes.Y_NEURONES_MIN) + 1) + Constantes.Y_NEURONES_MIN;
                            neurone.setX(nouvelleValeurX);
                            neurone.setY(nouvelleValeurY);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
}
