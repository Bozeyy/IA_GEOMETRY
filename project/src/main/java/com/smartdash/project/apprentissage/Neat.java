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

    private static final Random random = new Random();
    private static final int nbIndividu = 1000;
    private static final int maxGeneration = 100;


    public static void main(String[] args) throws Exception {
//        Neurone n1 = new NeuroneBloc(3, 5);
//        Neurone n2 = new NeuroneActif(2, 1);
//        Neurone n3 = new NeuronePique(0, 0);
//
//        Module m1 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
//        Module m2 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
//        Module m3 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
//
//        Module m4 = ModuleFabrique.genererModule(new Neurone[]{n3, n1, n2});
//        Module m5 = ModuleFabrique.genererModule(new Neurone[]{n3, n1, n2});
//        Module m6 = ModuleFabrique.genererModule(new Neurone[]{n3, n1, n2});
//
//        Reseau r1 = ReseauFabrique.genererReseau(new Module[]{m1,m2,m3});
//        Reseau r2 = ReseauFabrique.genererReseau(new Module[]{m4,m5,m6});
//
//        Joueur j1 = new Joueur(r1);
//        Joueur j2 = new Joueur(r2);
//
//        System.out.println(r1);
//        mutation(j1);
//        System.out.println(j1.getReseau());

         lancerApprentissage();
    }

    public static void lancerApprentissage() throws Exception {
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

        while (generation < maxGeneration) {
            Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain1.txt");

            // calcul du score des individus
            for (Joueur j : population) {
                evaluerPerformance(j, terrain);
            }

            double moyenneGeneration = Statistique.calculerMoyenne10Meilleurs(population);
            System.out.println("Moyenne de la population " + generation + " : " + moyenneGeneration);


            parents = selectionnerParents(population);
            int indiceEnfant = 0;

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
                    indiceEnfant += 2;
                }
            }

            System.out.println("-------");
            int indiceParent = 0;
            while (enfants.size() < nbIndividu) {
                enfants.add(parents.get(indiceParent));
                indiceParent++;
            }

            population = enfants;
            enfants = new ArrayList<>();

            generation++;
        }
        System.out.println("fini");

    }

    /**
     * Méthode qui permet d'évaluer les performances d'un réseau
     * @param joueur joueur qu'on essaye d'évaluer
     */
    public static void evaluerPerformance(Joueur joueur, Terrain terrain)
    {
        int score = 0;
        joueur.setMap(terrain);
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

        int probaMutation;
        Neurone n;
        int index;

        List<Module> modules = res.getModules();
        try {
            for (Module module : modules) {
                for (Neurone neurone : module.getNeurones()) {
                    probaMutation = random.nextInt(res.getNbNeurone());
                    if (probaMutation == 0) {
                        n = changerTypeNeurone(neurone);
                        index = module.getNeurones().indexOf(neurone);
                        module.getNeurones().set(index, n);
                    }
                }
            }
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
    public static List<Joueur> selectionnerParents(List<Joueur> population) throws Exception {
        List<Joueur> nouvellePopulation = new ArrayList<>();

        List<Joueur> copiePopulation = new ArrayList<>(population);
        // On tri la population
        copiePopulation.sort(Comparator.comparingDouble(Joueur::getScore).reversed());

        if (population.size() != 1000)
        {
            throw new Exception("La taille de la population n'est pas de 1000");
        }

        // On intialise
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

    /**
     * Méthode qui permet de sélectionner aléatoirement une partie de la population
     * @param population liste de joueur
     * @param i nombre de joueurs à sélectionern
     * @return retourne une partie de la population aléatoire
     */
    public static List<Joueur> prendreAleatoire(List<Joueur> population, int i) {
        List<Joueur> individuAleatoire = new ArrayList<>();
        int j = 0;

        // Tant que i est plus petit que i
        while (j<i)
        {
            // On récupère un joueur aléatoirement dans la liste de population que l'on va envoyer dans la nouvelle liste
            int nbAleatoire = random.nextInt(population.size());

            individuAleatoire.add(population.remove(nbAleatoire));
            j++;
        }

        // On retourne la liste d'individu aléatoire
        return  individuAleatoire;
    }

}
