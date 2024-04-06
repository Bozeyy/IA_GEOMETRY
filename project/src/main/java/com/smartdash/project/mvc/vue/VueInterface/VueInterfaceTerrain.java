package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.scene.SceneInterface;
import com.smartdash.project.mvc.vue.Observateur;
import com.smartdash.project.mvc.vue.VueJeu;
import com.smartdash.project.mvc.vue.VueTerrain;
import com.smartdash.project.terrainAleatoire.GenerateurTerrainAleatoire;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.File;
import java.util.List;

public class VueInterfaceTerrain extends InterfaceChoix implements Observateur {

    List<String> couleurs;
    Rectangle affichageCouleur;
    Button genererTerrainAleatoire;

    public VueInterfaceTerrain(Jeu modele, Stage stage, VueInterfaceIA vueInterfaceIA) throws Exception {
        super(modele, stage);
        ajouterInterfaceConnectee(vueInterfaceIA);
    }

    public void init() throws Exception {
        super.init();
        initAffichageCouleur();
        initGenererTerrainAleatoire();
        addTout();

    }

    @Override
    public void addRetourArriere() {
        retourArriere.setOnAction(e -> {
            addTransitionInterface("VueInterfaceIA");
        });
    }

    @Override
    public void addChoixPrincipal() {
        choixPrincipal.setPrefHeight(choixPrincipal.getPrefHeight() - 100);

        List<Terrain> terrainList = modele.genererTerrains();


        for (Terrain terrain : terrainList){
            String[] split = terrain.getNomFichier().split("/");
            TreeItem<String> treeItem = new TreeItem<>(new File(terrain.getNomFichier()).getName());
            TreeItem<String> treeItemDossier;

            // On regarde si le nom du dossier du terrain est dans le treeView
            List<TreeItem<String>> item = choixPrincipal.getRoot().getChildren().stream().filter(stringTreeItem -> stringTreeItem.getValue().equals(split[split.length - 2])).toList();
            if(item.isEmpty()){
                treeItemDossier = new TreeItem<>(split[split.length - 2]);
                treeItemDossier.getChildren().add(treeItem);
                choixPrincipal.getRoot().getChildren().add(treeItemDossier);
            } else {
                item.getFirst().getChildren().add(treeItem);
            }

        }

        choixPrincipal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getChildren() != null && newValue.getChildren().isEmpty()) {

                //on change le terrain selon le terrain choisi
                String parent = newValue.getParent().getValue();
                String valeur = newValue.getValue();
                String terrain = "src/main/resources/"+parent+"/"+valeur;
                modele.setTerrain(new Terrain(terrain));

                afficherterrain();
            }
        });

    }

    private void afficherterrain() {
        try {
            double longueur = panePrincipal.getPrefWidth();
            double hauteur = panePrincipal.getPrefHeight();
            double x = panePrincipal.getLayoutX();
            double y = panePrincipal.getLayoutY();
            panePrincipal.getChildren().clear();
            getChildren().remove(panePrincipal);

            panePrincipal = new VueTerrain(modele,longueur,hauteur, Color.DARKBLUE);

            panePrincipal.setLayoutX(x);
            panePrincipal.setLayoutY(y);

            getChildren().add(panePrincipal);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addChoixSecondaire() throws Exception {
        choixSecondaire.setPrefWidth(choixSecondaire.getPrefWidth() - 100);

        couleurs = List.of("Noir","Rouge","Bleu");


        for (String couleur : couleurs) {
            choixSecondaire.getItems().add("Couleur : " + couleur);
        }

        choixSecondaire.setValue(choixSecondaire.getItems().getFirst());

        choixSecondaire.setOnAction(e -> {
            switch (choixSecondaire.getSelectionModel().getSelectedItem().replaceAll(".*: ","")){
                case "Noir" -> affichageCouleur.setFill(Color.BLACK);
                case "Bleu" -> affichageCouleur.setFill(Color.DARKBLUE);
                case "Rouge" -> affichageCouleur.setFill(Color.RED);
            }

        });

    }

    @Override
    public void addPanePrincipal() {

    }

    @Override
    public void addValider() {
        valider.setText("Lancer le jeu");
        valider.setOnAction(e -> {
            try {
                // On place le joueur au bon endroit en le recreant

                Joueur joueur = modele.getJoueur();
                Terrain terrain = modele.getTerrain();
                modele.setJoueur(new Joueur(terrain,joueur.getReseau()));

                // On change de scene
                ((SceneInterface)stage.getScene()).setSceneJeu(modele,stage,choixSecondaire.getSelectionModel().getSelectedItem().replaceAll(".*: ",""));

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void initAffichageCouleur(){
        affichageCouleur = new Rectangle(40,40);
        affichageCouleur.setFill(Color.BLACK);
        affichageCouleur.setLayoutX(choixSecondaire.getLayoutX() + choixSecondaire.getPrefWidth() - 80);
        affichageCouleur.setLayoutY(choixSecondaire.getLayoutY() + 5);

        getChildren().add(affichageCouleur);
    }

    public void initGenererTerrainAleatoire(){
        GenerateurTerrainAleatoire generateur = new GenerateurTerrainAleatoire();

        genererTerrainAleatoire = new Button("Générer un terrain aléatoire");
        genererTerrainAleatoire.setStyle("-fx-font-size: 20px");
        genererTerrainAleatoire.setPrefSize(choixPrincipal.getPrefWidth(),50);
        genererTerrainAleatoire.setLayoutX(choixPrincipal.getLayoutX());
        genererTerrainAleatoire.setLayoutY(choixPrincipal.getLayoutY() + choixPrincipal.getPrefHeight() - 50);

        getChildren().add(genererTerrainAleatoire);

        genererTerrainAleatoire.setOnAction(e -> {
            try {
                modele.setTerrain(generateur.genererTerrainAleatoire());
                afficherterrain();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
