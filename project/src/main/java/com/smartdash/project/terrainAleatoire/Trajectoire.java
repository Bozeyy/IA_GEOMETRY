package com.smartdash.project.terrainAleatoire;

import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.modele.objet.ObjetTrajectoire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trajectoire
{
    /**
     * ATTRIBUT
     */
    private int x;
    private int y;
    private int vy;
    private Terrain terrain;
    private boolean aDescendu;
    private boolean aSauter;
    private List<ObjetTrajectoire> listesTrajectoire;
    private Random random = new Random();


    /**
     * Constructeur d'une trajectoire
     * @param terrain terrain vide
     */
    public Trajectoire(Terrain terrain)
    {
        this.terrain = terrain;
        this.x = 0;
        this.y = this.terrain.getLargeur() - 7;
        this.vy = 0;
        this.listesTrajectoire = new ArrayList<>();
    }

    /**
     * Méthode qui permet de récupérer la liste qui correspond à la trajectoire
     * @return retourne une liste d'objet trajectoire
     */
    public List<ObjetTrajectoire> jouer()
    {
        while (verifierFinTerrain())
        {
            updateTrajectoire();
        }

        return this.listesTrajectoire;
    }


    /**
     * Méthode qui permet de générer le déplacement de la trajectoire à partir de probabilité
     */
    public void updateTrajectoire()
    {
        // Boolean qui vérifie si on est sur le sol
        boolean surSol = verifierSurSol();
        this.aSauter = false;
        this.aDescendu = false;

        // On pioche un random double
        double proba = random.nextDouble();


        // Si on est sur le sol
        if(surSol)
        {
            if(proba<0.3)
            {
                actionSauter();
            }
        }
        // Si on n'est pas sur le sol
        else
        {
            // Soit, on tombe sur sauter
            if(proba < 0.15)
            {
                actionSauter();
            }
            // Soit, on tombe sur tomber
            else if (proba< 0.5 && proba>0.15) {
                actionTomber();
            }
        }

        // On crée ensuite l'objet trajectoire que l'on ajoute dans notre liste, si il a saute on ajoute le boolean
        ajouterTrajectoire(aSauter, aDescendu);

        // On avance dans tous les cas
        actionAvancer();

    }

    /**
     * Méthode qui permet de créer un objet trajectoire et de l'ajouter à la liste
     * @param aSauter boolean sauter
     * @param aDescendu boolean descendu
     */
    private void ajouterTrajectoire(boolean aSauter, boolean aDescendu) {
        // Créer un objet trajectoire et l'ajoute à la liste
        ObjetTrajectoire objetTrajectoire = new ObjetTrajectoire(x,y, this.aDescendu, this.aSauter);
        System.out.println("Coordonnée de l'objet x : " + objetTrajectoire.getX() + " y : " + objetTrajectoire.getY());
        this.listesTrajectoire.add(objetTrajectoire);
    }

    /**
     * Méthode qui permet de faire avancer la trajectoire
     */
    private void actionAvancer() {
        // On vérifie si vY > 0 si c'est le cas on monte d'une case
        if(vy>0 && verifierFinTerrain())
        {
            this.y--;
            this.vy--;

        } else if (this.aDescendu) {
            this.y++;
        }

        this.x++;
    }

    /**
     * Méthode qui permet de faire descendre la trajectoire
     */
    private void actionTomber() {
        if (this.vy==0 && !verifierSurSol())
        {
            System.out.println("HAHAHA");
            this.aDescendu = true;
        }
    }

    /**
     * Méthode qui permet de faire sauter la trajectoire
     */
    private void actionSauter() {
        if(!this.verifierDepassementTerrain())
        {
            this.aSauter = true;
            this.vy = 2;
        }
    }

    /**
     * Méthode qui permet de vérifier que l'on ne dépasse pas la limite
     * @return retourne un boolean
     */
    private boolean verifierDepassementTerrain() {
        return y == 4;
    }


    /**
     * Méthode qui permet de vérifier que l'on est sur le sol
     * @return retourne un boolean
     */
    private boolean verifierSurSol() {
        return y == this.terrain.getLargeur() - 7;
    }


    /**
     * Méthode qui permet de vérifier que l'on est à la fin d'un terrain
     * @return retourne un boolean
     */
    private boolean verifierFinTerrain()
    {
        return x != terrain.getLongueur();
    }

}
