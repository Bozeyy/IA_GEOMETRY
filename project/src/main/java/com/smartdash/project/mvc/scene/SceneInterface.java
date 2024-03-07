package com.smartdash.project.mvc.scene;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.vue.VueInterface.VueInterfaceFirst;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneInterface extends Scene {

    Jeu modele;
    Stage stage;

    public SceneInterface(Jeu modele, Stage stage) throws Exception {
        super(new Pane());
        this.modele = modele;
        this.stage = stage;
        setRoot(init());
    }

    public Parent init() throws Exception {

        //Vueinterface
        VueInterfaceFirst vueInterface = new VueInterfaceFirst(modele, stage);
        modele.enregistrerObservateur(vueInterface);
        return vueInterface;
    }

}
