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

    private final List<List<NeuroneVue>> reseau;

    public VueReseau(Jeu jeu) throws Exception {
        this.reseau = new ArrayList<>();
        for (int i = 0; i < jeu.getJoueur().getReseau().getModules().size(); i++) {
            Module mod = jeu.getJoueur().getReseau().getModules().get(i);
            List<NeuroneVue> moduleVue = new ArrayList<>();
            for (int j = 0; j < mod.getNeurones().size(); j++) {
                Neurone neurone = mod.getNeurones().get(j);
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
                moduleVue.add(neuroneVue);
            }
            this.reseau.add(moduleVue);
        }
    }

    @Override
    public void actualiser(Sujet sujet) {
        for (int i = 0; i < this.reseau.size(); i++) {
            List<NeuroneVue> module = reseau.get(i);
            boolean actif = ((Jeu)sujet).getJoueur().getReseau().getModules().get(i).isActive();
            for (NeuroneVue neuroneVue : module) {
                neuroneVue.updateView(actif);
            }
        }

    }

    public List<NeuroneVue> getNeurones() {
        List<NeuroneVue> res = new ArrayList<>();
        for (List<NeuroneVue> mod : reseau) {
            res.addAll(mod);
        }
        return res;
    }
}
