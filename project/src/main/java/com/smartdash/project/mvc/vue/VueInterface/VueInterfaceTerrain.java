package com.smartdash.project.mvc.vue.VueInterface;

import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.Observateur;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VueInterfaceTerrain extends Pane implements Observateur {

    Stage stage;
    Jeu modele;

    VueInterfaceIA vueInterfaceIA;

    public VueInterfaceTerrain(Jeu modele, Stage stage, VueInterfaceIA vueInterfaceIA) throws Exception {
        this.modele = modele;
        this.vueInterfaceIA = vueInterfaceIA;
        this.stage = stage;
        init();

    }

    public void init(){

    }

    @Override
    public void actualiser(Sujet sujet) {

    }
}
