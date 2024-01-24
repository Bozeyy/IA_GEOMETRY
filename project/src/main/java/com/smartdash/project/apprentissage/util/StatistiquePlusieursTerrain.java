package com.smartdash.project.apprentissage.util;

import com.smartdash.project.modele.Joueur;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatistiquePlusieursTerrain extends Statistique
{
    @Override
    public double calculerMoyenneDesScores(List<Joueur> joueurs) throws Exception
    {
        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScoreMoyen).reversed());

        return copieJoueurs.stream()
                .peek(joueur -> {
                    if (joueur.getScoreMoyen() < 0) throw new RuntimeException("Score inférieur à 0");
                })
                .mapToDouble(Joueur::getScoreMoyen)
                .average()
                .orElse(0.0);
    }

    /**
     * Méthode qui permet de calculer les 10 meilleurs scores moyen
     * @param joueurs joueur
     * @return retourne un double qui est la moyenne
     */
    @Override
    public double calculerMoyenne10Meilleurs(List<Joueur> joueurs) throws Exception
    {
        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScoreMoyen).reversed());

        return copieJoueurs.stream()
                .limit(10)
                .peek(joueur -> {
                    if (joueur.getScoreMoyen() < 0) throw new RuntimeException("Score inférieur à 0");
                })
                .mapToDouble(Joueur::getScoreMoyen)
                .average()
                .orElse(0.0);
    }
}
