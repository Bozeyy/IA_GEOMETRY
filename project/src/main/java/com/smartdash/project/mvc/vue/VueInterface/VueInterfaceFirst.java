package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VueInterfaceFirst extends Pane implements Observateur {

    Stage stage;

    Jeu modele;

    StackPane play;

    public VueInterfaceFirst(Jeu jeu, Stage stage) {
        this.modele = jeu;
        this.stage = stage;
        init();
    }

    public void init(){
        getChildren().clear();
        defilerImage("background2.png");
        setPlay();
    }

    public void setPlay(){
        Image image = new Image("play.png");
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
            System.out.println("Cercle cliqué !");
        });

        stackPane.setLayoutX(Screen.getPrimary().getBounds().getWidth()/2 - 150);
        stackPane.setLayoutY(Screen.getPrimary().getBounds().getHeight()/2 - 150);

        play = stackPane;
        getChildren().add(play);
    }

    public void defilerImage(String fichierBackground){
        Image image = new Image(fichierBackground);

        // Créer plusieurs ImageView pour afficher l'image en continu
        ImageView[] imageViews = new ImageView[4];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(image);
            imageViews[i].setTranslateX(i * image.getWidth());
        }

        // Ajouter les ImageView à la scène
        getChildren().addAll(imageViews);

        // Créer un écouteur d'événements de chronométrage pour déplacer les images
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
            // Déplacer les images vers la gauche
            for (ImageView imageView : imageViews) {
                imageView.setTranslateX(imageView.getTranslateX() - 1);

                // Si une image est complètement sortie de l'écran, réinitialiser sa position
                if (imageView.getBoundsInParent().getMaxX() <= 0) {
                    imageView.setTranslateX(imageView.getTranslateX() + image.getWidth() * imageViews.length);
                }
            }
        }));

        // Configurer la répétition de l'animation
        timeline.setCycleCount(Animation.INDEFINITE);

        // Démarrer l'animation
        timeline.play();

    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
