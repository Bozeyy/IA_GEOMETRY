package com.smartdash.project.modele;


import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Pique;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Jeu {
    // Attributs
    private Joueur joueur;

    //Constructeur
    public Jeu(Joueur joueur)
    {
        this.joueur = joueur;
    }

    /**
     * Méthode qui permet de lancer le jeu
     */
    public void lancer()
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
                    afficherJeu();
                    if (sc.hasNext()) {
                        String input = sc.nextLine();
                        if (input.equalsIgnoreCase("s")) {
                            joueur.sauter(); // Action de saut du joueur
                            // Vous pouvez ajouter la logique de saut ici
                        } else if (input.equalsIgnoreCase("q")) {
                            timer.cancel(); // Arrête le Timer si l'utilisateur entre 'q'
                        }
                    }

                    /**

                    boolean sauter = joueur.getReseau().isActive(joueur.getObjetsReseau());

                    if(sauter)
                    {
                        joueur.sauter();
                    }

                    */

                    update();
                }
            }
        };

        timer.scheduleAtFixedRate(task,0,200);

    }

    /**
     * Méthode qui permet de changer l'état du jeu à chaque temps
     */
    public void update()
    {
        this.joueur.update();
        afficherJeu();
    }

    /**
     * Méthode temporaire qui affiche le jeu en console
     */
    private void afficherJeu() {
        Terrain terrain = joueur.getMap();
        Double joueurX = joueur.getX();
        Double joueurY = joueur.getY();

        for (int i = 0; i < terrain.getLargeur(); i++) {
            for (int j = 0; j < terrain.getLongueur(); j++) {
                boolean isJoueur = (j == joueurX.intValue() && i == joueurY.intValue());
                int finalI = i;
                int finalJ = j;

                boolean isBloc = terrain.getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Bloc);

                boolean isPique = terrain.getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Pique);


                if (isJoueur) {
                    System.out.print("J"); // Afficher le joueur
                } else if (isBloc) {
                    System.out.print("B");
                } else if (isPique) {
                    System.out.print("P");
                }
                else {
                    System.out.print("."); // Afficher une case vide
                }
            }
            System.out.println(); // Nouvelle ligne pour chaque ligne de la carte
        }
        System.out.println(); // Ligne vide pour séparer les affichages successifs
    }

    public static void main(String[] args) {
        Terrain terrain = new Terrain("src/main/resources/map.txt");
        Joueur joueur1 = new Joueur(0.0,0.0, terrain, new Reseau());
        Jeu jeu = new Jeu(joueur1);

        jeu.lancer();
    }
}
