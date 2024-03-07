package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public abstract class InterfaceChoix extends InterfaceBase{
    TreeView<String> choixPrincipal;
    ChoiceBox<String> choixSecondaire;
    Pane panePrincipal;
    Button retourArriere;
    Button valider;

    public InterfaceChoix(Jeu modele, Stage stage) throws Exception {
        super(modele, stage);
    }

    public InterfaceChoix(Jeu modele, Stage stage, List<InterfaceBase> interfacesConnectees) throws Exception {
        super(modele, stage, interfacesConnectees);
    }
    @Override
    public void init() throws Exception {
        getChildren().clear();
        defilerImage("background2.png");
        initChoixPrincipal();
        initChoixSecondaire();
        initPanePrincipal();
        initRetourArriere();
        initValider();
    }

    private void initValider() {
    }

    private void initRetourArriere() {
        retourArriere = new Button();

        // Création d'un triangle pour simuler une flèche vers la gauche
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(0.0, 10.0, 20.0, 0.0, 20.0, 20.0);
        arrow.setFill(Color.BLACK);

        // Création d'un trait pour simuler une tige à la flèche
        Rectangle stem = new Rectangle(20, 5, 20, 10);
        stem.setFill(Color.BLACK);

        // Bouton personnalisé avec la flèche et la tige
        retourArriere.setGraphic(new Pane(stem, arrow)); // Ajout de la flèche et du trait dans un StackPane
        retourArriere.setPadding(new Insets(10, 20, 10, 20)); // Augmentation de la taille du bouton

        retourArriere.setPrefSize(85,40);
        retourArriere.setLayoutX(50);
        retourArriere.setLayoutY(50);

        getChildren().add(retourArriere);
    }

    private void initPanePrincipal() {
        Rectangle2D bounds = screen.getVisualBounds();

        panePrincipal = new Pane();
        panePrincipal.setPrefSize(bounds.getWidth() - choixPrincipal.getPrefWidth() - 150, bounds.getHeight() - 100 - choixSecondaire.getPrefHeight() - 50);
        panePrincipal.setLayoutX(50 + choixPrincipal.getPrefWidth() + 50);
        panePrincipal.setLayoutY(50);
        panePrincipal.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");

        getChildren().add(panePrincipal);
    }

    private void initChoixSecondaire() {
        Rectangle2D bounds = screen.getVisualBounds();

        choixSecondaire = new ChoiceBox<>();
        choixSecondaire.setPrefSize(300, 50);
        choixSecondaire.setLayoutX(bounds.getWidth() - choixSecondaire.getPrefWidth() * 2 - 100);
        choixSecondaire.setLayoutY(bounds.getHeight() - 100);
        choixSecondaire.setId("choixSecondaire");
        ajouterElement(choixSecondaire);

        getChildren().add(choixSecondaire);
    }

    private void initChoixPrincipal() {
        Rectangle2D bounds = screen.getVisualBounds();

        choixPrincipal = new TreeView<>();
        choixPrincipal.setLayoutX(50);
        choixPrincipal.setLayoutY(150);
        choixPrincipal.setPrefSize(300, bounds.getHeight() - 200);
        choixPrincipal.setShowRoot(false);
        choixPrincipal.setEditable(true);
        choixPrincipal.setRoot(new TreeItem<>("Root"));
        choixPrincipal.setStyle("-fx-font-size: 15px;");
        choixPrincipal.setId("choixPrincipal");
        ajouterElement(choixPrincipal);

        getChildren().add(choixPrincipal);
    }

    public abstract void addRetourArriere();
    public abstract void addChoixPrincipal();
    public abstract void addChoixSecondaire() throws Exception;
    public abstract void addPanePrincipal();
    public abstract void addValider();
    public void addTout() throws Exception {
        addRetourArriere();
        addChoixPrincipal();
        addChoixSecondaire();
        addPanePrincipal();
        addValider();
    }

}
