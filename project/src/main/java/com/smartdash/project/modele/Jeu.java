package com.smartdash.project.modele;


import com.smartdash.project.IA.*;
import com.smartdash.project.IA.Module;
import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Jeu {
    // Attributs
    private Joueur joueur;
    private Terrain terrain;


    //Constructeur
    public Jeu(Terrain terrain, Reseau reseau)
    {
        //this.joueur = new Joueur(0,0, terrain, reseau);
        this.terrain = terrain;
        this.joueur = new Joueur(terrain, reseau);
    }

    /**
     * Méthode qui permet de lancer le jeu en utilisant un réseau d'IA
     */
    public void lancerIA()
    {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!joueur.getVivant())
                {
                    timer.cancel();
                }
                else
                {
                    joueur.initialiserReseauActive();
                    boolean sauter = joueur.getReseau().isActive();

                    if(sauter)
                    {
                        joueur.sauter();
                    }

                    updateJeu();
                }
            }
        };

        // On affiche une première fois le jeu
        afficherPartie();
        timer.scheduleAtFixedRate(task,0,200);
    }

    /**
     * Méthode qui permet de lancer le jeu et d'y jouer en console
     */
    public void lancerHuamin()
    {
        Scanner sc = new Scanner(System.in);
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!joueur.getVivant())
                {
                    timer.cancel();
                }
                else
                {
                    if (sc.hasNext()) {
                        String input = sc.nextLine();
                        if (input.equalsIgnoreCase("s")) {
                            joueur.sauter(); // Action de saut du joueur
                            // Vous pouvez ajouter la logique de saut ici
                        } else if (input.equalsIgnoreCase("q")) {
                            timer.cancel(); // Arrête le Timer si l'utilisateur entre 'q'
                        }
                    }

                    updateJeu();
                }
            }
        };

        // On affiche une première fois le jeu
        afficherPartie();
        timer.scheduleAtFixedRate(task,0,200);
    }

    /**
     * Méthode qui permet de changer l'état du jeu à chaque temps
     */
    public void updateJeu()
    {
        this.joueur.updateJoueur();
        afficherPartie();
    }

    /**
     * Méthode temporaire qui affiche le jeu en console
     */
    private void afficherPartie() {
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();

        for (int i = 0; i < terrain.getLargeur(); i++) {
            for (int j = 0; j < terrain.getLongueur(); j++) {
                boolean isJoueur = (j == joueurX && i == joueurY);
                int finalI = i;
                int finalJ = j;

                boolean isBloc = terrain.getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Bloc);

                boolean isPique = terrain.getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Pique);

                boolean isVide = terrain.getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Vide);


                if (isJoueur) {
                    System.out.print("J"); // Afficher le joueur
                } else if (isBloc) {
                    System.out.print("B");
                } else if (isPique) {
                    System.out.print("P");
                }
                else if (isVide){
                    System.out.print("."); // Afficher une case vide
                }
            }
            System.out.println(); // Nouvelle ligne pour chaque ligne de la carte
        }
        System.out.println(); // Ligne vide pour séparer les affichages successifs
    }


    public static void main(String[] args) {
        Terrain terrain = new Terrain("src/main/resources/map.txt");

        Neurone neurone = new NeuroneNonVide(3,-2);
        Neurone neurone2 = new NeuroneBloc(2, -2);
        Module module = new Module();
        module.addNeurone(neurone);
        module.addNeurone(neurone2);

        Reseau reseau = new Reseau();
        reseau.addModule(module);

        Jeu jeu = new Jeu(terrain, reseau);

        jeu.lancerHuamin();
    }
}
