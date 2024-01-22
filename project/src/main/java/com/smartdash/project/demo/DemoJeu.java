package com.smartdash.project.demo;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.apprentissage.Neat;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;
import com.smartdash.project.modele.objet.Bloc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoJeu {

    public static void lancerHumain() {
        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map2.txt"), new Reseau());
        j.lancerHuamin();
    }

    public static void lancerEvaluation()
    {
        Neurone neurone = new NeuronePique(0, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map2.txt"), reseau);
        j.evaluationUnJoueur();
        System.out.println(j.getJoueur().getScore());
    }

    public static void lancerNeuronePique1() {
        Neurone neurone = new NeuronePique(1, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map2.txt"), reseau);
        j.lancerIA();
    }

    public static void lancerNeuronePique2() {
        Neurone neurone = new NeuronePique(1, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map1.txt"), reseau);
        j.lancerIA();
    }

    public static void lancer2ModulesPique() {
        Neurone neurone = new NeuronePique(1, 0);
        Neurone neurone2 = new NeuronePique(2, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone}), ModuleFabrique.genererModule(new Neurone[]{neurone2})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map1.txt"), reseau);
        j.lancerIA();
    }

    public static void lancer2ModulesPique2() {
        Neurone neurone = new NeuronePique(1, 0);
        Neurone neurone2 = new NeuronePique(2, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone}), ModuleFabrique.genererModule(new Neurone[]{neurone2})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map3.txt"), reseau);
        j.lancerIA();
    }

    public static void lancer2ModulesPiqueBloc() {
        Neurone neurone = new NeuronePique(1, 0);
        Neurone neurone2 = new NeuronePique(2, 0);
        Neurone neurone3 = new NeuroneNonPique(0, -1);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone}), ModuleFabrique.genererModule(new Neurone[]{neurone2, neurone3})});

        Jeu j = new Jeu(new Terrain("src/main/resources/terrains_demo/test_map3.txt"), reseau);
        j.lancerIA();
    }

    public static void test1() {
        Neurone neurone = new NeuroneNonVide(0, -1);
        Neurone neurone2 = new NeuroneNonBloc(1, 0);
        Neurone neurone3 = new NeuroneVide(2, 0);

        Neurone neurone4 = new NeuroneNonPique(3, 0);
        Neurone neurone5 = new NeuroneNonBloc(3, 1);
        Neurone neurone6 = new NeuroneVide(1, 1);

        Neurone neurone7 = new NeuroneNonPique(2, -2);
        Neurone neurone8 = new NeuroneActif(1, 1);
        Neurone neurone9 = new NeuroneVide(2, 0);
        Reseau reseau = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3}), ModuleFabrique.genererModule(new Neurone[]{neurone4, neurone5, neurone6}), ModuleFabrique.genererModule(new Neurone[]{neurone7, neurone8, neurone9})});

        Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain3.txt");
        Joueur j = new Joueur(reseau);
        Neat n = new Neat();
        n.evaluerPerformance(j, terrain);
        System.out.println("score : " + j.getScore());
        Jeu jeu = new Jeu(terrain, reseau);
        jeu.lancerIA();
    }



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int val = 0;
        Jeu j;

        val = sc.nextInt();

        switch (val) {
            case 0:
                System.out.println("Lancement du jeu avec un joueur");
                lancerEvaluation();
                break;

            case 1:
                System.out.println("Lancement du jeu avec une IA à un neurone pique");
                lancerNeuronePique1();
                break;

            case 2:
                System.out.println("Lancement du jeu avec une IA à un neurone pique");
                lancerNeuronePique2();
                break;

            case 3:
                System.out.println("Lancement du jeu avec une IA à deux modules");
                lancer2ModulesPique();
                break;

            case 5:
                System.out.println("Lancement du jeu avec une IA à deux modules (pique, pique, non pique");
                lancer2ModulesPiqueBloc();
                break;

            case 4:
                System.out.println("Lancement du jeu avec une IA à deux modules");
                lancer2ModulesPique2();
                break;

            case 6:
                test1();
                break;

            case 7:
                testCroisement();
                break;

            case 8:
                testMutation();
                break;
            case 9:
                testSelectionParents();
                break;
        }
    }

    private static void testMutation() {
        Neat neat = new Neat();
        Neurone neurone = new NeuroneVide(0, -1);
        Neurone neurone2 = new NeuroneVide(1, 0);
        Neurone neurone3 = new NeuroneVide(2, 0);
        Neurone neurone4 = new NeuroneVide(0, -1);
        Neurone neurone5 = new NeuroneVide(1, 0);
        Neurone neurone6 = new NeuroneVide(2, 0);
        Neurone neurone7 = new NeuroneVide(0, -1);
        Neurone neurone8 = new NeuroneVide(1, 0);
        Neurone neurone9 = new NeuroneVide(2, 0);

        Reseau reseau1 = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3}), ModuleFabrique.genererModule(new Neurone[]{neurone4, neurone5, neurone6}), ModuleFabrique.genererModule(new Neurone[]{neurone7, neurone8, neurone9})});
        Joueur j1 = new Joueur(reseau1);

        System.out.println("Reseau du joueur :");
        System.out.println(reseau1);
        System.out.println("------------------");

        neat.mutationParModule(j1);


        System.out.println("Reseau muté :");
        System.out.println(j1.getReseau());

    }

    private static void testCroisement() {
        Neat neat = new Neat();
        Neurone neurone = new NeuroneVide(0, -1);
        Neurone neurone2 = new NeuroneVide(1, 0);
        Neurone neurone3 = new NeuroneVide(2, 0);
        Neurone neurone4 = new NeuroneVide(2, 0);
        Neurone neurone5 = new NeuroneVide(2, 0);


        Neurone neurone6 = new NeuronePique(3, 0);
        Neurone neurone7 = new NeuronePique(3, 1);
        Neurone neurone8 = new NeuronePique(1, 1);
        Neurone neurone9 = new NeuronePique(3, 0);
        Neurone neurone10 = new NeuronePique(3, 1);




        Module m1 = ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3, neurone4, neurone5});
        Module m2 = ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3, neurone4, neurone5});
        Module m3 = ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3, neurone4, neurone5});
        Module m4 = ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3, neurone4, neurone5});
        Module m5 = ModuleFabrique.genererModule(new Neurone[]{neurone, neurone2, neurone3, neurone4, neurone5});

        Reseau reseau1 = ReseauFabrique.genererReseau(new Module[]{m1,m2,m3,m4,m5});

        Module m6 = ModuleFabrique.genererModule(new Neurone[]{neurone6, neurone7, neurone8, neurone9, neurone10});
        Module m7 = ModuleFabrique.genererModule(new Neurone[]{neurone6, neurone7, neurone8, neurone9, neurone10});
        Module m8 = ModuleFabrique.genererModule(new Neurone[]{neurone6, neurone7, neurone8, neurone9, neurone10});
        Module m9 = ModuleFabrique.genererModule(new Neurone[]{neurone6, neurone7, neurone8, neurone9, neurone10});
        Module m10 = ModuleFabrique.genererModule(new Neurone[]{neurone6, neurone7, neurone8, neurone9, neurone10});


        Reseau reseau2 = ReseauFabrique.genererReseau(new Module[]{m6,m7,m8,m9,m10});
//        Reseau reseau2 = reseau1.clone();

        Joueur j1 = new Joueur(reseau1);
        Joueur j2 = new Joueur(reseau2);

        Joueur enfant = neat.croisement(j1, j2);

        System.out.println("Reseau joueur 1 :");
        System.out.println(reseau1);
        System.out.println("------------------");
        System.out.println("Reseau joueur 2");
        System.out.println(reseau2);
        System.out.println("------------------");
        System.out.println("Reseau enfant : ");
        System.out.println(enfant.getReseau());

    }

    public static void testSelectionParents()
    {
        Neat neat = new Neat();
        Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain2.txt");
        List<Joueur> population = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Reseau r = ReseauFabrique.genererReseau();
            population.add(new Joueur(r));
        }

        for (Joueur joueur : population)
        {
            joueur.setMap(terrain);
            Jeu jeu = new Jeu(joueur, terrain);
            jeu.evaluationUnJoueur();
        }

        for (int i=0; i<11; i++)
        {
            System.out.println("Joueur " + i + " : score de : " + population.get(i).getScore());
        }

        System.out.println(" ");

        List<Joueur> parents = neat.selectionnerParents(population);

        for (int i=0; i<11; i++)
        {
            System.out.println("Joueur " + i + " : score de : " + parents.get(i).getScore());
        }
    }
}