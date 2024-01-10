package com.smartdash.project.modele;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.objet.Objet;

import java.util.List;

public class JoueurAvecCompteur extends Joueur{

    private int nbSauts
            ;
    public JoueurAvecCompteur(int x, int y, Terrain mapJeu, Reseau reseau) {

        super(x, y, mapJeu, reseau);
        nbSauts = 0;
    }

    @Override
    public void sauter() {  // On vérifie que le joueur est bien sur un bloc
        List<Objet> objetsAutours = getObjetsAutour();
        boolean surBloc = verificationSurObjets(objetsAutours);

        if(surBloc && vY==0)
        {
            this.vY = 1;
            nbSauts++;
        }

    }

    public int getNbSauts() {
        return nbSauts;
    }
}
