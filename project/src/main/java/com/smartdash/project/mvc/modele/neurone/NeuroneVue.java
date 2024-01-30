package com.smartdash.project.mvc.modele.neurone;

import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import javafx.scene.Node;

public abstract class NeuroneVue {

    protected Node shape;

    protected Neurone neurone;

    protected Jeu jeu;

    public NeuroneVue(Neurone neurone, Jeu jeu) {
        this.jeu = jeu;
        this.neurone = neurone;
        this.shape = createNeuroneShape();
    }

    protected abstract Node createNeuroneShape();


    public abstract void updateView(boolean actif);

    public Node getShape() {
        return shape;
    }
}