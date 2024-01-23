package com.smartdash.project.vue;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Sujet;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

public class AffichageJeu extends GridPane implements Observateur {

    private Jeu donnees;

    public AffichageJeu(Jeu donnees) {
        this.donnees = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(1000, 600);

        //Taille maximale de la fenêtre de jeu
        setMaxSize(1000, 600);

        //Ajout du background
        setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, null, null)));
    }
    @Override
    public void actualiser(Sujet sujet) {

    }
}
