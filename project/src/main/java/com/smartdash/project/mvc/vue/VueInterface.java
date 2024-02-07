package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.controller.ControllerButton;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.Terrain;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class VueInterface extends Pane implements Observateur{

    Stage stage;

    Jeu modele;

    List<Node> buttons;
    VBox vboxButton;

    public VueInterface(Jeu modele, Stage stage){
        this.modele = modele;
        buttons = new ArrayList<>();
        vboxButton = new VBox();
        this.stage = stage;


    }

    public void init(){
        getChildren().clear();
        defilerImage("background2.png");
        initBoutons();
        ajouterButton(new Button("LancerIA"));
        ajouterButton(new Button("LancerJeu"));
        ajouterButton(terrainsToChoiceBox());


    }

    public void defilerImage(String fichierBackground){
        Image image = new Image(fichierBackground);

        // Créer plusieurs ImageView pour afficher l'image en continu
        ImageView[] imageViews = new ImageView[3];
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

    public void ajouterButton(Node node){

        node.setStyle("-fx-background-color: #C8C0BF; -fx-border-color: #000000; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        Node finalNode1 = node;
        node.setOnMouseEntered(e -> finalNode1.setStyle("-fx-background-color: #4BCB3A; -fx-border-color: #C8C0BF; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: #FFFFFF;"));
        Node finalNode = node;
        node.setOnMouseExited(e -> finalNode.setStyle("-fx-background-color: #C8C0BF; -fx-border-color: #000000; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-text-fill: #000000;"));

        if(node instanceof Button){
            Button nodeButton = (Button) node;
            nodeButton.setPrefWidth(250);
            nodeButton.setId(nodeButton.getText());
            nodeButton.setOnAction(new ControllerButton(modele, stage));
            node = nodeButton;
        } else if (node instanceof ChoiceBox){
            ChoiceBox nodeChoiceBox = (ChoiceBox) node;
            nodeChoiceBox.setPrefWidth(250);
            nodeChoiceBox.setId("Terrains");

            node = nodeChoiceBox;
        }


        buttons.add(node);
        vboxButton.getChildren().add(node);
        actualiserBoutons();
    }

    public void suppressionButton(Button button){
        buttons.remove(button);
        vboxButton.getChildren().remove(button);
        actualiserBoutons();
    }

    public void initBoutons(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        vboxButton.setSpacing(10);
        vboxButton.setPadding(new Insets(10));
        vboxButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: #f0f0f0; -fx-border-radius: 5px; -fx-background-radius: 10px; -fx-padding: 10px; -fx-spacing: 10px; -fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-border-style: solid; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 10px; -fx-spacing: 10px; -fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-border-style: solid; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 10px; -fx-spacing: 10px; -fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-border-style: solid; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 10px; -fx-spacing: 10px; -fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-border-style: solid; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-alignment: center; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-border-style: solid; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px; ");
        vboxButton.setPrefWidth(300);
        vboxButton.setPrefHeight(buttons.size() * 100);

        vboxButton.setLayoutX(bounds.getWidth() / 2 - vboxButton.getPrefWidth() / 2);
        vboxButton.setLayoutY(bounds.getHeight() / 2 - vboxButton.getPrefHeight() / 2);

        getChildren().add(vboxButton);

    }

    public void actualiserBoutons(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        vboxButton.setPrefHeight(buttons.size() * 100);

        vboxButton.setLayoutX(bounds.getWidth() / 2 - vboxButton.getPrefWidth() / 2);
        vboxButton.setLayoutY(bounds.getHeight() / 2 - vboxButton.getPrefHeight() / 2);

    }

    public ChoiceBox terrainsToChoiceBox(){
        ChoiceBox choiceBox = new ChoiceBox<>();

        List<Terrain> terrains = modele.genererTerrains();
        for(Terrain terrain : terrains){
            choiceBox.getItems().add(terrain.getNomFichier().replace("src/main/resources/", ""));
        }
        choiceBox.setValue(modele.getTerrain().getNomFichier().replace("src/main/resources/", ""));
        choiceBox.setOnAction(e -> choiceBoxToTerrain(choiceBox));

        return choiceBox;
    }

    public void choiceBoxToTerrain(ChoiceBox choiceBox){
        String terrain = (String) choiceBox.getValue();
        modele.setTerrain(new Terrain("src/main/resources/" + terrain));
    }

    @Override
    public void actualiser(Sujet sujet) {
        actualiserBoutons();
    }
}
