package com.smartdash.project.mvc.vue;

import com.smartdash.project.IA.Module;
import com.smartdash.project.IA.neurones.Neurone;
import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.modele.neurone.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class VueReseau extends Pane implements Observateur {

    private final List<NeuroneVue> neurones;

    public VueReseau(Jeu jeu) throws Exception {
        this.neurones = new ArrayList<>();
        for (Module mod : jeu.getJoueur().getReseau().getModules()) {
            for (Neurone neurone : mod.getNeurones()) {
                NeuroneVue neuroneVue;
                switch (neurone.getType()) {
                    case 'v':
                        neuroneVue = new NeuroneVideVue(neurone,jeu);
                        break;
                    case 'b':
                        neuroneVue = new NeuroneBlocVue(neurone, jeu);
                        break;
                    case 'p':
                        neuroneVue = new NeuronePiqueVue(neurone, jeu);
                        break;
                    case 'a':
                        neuroneVue = new NeuroneActifVue(neurone, jeu);
                        break;
                    case 'q':
                        neuroneVue = new NeuroneNonPiqueVue(neurone, jeu);
                        break;
                    case 'w':
                        neuroneVue = new NeuroneNonVideVue(neurone, jeu);
                        break;
                    case 'd':
                        neuroneVue = new NeuroneNonBlocVue(neurone, jeu);
                        break;
                    default:
                        throw new Exception("neurone non reconnu");
                }
                neurones.add(neuroneVue);
            }
        }
    }

    @Override
    public void actualiser(Sujet sujet) {
        for (NeuroneVue neuroneVue : this.neurones) {
            neuroneVue.updateView();
        }

    }

    public List<NeuroneVue> getNeurones() {
        return neurones;
    }
}
