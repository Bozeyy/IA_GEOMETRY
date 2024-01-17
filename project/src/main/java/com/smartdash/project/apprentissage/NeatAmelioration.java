package com.smartdash.project.apprentissage;

import com.smartdash.project.modele.Joueur;

import java.util.List;

public class NeatAmelioration extends Neat
{
    public NeatAmelioration()
    {
        super();
    }

    public NeatAmelioration(int maxGenerations)
    {
        super();
        this.maxGenerations = maxGenerations;
    }

    @Override
    public Joueur croisement(Joueur parent1, Joueur parent2)
    {
        // TODO
        return null;
    }

    @Override
    public void mutation(Joueur joueur)
    {
        // TODO
    }

    @Override
    public List<Joueur> selectionnerParents(List<Joueur> population)
    {
        // TODO
        return null;
    }
}
