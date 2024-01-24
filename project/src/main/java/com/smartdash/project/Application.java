package com.smartdash.project;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.vue.VueJeu;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1000, 600);

        Jeu jeu = new Jeu(new Terrain("src/main/resources/apprentissage/terrain1.txt"), new Reseau());

        VueJeu vueJeu = new VueJeu(jeu);
        jeu.enregistrerObservateur(vueJeu);
        borderPane.setCenter(vueJeu);

        /*Rectangle r = new Rectangle(50,50,35,35);
        r.setFill(new ImagePattern(new Image("pique.png")));
        vueJeu.getChildren().add(r);*/

        jeu.notifierObservateurs();


        Scene scene = new Scene(borderPane);
        stage.setTitle("SmartDash");
        stage.setScene(scene);
        stage.show();

        jeu.lancerJeu();
    }

    public static void main(String[] args) {
        launch();
    }
}