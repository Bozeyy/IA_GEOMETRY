package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import com.smartdash.project.mvc.vue.VueNeurone.NeuroneVue;
import com.smartdash.project.mvc.vue.VueReseau;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Map;


public class VueInterfaceIA extends Pane implements Observateur {

    Stage stage;

    VueInterfaceFirst vueInterfaceFirst;

    Jeu modele;

    TreeView<String> treeView;

    ChoiceBox<String> joueurs = new ChoiceBox<>();

    VueReseau vueReseau;
    Pane paneJoueur;

    Button retourArriere = new Button();

    public VueInterfaceIA(Jeu modele, Stage stage, VueInterfaceFirst vueInterfaceFirst) throws Exception {
        this.modele = modele;
        this.vueInterfaceFirst = vueInterfaceFirst;
        treeView = new TreeView<String>();
        this.stage = stage;
        init();


    }

    public void init() throws Exception {
        getChildren().clear();
        defilerImage("background2.png");
        initTreeview();
        initChoiceBox();
        initStackPaneJoueur();
        initVueReseau();
        initRetourArriere();

    }

    private void initRetourArriere() {
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

        // Ajout d'un listener pour le clic sur le bouton
        retourArriere.setOnAction(event -> {
            modele.enregistrerObservateur(vueInterfaceFirst);
            stage.getScene().setRoot(vueInterfaceFirst);
        });

        retourArriere.setPrefSize(85,40);
        retourArriere.setLayoutX(50);
        retourArriere.setLayoutY(50);

        getChildren().add(retourArriere);

    }

    private void initStackPaneJoueur() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        paneJoueur = new Pane();
        paneJoueur.setPrefSize(bounds.getWidth() - treeView.getPrefWidth() - 150, bounds.getHeight() - 100 - joueurs.getPrefHeight() - 50);
        paneJoueur.setLayoutX(50 + treeView.getPrefWidth() + 50);
        paneJoueur.setLayoutY(50);
        paneJoueur.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");

        getChildren().add(paneJoueur);
    }

    private void initVueReseau() {

        try {
            modele.getJoueur().setX((int) ((paneJoueur.getLayoutX() + paneJoueur.getPrefWidth() / 2) / modele.getTailleCase()));
            modele.getJoueur().setY((int) ((paneJoueur.getLayoutY() + paneJoueur.getPrefHeight() / 2 ) / modele.getTailleCase()));
            vueReseau = new VueReseau(modele);

            paneJoueur.getChildren().clear();
            for (NeuroneVue neuroneVue : vueReseau.getNeurones()) {
                paneJoueur.getChildren().add(neuroneVue.getShape());
            }

            //vueReseau.actualiser(modele);

            paneJoueur.getChildren().add(vueReseau);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initChoiceBox() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        joueurs = new ChoiceBox<>();
        joueurs.setPrefSize(300, 50);
        joueurs.setLayoutX(bounds.getWidth() - joueurs.getPrefWidth() * 2 - 100);
        joueurs.setLayoutY(bounds.getHeight() - 100);

        getChildren().add(joueurs);


    }

    private void initTreeview() throws Exception {
        Map<String, Map<String, List<Joueur>>> stringMapMap = modele.genererJoueurs(false);



        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        treeView.setLayoutX(50);
        treeView.setLayoutY(150);
        treeView.setPrefSize(300, bounds.getHeight() - 200);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.setRoot(new TreeItem<>("Root"));
        treeView.setStyle("-fx-font-size: 15px;");


        for (Map.Entry<String, Map<String, List<Joueur>>> stringMapEntry : stringMapMap.entrySet()) {

            TreeItem<String> item = new TreeItem<>(new File(stringMapEntry.getKey()).getName());

            for (Map.Entry<String, List<Joueur>> stringListEntry : stringMapEntry.getValue().entrySet()) {

                TreeItem<String> item2 = new TreeItem<>(stringListEntry.getKey());

                item.getChildren().add(item2);

            }
            treeView.getRoot().getChildren().add(item);
        }

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getChildren() != null && newValue.getChildren().size() == 0) {

                //ajout des joueurs dans la choicebox
                joueurs.getItems().clear();

                String cleP = newValue.getParent().getValue();
                String cleS = newValue.getValue();

                //ajout des joueurs dans la choicebox
                if (stringMapMap.containsKey(cleP) && stringMapMap.get(cleP).containsKey(cleS)) {
                    for (Joueur joueur : stringMapMap.get(cleP).get(cleS)) {
                        joueurs.getItems().add("Joueur n°" + (stringMapMap.get(cleP).get(cleS).indexOf(joueur) +1));
                    }
                }

                //style de la choicebox
                joueurs.setStyle("-fx-font-size: 15px;");

                //selection du premier joueur
                if(!joueurs.getItems().isEmpty())
                    joueurs.setValue(joueurs.getItems().getFirst());

                //ajout du listener sur la choicebox pour changer les infos du joueur sélectionné
                joueurs.setOnAction(e -> {
                    if (joueurs.getValue() != null) {
                        modele.setJoueur(stringMapMap.get(cleP).get(cleS).get(joueurs.getItems().indexOf(joueurs.getValue())));
                        initVueReseau();
                    }
                });


            }



        });

        getChildren().add(treeView);

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
