package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;

public class VueInformationApp extends GridPane implements Observateur {

    Jeu donnees;

    Text titreTouches;
    VBox touches;

    public VueInformationApp(Jeu donnees,int width, int height) {
        this.donnees = donnees;
        setPrefWidth(width);
        setPrefHeight(height);
        setStyle("-fx-background-color: grey");

        titreTouches = new Text("TOUCHES");
        titreTouches.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        touches = new VBox();
        touches.setSpacing(10);
        touches.setPrefHeight(height - 50);
        addRow(0,titreTouches);
        addRow(1,touches);

    }

    public void init(){
        touches.getChildren().clear();
        touches.getChildren().add(new Text("Espace : Sauter"));
        touches.getChildren().add(new Text("R : Réinitialiser"));
        touches.getChildren().add(new Text("P : Avancer d'une case"));
        touches.getChildren().add(new Text("A : Lancer l'IA"));
        touches.getChildren().add(new Text("Entrée : Lancer le jeu"));


    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
