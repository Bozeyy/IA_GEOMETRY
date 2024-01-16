package com.smartdash.project;

import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Terrain;

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
        j.evaluation();
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

        }
    }
}
