package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.List;

public class VueCommande extends MenuBar implements Observateur {

    Jeu modele;

    Menu menuTerrain = new Menu("Terrains");

    public VueCommande(Jeu modele, int width, int height) {
        this.modele = modele;
        setPrefSize(width,height);



    }
    @Override
    public void actualiser(Sujet sujet) {

    }

    public void init() throws Exception {
        getMenus().add(menuTerrain);

        File repertoire = new File("src/main/resources");

        if(!repertoire.exists()) throw new Exception("Le répertoire n'existe pas");
        if(!repertoire.isDirectory()) throw new Exception("Le répertoire n'est pas un répertoire");

        List<File> fichiers = List.of(repertoire.listFiles()).stream().filter(file -> (file.isDirectory() && !file.getName().contains("com")) || (file.isDirectory() && !file.getName().equals("enregistrement")) || file.getName().endsWith(".txt")).toList();





        /*List<String> terrains = modele.getTerrains();

        for (int i = 0; i < modele.getTerrains().size(); i++) {
            MenuItem menuItem = new MenuItem("Terrain " + (i + 1));
            menuItem.setOnAction(event -> {
                modele.setTerrainCourant(i);
            });
            menuTerrain.getItems().add(menuItem);
        }*/
    }
}
