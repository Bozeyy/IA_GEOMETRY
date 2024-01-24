package com.smartdash.project.controller;

import com.smartdash.project.modele.Jeu;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControllerSouris implements EventHandler<MouseEvent> {

    private Jeu donnees;

    public ControllerSouris(Jeu donnees){
        this.donnees = donnees;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        MouseButton bouton = mouseEvent.getButton();
        switch (bouton){
            case PRIMARY -> {
                donnees.getJoueur().sauter();
                break;
            }
            case SECONDARY -> {
               donnees.lancerHumainGraphique();
            }
        }
    }
}
