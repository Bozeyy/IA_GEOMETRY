package com.smartdash.project.mvc.modele.neurone;

import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class NeuronePiqueVue extends NeuroneVue{


    public NeuronePiqueVue(Neurone neurone, Jeu jeu) {
        super(neurone, jeu);
    }

    @Override
    protected Node createNeuroneShape() {
        double triangleSize = 15.0; // Ajustez la taille du triangle selon vos besoins

        double x = (jeu.getJoueur().getX() + neurone.getX() + 0.5) * jeu.getTailleCase();
        double y = (jeu.getJoueur().getY() + neurone.getY() + 0.5) * jeu.getTailleCase();

        double[] points = {
                x - triangleSize / 2, y + triangleSize / 2,
                x, y - triangleSize / 2,
                x + triangleSize / 2, y + triangleSize / 2
        };

        Polygon triangle = new Polygon(points);
        triangle.setFill(Color.GREY);

        triangle.setStyle("-fx-opacity: 0.5;");

        return triangle;
    }

    @Override
    public void updateView() {
        double x = (jeu.getJoueur().getX() + neurone.getX() + 0.5) * jeu.getTailleCase();
        double y = (jeu.getJoueur().getY() + neurone.getY() + 0.5) * jeu.getTailleCase();

        shape.setLayoutX(x);
        shape.setLayoutY(y);

        if(neurone.isActive()) {
            shape.setStyle("-fx-fill: green;");
        } else {
            shape.setStyle("-fx-fill: grey;");
        }
    }
}
