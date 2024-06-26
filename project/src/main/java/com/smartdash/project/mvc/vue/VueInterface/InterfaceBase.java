package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class InterfaceBase extends Pane{

    String nom;
    Stage stage;
    Jeu modele;
    Screen screen;
    List<Node> elements;

    List<InterfaceBase> interfacesConnectees;

    public InterfaceBase(Jeu modele, Stage stage) throws Exception {
        this.modele = modele;
        this.stage = stage;
        this.elements = new ArrayList<>();
        this.screen = Screen.getPrimary();
        this.interfacesConnectees = new ArrayList<>();
        this.nom = this.getClass().getSimpleName();
        init();

    }

    public InterfaceBase(Jeu modele, Stage stage, List<InterfaceBase> interfacesConnectees) throws Exception {
        this.modele = modele;
        this.stage = stage;
        this.elements = new ArrayList<>();
        this.screen = Screen.getPrimary();
        this.interfacesConnectees = interfacesConnectees;
        this.nom = this.getClass().getSimpleName();
        init();

    }

    public void init() throws Exception{
        getChildren().clear();
        defilerImage("background2.png");
    }

    public void ajouterInterfaceConnectee(InterfaceBase interfaceConnectee){
        interfacesConnectees.add(interfaceConnectee);
    }

    public void retirerInterfaceConnectee(InterfaceBase interfaceConnectee){
        interfacesConnectees.remove(interfaceConnectee);
    }

    public InterfaceBase getInterfaceConnectee(String nom){
        for (InterfaceBase interfaceConnectee : interfacesConnectees) {
            if (interfaceConnectee.nom.equals(nom)){
                return interfaceConnectee;
            }
        }
        return null;
    }

    public List<InterfaceBase> getInterfacesConnectees() {
        return interfacesConnectees;
    }

    public Node getElement(String id){
        for (Node element : elements) {
            if (element.getId().equals(id)){
                return element;
            }
        }
        return null;
    }
    
    public void ajouterElement(Node element){
        if (!elements.contains(element))
            elements.add(element);
    }

    public void retirerElement(Node element){
        elements.remove(element);
    }

    public void addTransitionInterface(String nomInterface) {
        InterfaceBase interfaceBase = getInterfaceConnectee(nomInterface);
        if(interfaceBase != null) {
            stage.getScene().setRoot(interfaceBase);
        }
    }

    public void defilerImage(String fichierBackground){
        Image image = new Image(fichierBackground);

        double size = screen.getVisualBounds().getHeight();

        // Créer plusieurs ImageView pour afficher l'image en continu
        ImageView[] imageViews = new ImageView[4];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(image);

            imageViews[i].setFitHeight(size);
            imageViews[i].setFitWidth(size);
            imageViews[i].setTranslateX(i * size);
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
                    imageView.setTranslateX(imageView.getTranslateX() + size * imageViews.length);
                }
            }
        }));

        // Configurer la répétition de l'animation
        timeline.setCycleCount(Animation.INDEFINITE);

        // Démarrer l'animation
        timeline.play();

        setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));

    }
}
