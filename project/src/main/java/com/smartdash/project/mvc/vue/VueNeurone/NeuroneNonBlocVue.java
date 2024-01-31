package com.smartdash.project.mvc.vue.VueNeurone;

import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NeuroneNonBlocVue extends NeuroneVue {

    public NeuroneNonBlocVue(Neurone neurone, Jeu jeu) {
        super(neurone, jeu);
    }

    @Override
    protected Node createNeuroneShape() {
        Rectangle rectangle = new Rectangle(20, 20, Color.WHITE); // Exemple : un rectangle rouge pour les neurones Bloc
        // Ajoutez d'autres propriétés de forme ou de style en fonction de vos besoins
        double x = (jeu.getJoueur().getX() + neurone.getX()) * jeu.getTailleCase() + 5; // 10 est la moitié de la largeur du rectangle
        double y = (jeu.getJoueur().getY() + neurone.getY()) * jeu.getTailleCase() + 5; // 10 est la moitié de la hauteur du rectangle

        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);

        rectangle.setStyle("-fx-opacity: 0.5;");

        return rectangle;
    }

    @Override
    public void updateView(boolean actif) {
        double x = (jeu.getJoueur().getX() + neurone.getX()) * jeu.getTailleCase() + 5;
        double y = (jeu.getJoueur().getY() + neurone.getY()) * jeu.getTailleCase() + 5;

        shape.setLayoutX(x);
        shape.setLayoutY(y);

        if(actif) {
            shape.setStyle("-fx-fill: red;");
            shape.setVisible(true);
        } else {
            shape.setStyle("-fx-fill: white;");
            shape.setVisible(false);
        }
    }
}
