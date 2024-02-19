package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.Module;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.IA.ReseauFabrique;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.apprentissage.util.Statistique;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NeatVariation extends NeatAmelioration
{
    /**
     * Constructeur avec un max de génération et un nb de terrain à générer
     *
     * @param maxGenerations max de génération
     * @param nbTerrains     nombre de terrains max
     */
    public NeatVariation(int maxGenerations, int nbTerrains) {
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
     * Méthode qui permet de réaliser toute les mutations
     * @param enfant1 enfant
     */
    private void mutationAll(Joueur enfant1) {
        mutation(enfant1);
        mutationPosition(enfant1);

        mutationNbModules(enfant1);
        mutationNbNeuronne(enfant1);
    }

    /**
     * Méthode qui permet de réaliser une mutation pour ajouter ou supprime des modules
     * @param enfant1 l'enfant sur lequel il y aura une mutation
     */
    public void mutationNbModules(Joueur enfant1) {
        // Initialise la probabilité des actions
        final double PROBA_AJOUTER = 5.0;

        double probabilite = random.nextDouble() * 100.0;

        if(probabilite < PROBA_AJOUTER)
        {
            // Ajouter un module
            ajouterModule(enfant1);
        }
    }

    /**
     * Méthode qui permet d'ajouter un module
     * @param joueur l'enfant
     */
    private void ajouterModule(Joueur joueur) {
        // On récupère un module aléatoire de l'enfant
        int indexRandomModule = random.nextInt(joueur.getReseau().getModules().size());

        Module moduleAleatoire = joueur.getReseau().getModules().get(indexRandomModule).clone();

        // On ajoute ce module au réseau
        joueur.getReseau().addModule(moduleAleatoire);

        // On mute ce module
        mutation(joueur);
        mutationPosition(joueur);
    }

    /**
     * Méthode qui permet de réaliser une mutation sur le nombre de neuronnes
     * @param joueur
     */
    public void mutationNbNeuronne(Joueur joueur) {
        Reseau res = joueur.getReseau();

        int probaMutation = random.nextInt(5);

        if (probaMutation == 3) {
            // ajout d'un neurone
            res.ajouterNeuroneAleatoire();

        } else {
            if (probaMutation == 4) {
                // suppression d'un neurone
                res.supprimerNeuroneAleatoire();

            }
        }
    }

    @Override
    public List<Joueur> selectionnerParents(List<Joueur> population){
        List<Joueur> copiePopulation = new ArrayList<>(population);
        // On tri la population
        copiePopulation.sort(Comparator.comparingDouble(Joueur::getScoreApprentissage).reversed());

        if (population.size() != 1000)
        {
            throw new IllegalStateException("La taille de la population n'est pas de 1000");
        }

        System.out.println("nb neurone :" + copiePopulation.get(0).getReseau().getNbNeurone() );
        // On initialise
        List<Joueur> nouvellePopulation = new ArrayList<>(copiePopulation.subList(0, NB_MEILLEURS));
        nouvellePopulation.addAll(prendreAleatoire(copiePopulation.subList(8,57),NB_PARTIE_2));
        nouvellePopulation.addAll(prendreAleatoire(copiePopulation.subList(57,407),NB_PARTIE_3));
        nouvellePopulation.addAll(prendreAleatoire(copiePopulation.subList(407,907),NB_PARTIE_4));
        nouvellePopulation.addAll(prendreAleatoire(copiePopulation.subList(8,57),NB_PARTIE_5));

        return nouvellePopulation;
    }
}
