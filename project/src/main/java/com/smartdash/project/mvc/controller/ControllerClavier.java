package com.smartdash.project.mvc.controller;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.scene.SceneInterface;
import com.smartdash.project.mvc.scene.SceneJeu;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Timer;

public class ControllerClavier implements EventHandler<KeyEvent> {

    private Jeu donnees;

    private Stage stage;
    Timeline timeline = null;

    public ControllerClavier(Jeu donnees, Stage stage){
        this.donnees = donnees;
        this.stage = stage;
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
                if(donnees.getJoueur().getVivant() && timeline == null){
                    timeline = donnees.lancerHumainGraphique(false,800);
                    timeline.play();
                }
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
                donnees.notifierObservateurs();
            }

            case A -> {
                if(timeline == null){
                    timeline = donnees.lancerJeu(false,150);
                }
                timeline.play();
            }

            case J -> {
                if(timeline != null){
                    timeline.stop();
                    timeline = null;
                }
                donnees.reinitialiser();
                try {
                    if(stage.getScene() instanceof SceneJeu)
                        ((SceneJeu)stage.getScene()).setSceneInterface(donnees,stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                donnees.notifierObservateurs();
            }

            case N -> {
                donnees.setAfficherReseau(!donnees.getAfficherReseau());
                System.out.println(donnees.getAfficherReseau());
                donnees.notifierObservateurs();
            }
        }
    }
}