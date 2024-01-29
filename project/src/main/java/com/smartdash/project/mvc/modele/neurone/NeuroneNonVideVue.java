package com.smartdash.project.mvc.modele.neurone;

import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NeuroneNonVideVue extends NeuroneVue {

    public NeuroneNonVideVue(Neurone neurone, Jeu jeu) {
        super(neurone, jeu);
    }

    @Override
    protected Node createNeuroneShape() {
        Circle circle = new Circle(10, Color.BLACK);
        double x = (jeu.getJoueur().getX() + neurone.getX() + 0.5) * jeu.getTailleCase();
        double y = (jeu.getJoueur().getY() + neurone.getY() + 0.5) * jeu.getTailleCase();

        circle.setLayoutX(x);
        circle.setLayoutY(y);

        circle.setStyle("-fx-opacity: 0.5;");

        return circle;
    }

    @Override
    public void updateView() {
        double x = (jeu.getJoueur().getX() + neurone.getX() + 0.5) * jeu.getTailleCase();
        double y = (jeu.getJoueur().getY() + neurone.getY() + 0.5) * jeu.getTailleCase();

        shape.setLayoutX(x);
        shape.setLayoutY(y);

        if(neurone.isActive()) {
            shape.setStyle("-fx-fill: red;");
        } else {
            shape.setStyle("-fx-fill: black;");
        }
    }
}
