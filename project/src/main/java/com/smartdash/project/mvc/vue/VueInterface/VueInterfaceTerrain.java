package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.scene.SceneInterface;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class VueInterfaceTerrain extends InterfaceChoix implements Observateur {

    public VueInterfaceTerrain(Jeu modele, Stage stage, VueInterfaceIA vueInterfaceIA) throws Exception {
        super(modele, stage);
        ajouterInterfaceConnectee(vueInterfaceIA);
    }

    public void init() throws Exception {
        super.init();

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

    }

    @Override
    public void addChoixSecondaire() throws Exception {

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
                ((SceneInterface)stage.getScene()).setSceneJeu();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
