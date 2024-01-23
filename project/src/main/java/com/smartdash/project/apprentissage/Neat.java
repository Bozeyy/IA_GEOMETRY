package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Jeu;

import java.util.*;

public class Neat{
    protected final Random random = new Random();
    protected int maxGenerations;
    protected Terrain terrain;

    public Neat()
    {
        this.maxGenerations = 150;
        terrain = new Terrain(3);
    }

    public Neat(int maxGenerations)
    {
        this.maxGenerations = maxGenerations;
    }

    public Neat(int maxGenerations, Terrain terrain)
    {
        this.maxGenerations = maxGenerations;
        this.terrain = terrain;
    }

    public static void main(String[] args) throws Exception {
        Neat n = new Neat();
        n.lancerApprentissage();
    }

    /**
     * Méthode qui permet de lancer l'apprentissage de l'IA grâce à NEAT
     * @throws Exception exception
     */
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

        Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain2.txt");

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
                    Joueur enfant2 = croisement(parent1, parent2);
                    mutationParModule(enfant2);

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

    /**
     * Méthode qui permet d'évaluer les performances d'un réseau
     * @param joueur joueur qu'on essaye d'évaluer
     */
    public void evaluerPerformance(Joueur joueur, Terrain terrain)
    {
        joueur.setMap(terrain);
        Jeu jeu = new Jeu(joueur, terrain);
        jeu.evaluationUnJoueur();
    }

    /**
     * Méthode qui permet de réaliser le croisement entre deux joueurs parents
     * @param parent1 parent 1
     * @param parent2 parent 2
     * @return retourne l'enfant
     */
    public Joueur croisement(Joueur parent1, Joueur parent2) {
        Reseau enfant = new Reseau();
        Module mod;
        boolean arret = false;


        while (!arret) {
            arret = true;

            for (int i = 0; i < Constantes.NB_MODULES_PAR_RESEAU; i++) {
                boolean boolAleatoire = random.nextBoolean();

                if (boolAleatoire) {
                    mod = parent1.getReseau().getModules().get(i).clone();
                    enfant.addModule(mod);
                } else {
                    mod = parent2.getReseau().getModules().get(i).clone();
                    enfant.addModule(mod);
                }
            }
            if (Objects.equals(enfant.toString(), parent1.getReseau().toString()) || Objects.equals(enfant.toString(), parent2.getReseau().toString())) {
                arret = false;
                enfant = new Reseau();
            }
        }
        return new Joueur(enfant);
    }

    /**
     * Méthode qui permet de réaliser une mutation d'un joueur
     * @param joueur le joueur qui mute
     */
    public void mutation(Joueur joueur) {
        Reseau res = joueur.getReseau();

        int probaMutation;
        Neurone n;
        int index;

        List<Module> modules = res.getModules();
        try {
            for (Module module : modules) {
                for (Neurone neurone : module.getNeurones()) {
                    probaMutation = random.nextInt(res.getNbNeurone()-1);
                    if (probaMutation == 0) {
                        n = changerTypeNeurone(neurone);
                        index = module.getNeurones().indexOf(neurone);
                        module.getNeurones().set(index, n);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mutationParModule(Joueur joueur) {
        Reseau res = joueur.getReseau();

        for (Module mod : res.getModules()) {
            mutationModule(mod);
        }
    }

    private void mutationModule(Module module) {

        int probaMutation;
        Neurone n;
        int index;

        try {
                for (Neurone neurone : module.getNeurones()) {
                    probaMutation = random.nextInt(module.getNeurones().size());
                    if (probaMutation == 0) {
                        n = changerTypeNeurone(neurone);
                        index = module.getNeurones().indexOf(neurone);
                        module.getNeurones().set(index, n);
                    }
                }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode qui permet de réaliser la mutation
     * @param neurone le neurone qui mute
     * @return retourne le nouveau neurone
     */
    protected Neurone changerTypeNeurone(Neurone neurone){
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
                default -> throw new IllegalArgumentException("Erreur indice du type du neurone lors de la mutation");
            };
        }

        return res;
    }

    /**
     * Méthode qui permet de sélectionner les parents
     * @param population population
     * @return retourne la liste des parents
     */
    public List<Joueur> selectionnerParents(List<Joueur> population){
        List<Joueur> nouvellePopulation = new ArrayList<>();

        List<Joueur> copiePopulation = new ArrayList<>(population);
        // On tri la population
        copiePopulation.sort(Comparator.comparingDouble(Joueur::getScore).reversed());

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

    /**
     * Méthode qui permet de sélectionner aléatoirement une partie de la population
     * @param population liste de joueur
     * @param i nombre de joueurs à sélectionner
     * @return retourne une partie de la population aléatoire
     */
    protected List<Joueur> prendreAleatoire(List<Joueur> population, int i) {
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
