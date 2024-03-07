package com.smartdash.project.mvc.modele;


import com.smartdash.project.IA.*;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.Vide;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

public class Jeu implements Sujet{
    private Joueur joueur;
    private Terrain terrain;
    private boolean jouer;
    private List<Observateur> observateurs;
    private final int TAIILE_CASE = 30;


    /**
     * Constructeur avec terrain et un reseau
     * @param terrain terrain
     * @param reseau réseau
     */
    public Jeu(Terrain terrain, Reseau reseau)
    {
        this.terrain = terrain;
        this.joueur = new Joueur(terrain, reseau);
        this.jouer = false;

        this.observateurs = new ArrayList<>();
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
        jouer = false;
    }


    /**
     * Méthode qui permet d'évaluer un joueur
     */
    public void evaluationUnJoueur() {
        reinitialiser();
        lancerEvaluation(false);

        this.joueur.setScorePartie(joueur.getX() + 1 );
        this.joueur.setScoreApprentissage(joueur.getScorePartie() - joueur.getReseau().getNbNeurone());
    }

    /**
     * Méthode qui permet de faire jouer un joueur
     * @param afficher permet d'afficher le jeu dans la console
     */
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
     * temporaire
     */
    public void lancerIA(boolean affiche)
    {
        jouer = true;
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

        this.joueur.setScorePartie(joueur.getX() + 1);
    }

    /**
     * Méthode qui permet de lancer le jeu et d'y jouer en console
     * temporaire
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
     * Méthode qui permet de jouer
     */
    public Timeline lancerHumainGraphique(boolean afficher, double millis) {
        jouer = true;
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(millis), evt -> {
                if (!joueur.getVivant()) {
                    System.out.println("Vous avez perdu");
                    //((Timeline) evt.getSource()).stop();
                } else if (joueur.fin) {
                    System.out.println("Vous avez gagné");
                    //((Timeline) evt.getSource()).stop();
                }
                updateJeu(afficher);
                notifierObservateurs();
            }));
        timer.setCycleCount(Timeline.INDEFINITE);
        return timer;
    }

    /**
     * Méthode qui permet de lancer le jeu
     * @param afficher affiche le jeu dans la console
     * @param millis chaque temps
     * @return Timeline
     */
    public Timeline lancerJeu(boolean afficher,double millis) {
        jouer = true;
        Timeline timer = new Timeline(
                new KeyFrame(Duration.millis(millis), evt -> {
                    if(joueur.getVivant() && !joueur.fin)
                    {
                        joueur.initialiserReseauActive();
                        boolean sauter = joueur.getReseau().isActive();

                        if(sauter)
                        {
                            joueur.sauter();
                        }

                        updateJeu(afficher);
                    }
                })
        );

        timer.setCycleCount(Timeline.INDEFINITE);
        return timer;
    }

    /**
     * Méthode qui permet de changer l'état du jeu à chaque temps
     */
    public void updateJeu(boolean afficher)
    {
        this.joueur.updateJoueur();
        //this.camera.update(this.joueur);


        if(afficher)
        {
            afficherPartie();
        }

        this.notifierObservateurs();
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

    /**
     * Méthode qui permet de générer les terrains pour l'interface
     * @return retourne la liste des terrains
     */
    public List<Terrain> genererTerrains(){
        List<Terrain> terrains = new ArrayList<>();

        // On récupère les terrains dans le dossier src/main/resources
        File folder = new File("src/main/resources");
        File[] listOfFiles = folder.listFiles();

        // On parcourt les dossiers pour récupérer les terrains
        assert listOfFiles != null;
        for (File file : listOfFiles) {

            if (file.isDirectory() && (file.getName().equals("apprentissage") || file.getName().equals("Terrains") || file.getName().equals("test_apprentissage"))) {
                // On parcourt les terrains dans chaque dossier
                for (File terrain : Objects.requireNonNull(file.listFiles())) {
                    // On ajoute le terrain à la liste
                    terrains.add(new Terrain("src/main/resources/" + file.getName() + "/" + terrain.getName()));
                }
            }
        }
        return terrains;
    }


    /**
     * Méthode qui permet de réinitialiser l'état du joueur
     */
    public void reinitialiser() {
        this.joueur.renitialiser();
        this.jouer = false;
    }


    public Joueur getJoueur() {
        return this.joueur;
    }
    public Terrain getTerrain() {
        return this.terrain;
    }

    public int getTailleCase() {
        return TAIILE_CASE;
    }

    public boolean isJouer() {
        return jouer;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
        this.joueur.setMap(terrain);
    }

    public Map<String,Map<String,List<Joueur>>> genererJoueurs(boolean meilleur) throws Exception {

        // On aussi chaque apprentissage à son nom de dossier
        HashMap<String,Map<String,List<Joueur>>> apprentissageAll = new HashMap<>();

        // On récupère les dossiers d'apprentissage
        File dossierEnregistrement = new File("src/main/resources/enregistrement");

        int nbApprentissage = 0;
        int nbGeneration = 0;
        int nbJoueur = 0;

        // Si meilleur est vrai, on ne prend que les meilleurs sion on prend tous les dossiers
        List<File> dossierApprentissages = Objects.requireNonNull(Arrays.stream(Objects.requireNonNull(dossierEnregistrement.listFiles())).toList().stream().filter(file -> !meilleur && file.isDirectory() && Objects.requireNonNull(file.listFiles()).length != 0 || file.getName().contains("meilleurs")).toList());

        for (File dossierApprentissage : dossierApprentissages) {

            // On crée une liste de generations
            Map<String,List<Joueur>> apprentissage = new HashMap<>();

            // On récupère les fichiers de génération
            List<File> generations = Arrays.stream(Objects.requireNonNull(dossierApprentissage.listFiles())).toList();


            int size = generations.size();

            // On trie les fichiers de génération
            if(dossierApprentissage.getName().matches("[0-9]+-[0-9]+-[0-9]+.*")) generations = generations.stream().sorted(Comparator.comparingInt(file -> {
                String fileName = file.getName();
                int underscoreIndex = fileName.lastIndexOf('_');
                int dotIndex = fileName.lastIndexOf('.');
                String number = fileName.substring(underscoreIndex + 1, dotIndex);
                return Integer.parseInt(number);
            })).toList();

            // On parcourt les fichiers de génération
            if(size > 3000) { // si il y a plus de 3000 générations, on prend le premier, celui du milieu et les 3 dernières

                ArrayList<File> generations1 = new ArrayList<>();
                generations1.add(generations.get(0));
                generations1.add(generations.get(size / 2));
                generations1.add(generations.get(size - 1));
                generations1.add(generations.get(size - 2));
                generations1.add(generations.get(size - 3));
                generations = generations1;

            } else if(size > 4) { // sinon on prend le premier, celui du quart, celui du milieu, celui des 3/4 et le dernier

                generations = List.of(generations.get(0), generations.get(size / 4), generations.get(size / 2), generations.get(3 * size / 4), generations.get(size - 1));

            } // sinon on prend tout

            for (File generation : generations) {

                // On récupère les 10 meilleurs
                List<Joueur> joueurs = Enregistrement.stringToPopulation(generation.getPath()).stream().limit(10).toList();

                    nbJoueur+= joueurs.size();

                // On ajoute la liste des joueurs à la liste des generations
                apprentissage.put(generation.getName(),joueurs);

                nbGeneration++;

            }

            // On ajoute la liste des generations à la liste d'apprentissage
            apprentissageAll.put(dossierApprentissage.getName(),apprentissage);

            nbApprentissage++;

        }

        return apprentissageAll;
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
}
