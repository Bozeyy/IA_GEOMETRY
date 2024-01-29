package com.smartdash.project.mvc.controller;

import com.smartdash.project.mvc.modele.Jeu;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Timer;

public class ControllerClavier implements EventHandler<KeyEvent> {

    private Jeu donnees;
    Timeline timeline = null;

    public ControllerClavier(Jeu donnees){
        this.donnees = donnees;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode touche = keyEvent.getCode();
        switch (touche){
            case SPACE -> {
                if(donnees.getJoueur().getVivant() && donnees.isJouer())
                    donnees.getJoueur().sauter();
                break;
            }
            case ENTER -> {
                if(donnees.getJoueur().getVivant())
                    donnees.lancerHumainGraphique();
            }
            case R -> {
                if(timeline != null){
                    timeline.stop();
                    timeline = null;
                }
                donnees.reinitialiser();
                donnees.notifierObservateurs();
            }

            case P -> {
                donnees.getJoueur().setY(0);
                donnees.getJoueur().setX(donnees.getJoueur().getX() + 1);
                donnees.notifierObservateurs();
            }

            case A -> {
                if(timeline == null){
                    timeline = donnees.lancerJeu(false,150);
                }
                timeline.play();
            }
        }
    }
}