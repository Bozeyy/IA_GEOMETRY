package com.smartdash.project.apprentissage;

import com.smartdash.project.modele.Joueur;

import java.util.List;

public class Statistique {

    // Méthode pour calculer la moyenne des scores des joueurs dans une liste
    public static double calculerMoyenneDesScores(List<Joueur> joueurs) {
        if (joueurs == null || joueurs.isEmpty()) {
            return 0.0; // Retourne 0 si la liste est vide ou nulle
        }

        double sommeDesScores = 0;

        for (Joueur joueur : joueurs) {
            sommeDesScores += joueur.getScore();
        }

        return sommeDesScores / joueurs.size();
    }

}
