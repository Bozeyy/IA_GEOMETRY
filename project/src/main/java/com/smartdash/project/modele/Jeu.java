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


    /**
     * Constructeur avec terrain et un reseau
     * @param terrain terrain
     * @param reseau réseau
     */
    public Jeu(Terrain terrain, Reseau reseau)
    {
        //this.joueur = new Joueur(0,0, terrain, reseau);
        this.terrain = terrain;
        this.joueur = new Joueur(terrain, reseau);
    }

    /**
     * Constructeur avec un joueur
     * @param joueur joueur
     */
    public Jeu(Joueur joueur, Terrain terrain)
    {
        this.joueur = joueur;
        this.joueur.setMap(terrain);
        this.terrain = terrain;
    }

    /**
     * Méthode qui permet d'évaluer une partie
     * retourne le score de la partie
     */
    public void evaluation()
    {
        lancerEvaluation();
        this.joueur.setScore(joueur.getX() + 1);
    }

    public void lancerEvaluation() {
//        afficherPartie();
        while (joueur.getVivant() && !joueur.fin) {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if (sauter) {
                joueur.sauter();
            }
            updateJeu();
        }
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
                    System.out.println("Vous avez perdu");
                    timer.cancel();
                } else if (joueur.fin) {
                    System.out.println("Vous avez gagné");
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
                    System.out.println("Vous avez perdu");
                    timer.cancel();
                } else if (joueur.fin) {
                    System.out.println("Vous avez gagné");
                    timer.cancel();
                } else
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

    public Joueur getJoueur() {
        return this.joueur;
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
        Terrain terrain = new Terrain("src/main/resources/apprentissage/terrain5.txt");

        Neurone n1 = new NeuroneBloc(2, -3);
        Neurone n2 = new NeuroneNonVide(3, -3);
        Neurone n3 = new NeuronePique(4, -3);

        Neurone n4 = new NeuroneBloc(2, -4);
        Neurone n5 = new NeuroneNonVide(3, -4);

        Neurone n6 = new NeuronePique(1,0);
        Reseau r = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3}), ModuleFabrique.genererModule(new Neurone[]{n4, n5}), ModuleFabrique.genererModule(new Neurone[]{n6})});

        Jeu jeu = new Jeu(terrain, r);

        jeu.lancerHuamin();
    }
}
