package com.smartdash.project.controller;

import com.smartdash.project.modele.Jeu;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Timer;

public class ControllerClavier implements EventHandler<KeyEvent> {

    private Jeu donnees;

    public ControllerClavier(Jeu donnees){
        this.donnees = donnees;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode touche = keyEvent.getCode();
        switch (touche){
            case SPACE -> {
                donnees.getJoueur().sauter();
                break;
            }
            case ENTER -> {
                if(donnees.getJoueur().getVivant())
                    donnees.lancerHumainGraphique();
            }
            case R -> {
                donnees.getJoueur().renitialiser();
                donnees.notifierObservateurs();
            }
        }
    }
}
