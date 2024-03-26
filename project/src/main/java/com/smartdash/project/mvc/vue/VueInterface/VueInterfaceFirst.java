package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class VueInterfaceFirst extends InterfaceBase implements Observateur{

    public VueInterfaceFirst(Jeu jeu, Stage stage) throws Exception {
        super(jeu, stage);
    }

    public void init() throws Exception {
        super.init();
        setPlay("play2.png");
    }

    public void setPlay(String fichierBouton) throws Exception {
        ajouterInterfaceConnectee(new VueInterfaceIA(modele, stage, this));
        Image image = new Image(fichierBouton);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300); // Ajuster la largeur de l'image
        imageView.setFitHeight(300); // Ajuster la hauteur de l'image

        // Création du cercle
        Circle circle = new Circle(80);
        circle.setFill(Color.TRANSPARENT); // Remplissage transparent pour le cercle

        // Création d'un StackPane pour superposer l'imageview et le cercle
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, circle);

        // Création de l'animation d'agrandissement pour le cercle
        ScaleTransition circleScaleTransition = new ScaleTransition(Duration.millis(200), circle);
        circleScaleTransition.setToX(1.1);
        circleScaleTransition.setToY(1.1);

        // Création de l'animation d'agrandissement pour l'image
        ScaleTransition imageScaleTransition = new ScaleTransition(Duration.millis(200), imageView);
        imageScaleTransition.setToX(1.1);
        imageScaleTransition.setToY(1.1);

        // Gestion de l'événement de survol de la souris
        stackPane.setOnMouseEntered(event -> {
            circleScaleTransition.play();
            imageScaleTransition.play();
        });

        // Gestion de l'événement de sortie de la souris
        stackPane.setOnMouseExited(event -> {
            circleScaleTransition.stop();
            imageScaleTransition.stop();
            circle.setScaleX(1.0);
            circle.setScaleY(1.0);
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });

        // Gestion de l'événement de clic sur le cercle
        stackPane.setOnMouseClicked(event -> {
            addTransitionInterface(getInterfacesConnectees().getLast().nom);
        });

        stackPane.setLayoutX(screen.getBounds().getWidth()/2 - 150);
        stackPane.setLayoutY(screen.getBounds().getHeight()/2 - 150);

        stackPane.setId("playBouton");
        ajouterElement(stackPane);

        getChildren().add(stackPane);
    }



    @Override
    public void actualiser(Sujet sujet) {

    }
}
