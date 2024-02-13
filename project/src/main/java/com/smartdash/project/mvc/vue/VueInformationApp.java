package com.smartdash.project.mvc.vue;

import com.smartdash.project.IA.Module;
import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.controller.ControllerSouris;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

public class VueInformationApp extends HBox implements Observateur {

    Jeu donnees;

    VBox joueurInfo = new VBox();
    VBox terrainInfo = new VBox();

    public VueInformationApp(Jeu donnees,int width, int height, Color c) {
        this.donnees = donnees;
        setPrefSize(width,height);
        setMaxSize(width,height);
        setMinHeight(height);
        setPadding(new javafx.geometry.Insets(10,10,10,10));
        setSpacing(20);

        // Obtenir les composantes RVB
        double red = c.getRed();
        double green = c.getGreen();
        double blue = c.getBlue();

        // Convertir en hexadécimal
        String hex = String.format("#%f%f%f", red, green, blue);
        System.out.println(hex);

        setStyle("-fx-background-color: grey");


    }

    private Text genererTextModule(Module module, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("M").append(i+1).append(" : ");
        for(Neurone neurone : module.getNeurones()){
            switch (neurone.getType()){
                case 'a' -> sb.append("Actif");
                case 'p' -> sb.append("Pique");
                case 'q' -> sb.append("NonPique");
                case 'v' -> sb.append("Vide");
                case 'w' -> sb.append("NonVide");
                case 'b' -> sb.append("Bloc");
                case 'd' -> sb.append("NonBloc");
            }
            sb.append("(").append(neurone.getX()).append(",").append(neurone.getY()).append(") ");
        }
        Text text = new Text(sb.toString());
        text.setId("moduleText" + i);
        if(module.isActive()){
            text.setStyle("-fx-font-size: 12px; -fx-fill: green");
        }else{
            text.setStyle("-fx-font-size: 12px; -fx-fill: red");
        }
        return text;
    }

    public void init(){
        //Partie joueur

        //Titre joueur
        HBox titreJoueur = new HBox();
        titreJoueur.setSpacing(10);
        Text joueur = new Text("Info Joueur :");
        joueur.setStyle("-fx-font-size: 20px; -fx-fill: white");

        //image du joueur
        javafx.scene.shape.Rectangle imageJoueur = new javafx.scene.shape.Rectangle(30, 30);
        imageJoueur.setFill(new ImagePattern(new javafx.scene.image.Image("player.png")));


        //ajout du score du joueur
        Text score = new Text("- Score : " + donnees.getJoueur().getX());
        score.setId("score");
        score.setStyle("-fx-font-size: 15px; -fx-fill: white");

        //ajout du nombre de neurones
        Text nbNeurone = new Text("- Neurones : " + donnees.getJoueur().getReseau().getNbNeurone());
        nbNeurone.setId("nbNeurone");
        nbNeurone.setStyle("-fx-font-size: 15px; -fx-fill: white");

        //ajout du nombre de modules
        Text nbModules = new Text("- Modules : " + donnees.getJoueur().getReseau().getModules().size());
        nbModules.setId("nbModules");
        nbModules.setStyle("-fx-font-size: 15px; -fx-fill: white");

        //ajout du nombre de modules actifs
        Text nbModulesActif = new Text("- Modules actifs : " + donnees.getJoueur().getReseau().nbModulesActifs());
        nbModulesActif.setId("nbModulesActif");
        nbModulesActif.setStyle("-fx-font-size: 15px; -fx-fill: white");

        joueurInfo.getChildren().addAll(titreJoueur, score, nbNeurone, nbModules, nbModulesActif);

        //ajout des modules
        for (int i = 0; i < donnees.getJoueur().getReseau().getModules().size(); i++) {
            Text module = genererTextModule(donnees.getJoueur().getReseau().getModules().get(i), i);
            joueurInfo.getChildren().add(module);
        }
        //fin partie joueur


        //Partie terrain

        //Titre terrain
        Text titreTerrain = new Text("Info Terrain :");
        titreTerrain.setStyle("-fx-font-size: 20px; -fx-fill: white");

        //ajout chemin du terrain
        Text cheminTerrain = new Text("- Chemin : " + donnees.getTerrain().getNomFichier());
        cheminTerrain.setStyle("-fx-font-size: 15px; -fx-fill: white");

        Text tailleTerrain = new Text("- Taille : " + donnees.getTerrain().getLongueur() + "x" + donnees.getTerrain().getLargeur());
        tailleTerrain.setStyle("-fx-font-size: 15px; -fx-fill: white");


        terrainInfo.getChildren().addAll(titreTerrain, cheminTerrain, tailleTerrain);
        //fin partie terrain

        //on ajoute les parties de la vue
        getChildren().addAll(joueurInfo,terrainInfo);

    }

    @Override
    public void actualiser(Sujet sujet) {
        joueurInfo.getChildren().stream().filter(node -> Objects.equals(node.getId(), "score")).forEach(node -> {
            ((Text) node).setText("- Score : " + donnees.getJoueur().getX());
        });
        joueurInfo.getChildren().stream().filter(node -> Objects.equals(node.getId(), "nbNeurone")).forEach(node -> {
            ((Text) node).setText("- Neurones : " + donnees.getJoueur().getReseau().getNbNeurone());
        });
        joueurInfo.getChildren().stream().filter(node -> Objects.equals(node.getId(), "nbModules")).forEach(node -> {
            ((Text) node).setText("- Modules : " + donnees.getJoueur().getReseau().getModules().size());
        });
        joueurInfo.getChildren().stream().filter(node -> Objects.equals(node.getId(), "nbModulesActif")).forEach(node -> {
            ((Text) node).setText("- Modules actifs : " + donnees.getJoueur().getReseau().nbModulesActifs());
        });
        joueurInfo.getChildren().stream().filter(node -> node.getId() != null && node.getId().contains("moduleText")).forEach(node -> {
            if(donnees.getJoueur().getReseau().getModules().get(Integer.parseInt(node.getId().substring(10))).isActive())
                node.setStyle("-fx-font-size: 12px; -fx-fill: green");
            else
                node.setStyle("-fx-font-size: 12px; -fx-fill: red");

        });


    }
}
