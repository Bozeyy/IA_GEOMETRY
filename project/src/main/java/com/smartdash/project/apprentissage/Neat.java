package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Neat {

    public static void main(String[] args) {
        Neurone n1 = new NeuroneBloc(1, 0);
        Neurone n2 = new NeuroneBloc(-4, 3);
        Neurone n3 = new NeuroneBloc(4, 2);

        Neurone n4 = new NeuronePique(1, 0);
        Neurone n5 = new NeuronePique(-4, 3);
        Neurone n6 = new NeuronePique(4, 2);

        Neurone n7 = new NeuroneVide(1, 0);
        Neurone n8 = new NeuroneVide(-4, 3);
        Neurone n9 = new NeuroneVide(4, 2);

        Reseau r1 = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3}), ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3}), ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3})});
        Reseau r2 = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n4, n5, n6}), ModuleFabrique.genererModule(new Neurone[]{n4, n5, n6}), ModuleFabrique.genererModule(new Neurone[]{n4, n5, n6})});

        Joueur j1 = new Joueur(r1);
        Joueur j2 = new Joueur(r2);
        Joueur j3 = croisement(j1, j2);

        System.out.println(j3.getReseau());
        lancerApprentissage();
    }

    public static void lancerApprentissage() {
        int nbIndividu = 1000;
        List<Joueur> population = new ArrayList<Joueur>(nbIndividu);

        // initialisation de la population pour la generation initiale
        for (int i = 0; i < nbIndividu; i++) {
            Reseau r = ReseauFabrique.genererReseau();
            population.set(i, new Joueur(r));
        }

        int generation = 0;
        int maxGeneration = 100;
        int nbParents = 32;
        List<Joueur> parents = new ArrayList<>(nbParents);
        List<Joueur> enfants = new ArrayList<>(nbIndividu);

        while (generation < maxGeneration) {
            Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain1.txt");

            // calcul du score des individus
            for (Joueur j : population) {
                evaluerPerformance(j, terrain);
            }

            parents = selectionnerParents(population);
            int indiceEnfant = 0;

            for (int i =0; i < nbParents - 1; i++) {
                for (int j = i+1; j < nbParents; j++) {

                    Joueur parent1 = parents.get(i);
                    Joueur parent2 = parents.get(j);

                    // 2 enfants par couple
                    Joueur enfant1 = croisement(parent1, parent2);
                    Joueur enfant2 = croisement(parent1, parent2);

                    enfants.add(enfant1);
                    enfants.add(enfant2);
                    indiceEnfant += 2;
                }
            }

            while (indiceEnfant < nbIndividu) {
                enfants.add(parents.get(nbIndividu - indiceEnfant+1));
                indiceEnfant++;
            }

            population = enfants;
            System.out.println("Moyenne de la population " + generation + " : " + Statistique.calculerMoyenneDesScores(population));

        }
        System.out.println("fini");

    }

    /**
     * Méthode qui permet d'évaluer les performances d'un réseau
     * @param joueur joueur qu'on essaye d'évaluer
     * @return le score
     */
    public static void evaluerPerformance(Joueur joueur, Terrain terrain)
    {
        int score = 0;
        Jeu jeu = new Jeu(joueur, terrain);
        jeu.evaluation();

    }

    public static Joueur croisement(Joueur parent1, Joueur parent2) {
        Reseau enfant = new Reseau();
        Module mod;

        for (int i = 0; i < Constantes.NB_MODULES_PAR_RESEAU; i++) {
            boolean boolAleatoire = new Random().nextBoolean();

            if (boolAleatoire) {
                mod = parent1.getReseau().getModules().get(i).clone();
                enfant.addModule(mod);
            } else {
                mod = parent2.getReseau().getModules().get(i).clone();
                enfant.addModule(mod);
            }
        }
        return new Joueur(enfant);
    }

    public static void mutation(Joueur joueur) {
        Reseau res = joueur.getReseau();

        int indiceModuleAleatoire = new Random().nextInt(res.getModules().size());

        int indiceNeuroneAleatoire = new Random().nextInt(res.getModules().get(indiceModuleAleatoire).getNeurones().size());

        List<Module> modules = res.getModules();
        List<Neurone> neurones = modules.get(indiceModuleAleatoire).getNeurones();
        Neurone neurone = neurones.get(indiceNeuroneAleatoire);

        try {
            neurone = changerTypeNeurone(neurone);
            neurones.set(indiceNeuroneAleatoire, neurone);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static Neurone changerTypeNeurone(Neurone neurone) throws Exception {
        Neurone res = neurone;
        int type;

        while (neurone.getClass() == res.getClass()) {

            type = new Random().nextInt(7);
            res = switch (type) {
                case 0 -> new NeuroneBloc(neurone.getX(), neurone.getY());
                case 1 -> new NeuronePique(neurone.getX(), neurone.getY());
                case 2 -> new NeuroneVide(neurone.getX(), neurone.getY());
                case 3 -> new NeuroneNonPique(neurone.getX(), neurone.getY());
                case 4 -> new NeuroneNonBloc(neurone.getX(), neurone.getY());
                case 5 -> new NeuroneNonVide(neurone.getX(), neurone.getY());
                case 6 -> new NeuroneActif(neurone.getX(), neurone.getY());
                default -> throw new Exception("Erreur indice du type du neurone lors de la mutation");
            };
        }

        return res;
    }

    /**
     * Méthode qui permet de sélectionner les parents
     * @param population population
     * @return retourne la liste des parents
     */
    public static List<Joueur> selectionnerParents(List<Joueur> population)
    {
        List<Joueur> nouvellePopulation = new ArrayList<>();
        // On tri la population
        Collections.sort(population, Comparator.comparingInt(Joueur::getScore));

        // On intialise
        List<Joueur> huitMeilleurs = new ArrayList<>(population.subList(0, 7));

        List<Joueur> partie2 = prendreAleatoire(population.subList(8,57), 12);
        List<Joueur> partie3 = prendreAleatoire(population.subList(58,407), 7);
        List<Joueur> partie4 = prendreAleatoire(population.subList(408,907), 3);
        List<Joueur> partie5 = prendreAleatoire(population.subList(908,1000), 2);

        nouvellePopulation.addAll(huitMeilleurs);
        nouvellePopulation.addAll(partie2);
        nouvellePopulation.addAll(partie3);
        nouvellePopulation.addAll(partie4);
        nouvellePopulation.addAll(partie5);

        return nouvellePopulation;
    }

    private static List<Joueur> prendreAleatoire(List<Joueur> joueurs, int i) {
        // TODO
        return  null;
    }

}
