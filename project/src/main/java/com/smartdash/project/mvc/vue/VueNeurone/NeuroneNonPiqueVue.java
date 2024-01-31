package com.smartdash.project.mvc.vue.VueNeurone;

import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class NeuroneNonPiqueVue extends NeuroneVue{

    public NeuroneNonPiqueVue(Neurone neurone, Jeu jeu) {
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
        triangle.setFill(Color.WHITE);

        triangle.setStyle("-fx-opacity: 0.5;");

        return triangle;
    }

    @Override
    public void updateView(boolean actif) {
        double x = (jeu.getJoueur().getX() + neurone.getX() + 0.5) * jeu.getTailleCase();
        double y = (jeu.getJoueur().getY() + neurone.getY() + 0.5) * jeu.getTailleCase();

        double triangleSize = 15.0; // Ajustez la taille du triangle selon vos besoins

        double[] points = {
                x - triangleSize / 2, y + triangleSize / 2,
                x, y - triangleSize / 2,
                x + triangleSize / 2, y + triangleSize / 2
        };


        List<Double> l = new ArrayList<>();
        for (double point : points) {
            l.add(point);
        }
        ((Polygon) shape).getPoints().setAll(l);

        if(actif) {
            shape.setStyle("-fx-fill: red;");
            shape.setVisible(true);
        } else {
            shape.setStyle("-fx-fill: white;");
            shape.setVisible(false);
        }
    }
}
