package com.smartdash.project.mvc.controller;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.scene.SceneJeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerButton implements EventHandler<ActionEvent> {

    Jeu modele;

    Stage stage;

    public ControllerButton(Jeu modele, Stage stage) {
        this.modele = modele;
        this.stage = stage;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        String idButton = ((Node) actionEvent.getSource()).getId();

        switch(idButton) {
            default:
                break;
        }




    }
}
