package com.smartdash.project.vue;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Sujet;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class VueJeu extends Pane implements Observateur {

    private Jeu donnees;

    public VueJeu(Jeu donnees) {
        this.donnees = donnees;

        //Taille de base de la fenêtre de jeu
        setPrefSize(1000, 600);

        //Taille maximale de la fenêtre de jeu
        setMaxSize(1000, 600);

        //Ajout du background en Image
        setBackground(new Background(new BackgroundImage(new Image("background1.png"), BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

    }
    @Override
    public void actualiser(Sujet sujet) {

    }
}
