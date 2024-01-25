package com.smartdash.project.mvc.modele;


import com.smartdash.project.IA.*;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.Objet;
import com.smartdash.project.mvc.modele.objet.Pique;
import com.smartdash.project.mvc.modele.objet.Vide;
import com.smartdash.project.mvc.vue.Observateur;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class Jeu implements Sujet{
    // Attributs
    private Joueur joueur;
    private Terrain terrain;
    private Camera camera;


    private int tailleCase = 30;

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
        this.camera = new Camera(this.joueur.getX(), this.joueur.getY());

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
        this.camera = new Camera(this.joueur.getX(), this.joueur.getY());
        this.observateurs = new ArrayList<>();
        //this.enregistrerObservateur(new VueJeu(this));
    }

    public void evaluationUnJoueur() {
        this.joueur.renitialiser();
        lancerEvaluation(false);
        this.joueur.setScore(joueur.getX() + 1);
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

    public void lancerHumainGraphique() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!joueur.getVivant()) {
                    System.out.println("Vous avez perdu");
                    timer.cancel();
                } else if (joueur.fin) {
                    System.out.println("Vous avez gagné");
                    timer.cancel();
                }
                updateJeu(false);
                notifierObservateurs();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 200);
    }

    public void lancerJeu() {
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(300.0), evt -> {

                    if(joueur.getVivant() && !joueur.fin)
                    {
                        joueur.initialiserReseauActive();
                        boolean sauter = joueur.getReseau().isActive();

                        if(sauter)
                        {
                            joueur.sauter();
                        }

                        updateJeu(true);
                    }

                })
        );
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /**
     * Méthode qui permet de changer l'état du jeu à chaque temps
     */
    public void updateJeu(boolean afficher)
    {
        this.joueur.updateJoueur();
        this.camera.update(this.joueur);
        this.notifierObservateurs();
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

    public Terrain getTerrain() {
        return this.terrain;
    }

    public int getTailleCase() {
        return tailleCase;
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
