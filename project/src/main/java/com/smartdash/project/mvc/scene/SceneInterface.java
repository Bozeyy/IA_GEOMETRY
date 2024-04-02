package com.smartdash.project.mvc.scene;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.vue.VueInterface.VueInterfaceFirst;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneInterface extends Scene {

    Jeu modele;
    Stage stage;
    SceneJeu sceneJeu;

    public SceneInterface(Jeu modele, Stage stage) throws Exception {
        super(new Pane());
        this.modele = modele;
        this.stage = stage;
        sceneJeu = new SceneJeu(modele, stage,this,"");
        setRoot(init());
    }

    public Parent init() throws Exception {

        //Vueinterface
        VueInterfaceFirst vueInterface = new VueInterfaceFirst(modele, stage);
        modele.enregistrerObservateur(vueInterface);
        return vueInterface;
    }

    public void setSceneJeu(Jeu modele, Stage stage, String couleur) throws Exception {
        this.modele = modele;
        this.stage = stage;
        this.modele.supprimerObservateurs();
        sceneJeu = new SceneJeu(this.modele,this.stage,this, couleur);
        stage.setScene(sceneJeu);
    }

    public Jeu getModele() {
        return modele;
    }

    public void setModele(Jeu modele) {
        this.modele = modele;
    }
}
