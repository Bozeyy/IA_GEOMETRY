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
import javafx.scene.control.*;
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


public class VueInterfaceIA extends InterfaceChoix implements Observateur{
    VueReseau vueReseau;
    Label scoreJoueur;
    Label apercuJoueur;

    public VueInterfaceIA(Jeu modele, Stage stage, VueInterfaceFirst vueInterfaceFirst) throws Exception {
        super(modele,stage);
        ajouterInterfaceConnectee(vueInterfaceFirst);
        ajouterInterfaceConnectee(new VueInterfaceTerrain(modele, stage, this));
    }

    public void init() throws Exception {
        super.init();
        initVueReseau();
        addTout();
    }


    private void initVueReseau() {

        try {
            if(scoreJoueur == null)
                this.scoreJoueur = new Label("");
            if(apercuJoueur == null)
                this.apercuJoueur = new Label("");

            modele.getJoueur().setX((int) (panePrincipal.getLayoutX() / modele.getTailleCase()));
            modele.getJoueur().setY((int) ((panePrincipal.getLayoutY() + panePrincipal.getPrefHeight() / 2 ) / modele.getTailleCase()));
            vueReseau = new VueReseau(modele);

            panePrincipal.getChildren().clear();

            for (NeuroneVue neuroneVue : vueReseau.getNeurones()) {
                panePrincipal.getChildren().add(neuroneVue.getShape());
            }

            if(choixSecondaire.getValue() != null)
                scoreJoueur.setText("Résultats du joueur: " + choixSecondaire.getValue().substring(choixSecondaire.getValue().length() - 7,choixSecondaire.getValue().length() - 2) + "%");
            else
                scoreJoueur.setText("Résultats du joueur: " + Math.floor(modele.getJoueur().getScoreApprentissage()) + "%");

            panePrincipal.getChildren().addAll(vueReseau,apercuJoueur,scoreJoueur);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRetourArriere() {
        retourArriere.setOnAction(event -> {
            addTransitionInterface("VueInterfaceFirst");
        });
    }

    @Override
    public void addChoixPrincipal() throws Exception {
        Map<String, Map<String, List<Joueur>>> stringMapMap = modele.genererJoueurs(false);


        for (Map.Entry<String, Map<String, List<Joueur>>> stringMapEntry : stringMapMap.entrySet()) {

            TreeItem<String> item = new TreeItem<>(new File(stringMapEntry.getKey()).getName());

            for (Map.Entry<String, List<Joueur>> stringListEntry : stringMapEntry.getValue().entrySet()) {

                TreeItem<String> item2 = new TreeItem<>(stringListEntry.getKey());

                item.getChildren().add(item2);

            }
            choixPrincipal.getRoot().getChildren().add(item);
        }
    }

    @Override
    public void addChoixSecondaire() throws Exception {
        Map<String, Map<String, List<Joueur>>> stringMapMap = modele.genererJoueurs(false);

        choixSecondaire.setValue("Joueur n°0 - score : " + Math.floor(modele.getJoueur().getScoreApprentissage()) + " %");

        choixPrincipal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getChildren() != null && newValue.getChildren().isEmpty()) {

                //ajout des joueurs dans la choicebox
                choixSecondaire.getItems().clear();

                String cleP = newValue.getParent().getValue();
                String cleS = newValue.getValue();

                //ajout des joueurs dans la choicebox
                if (stringMapMap.containsKey(cleP) && stringMapMap.get(cleP).containsKey(cleS)) {
                    for (Joueur joueur : stringMapMap.get(cleP).get(cleS)) {
                        choixSecondaire.getItems().add("Joueur n°" + (stringMapMap.get(cleP).get(cleS).indexOf(joueur) +1) + " - score : " + Math.floor(joueur.getScoreApprentissage()) + " %");
                    }
                }

                //selection du premier joueur
                if(!choixSecondaire.getItems().isEmpty())
                    choixSecondaire.setValue(choixSecondaire.getItems().getFirst());

                //ajout du listener sur la choicebox pour changer les infos du joueur sélectionné
                choixSecondaire.setOnAction(e -> {
                    if (choixSecondaire.getValue() != null) {
                        modele.setJoueur(stringMapMap.get(cleP).get(cleS).get(choixSecondaire.getItems().indexOf(choixSecondaire.getValue())));

                        initVueReseau();
                    }
                });


            }

        });

    }

    @Override
    public void addPanePrincipal() {
        //Titre du pane
        apercuJoueur = new Label("Aperçu du joueur");
        apercuJoueur.setId("apercu");
        apercuJoueur.setPrefSize(panePrincipal.getPrefWidth(),50);
        apercuJoueur.setStyle("-fx-background-color:#debfbf; -fx-font-size: 30px; -fx-alignment: center");
        if (!panePrincipal.getChildren().contains(apercuJoueur))
            panePrincipal.getChildren().add(apercuJoueur);

        //Score
        scoreJoueur.setId("scoreJoueur");
        scoreJoueur.setText("Résultats du joueur: " + Math.floor(modele.getJoueur().getScoreApprentissage()) + "%");
        scoreJoueur.setPrefSize(panePrincipal.getPrefWidth(),50);
        scoreJoueur.setLayoutY(50);
        scoreJoueur.setStyle("-fx-font-size: 15px; -fx-padding: 5px; ");
        if (!panePrincipal.getChildren().contains(scoreJoueur))
            panePrincipal.getChildren().add(scoreJoueur);
    }

    @Override
    public void addValider() {
        valider.setText("Choix du Terrain");

        valider.setOnAction(e -> {
            try {
                addTransitionInterface(getInterfacesConnectees().getLast().nom);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
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
