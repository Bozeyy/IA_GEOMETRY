package com.smartdash.project.application;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.scene.SceneInterface;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UserInterface extends javafx.application.Application {
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    @Override
    public void start(Stage stage) throws Exception {
        SceneInterface sceneInterface = new SceneInterface(new Jeu(new Terrain(), new Reseau()), stage);

        stage.setMaximized(true);
        stage.setMinWidth(bounds.getWidth());
        stage.setMinHeight(bounds.getHeight());

        stage.setTitle("SmartDash");
        stage.setScene(sceneInterface);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}