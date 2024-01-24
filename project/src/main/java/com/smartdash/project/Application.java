package com.smartdash.project;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Terrain;
import com.smartdash.project.vue.VueJeu;
import com.smartdash.project.vue.VuePique;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1000, 600);

        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), new Reseau());

        borderPane.setCenter((Node)jeu.getVueJeu());

        /*Rectangle r = new Rectangle(50,50,35,35);
        r.setFill(new ImagePattern(new Image("pique.png")));
        vueJeu.getChildren().add(r);*/


        Scene scene = new Scene(borderPane);
        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}