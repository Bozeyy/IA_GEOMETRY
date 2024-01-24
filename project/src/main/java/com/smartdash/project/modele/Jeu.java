package com.smartdash.project.modele;


import com.smartdash.project.IA.*;
import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;
import com.smartdash.project.vue.Observateur;
import com.smartdash.project.vue.VueJeu;

import java.util.*;

public class Jeu implements Sujet{
    // Attributs
    private Joueur joueur;
    private Terrain terrain;

    private List<Observateur> observateurs;


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

        //Partie mvc
        this.observateurs = new ArrayList<>();
        //this.enregistrerObservateur(new VueJeu(this));
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
        this.observateurs = new ArrayList<>();
        //this.enregistrerObservateur(new VueJeu(this));
    }

    public void evaluationUnJoueur() {
        this.joueur.renitialiser();
        lancerEvaluation(false);
        this.joueur.setScore(joueur.getX() + 1);
    }

    /**
     * Méthode qui permet d'évaluer une partie
     * retourne le score de la partie
     */
    public void evaluationPlusieurs()
    {
        this.joueur.renitialiser();

        lancerEvaluation(false);

        this.joueur.addScore(this.joueur.getX() +1);

        this.joueur.setScoreMoyen(this.joueur.getScoresListes().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0));
    }

    public void lancerEvaluation(boolean afficher) {
        if(afficher)
        {
            afficherPartie();
        }

        while (joueur.getVivant() && !joueur.fin) {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if (sauter) {
                joueur.sauter();
            }
            updateJeu(afficher);
        }
    }

    /**
     * Méthode qui permet de lancer le jeu en utilisant un réseau d'IA
     */
    public void lancerIA(boolean affiche)
    {
        if (affiche) afficherPartie();

        while (joueur.getVivant() && !joueur.fin)
        {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if(sauter)
            {
                joueur.sauter();
            }
            updateJeu(affiche);

        }

        this.joueur.setScore(joueur.getX() + 1);
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

                    updateJeu(true);
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
    public void updateJeu(boolean afficher)
    {
        this.joueur.updateJoueur();
        if(afficher)
        {
            afficherPartie();
        }
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

    // GETTER

    public Joueur getJoueur() {
        return this.joueur;
    }

    @Override
    public void enregistrerObservateur(Observateur observateur) {
        if(!observateurs.contains(observateur))
        {
            observateurs.add(observateur);
        }
    }

    @Override
    public void supprimerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateurs() {
        for(Observateur observateur : observateurs)
        {
            observateur.actualiser(this);
        }
    }

    public void genererTerrainGraphique(){
        List<Objet> objets = terrain.getMap();
        //TODO
    }

    public Observateur getVueJeu(){
        //Retourne la vue du jeu
        return this.observateurs.stream().filter(o -> o instanceof VueJeu).toList().getFirst();
    }
}
