package com.smartdash.project.modele;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;

import java.util.List;
import java.util.stream.Collectors;

public class Joueur
{
    private Reseau reseau;
    private Double x;
    private Double y;
    private Double vY;
    private final Terrain map;
    private boolean vivant;


    public Joueur(Double x, Double y, Terrain mapJeu, Reseau reseau)
    {
        this.x = x;
        this.y = y;
        this.vY = 0.0;
        this.reseau = reseau;
        this.map = mapJeu;
        this.vivant = true;
    }

    /**
     * Méthode qui permet de récupérer la zone pour le réseau de neurone
     * @return retourne une liste d'objet
     */
    public List<Objet> getObjetsReseau()
    {
        return this.map.getMap().stream()
                .filter(objet -> Math.abs(this.getX() - objet.getX()) < 4 && Math.abs(this.getY() - objet.getY()) < 4)
                .collect(Collectors.toList());
    }

    /**
     * Méthode qui permet de renvoyer les objets autour du personnage
     * @return retourne une liste d'objet autours du personnage
     */
    private List<Objet> getObjetsAutour() {
        return this.map.getMap().stream()
                .filter(objet -> Math.abs(this.getX() - objet.getX()) < 2 && Math.abs(this.getY() - objet.getY()) < 2)
                .collect(Collectors.toList());
    }

    /**
     * Méthode qui permet d'actualiser le personnage
     */
    public void update()
    {
        // On vérifie que le joueur est bien vivant
        if(this.vivant)
        {
            // On initialise les booleans
            boolean rentrerDansObjet = false;
            boolean surObjet = false;
            boolean sauterObjet = false;

            // On récupère les objets autours du joueur
            List<Objet> objetsAutourJoueur = getObjetsAutour();

            // On réalise les verifications
            surObjet = verificationSurObjets(objetsAutourJoueur);

            // Si on est sur un objet
            if(surObjet)
            {
                if(vivant)
                {
                    if(this.vY>0)
                    {
                        sauterObjet = verificationSauterObjets(objetsAutourJoueur);
                        if(!sauterObjet)
                        {
                            this.y--;
                            this.vY--;
                        }
                    }

                    rentrerDansObjet = verificationRentrerDansObjets(objetsAutourJoueur);

                    if(!rentrerDansObjet) this.x++;
                }
            }
            else {
                if(this.vY>0)
                {
                    sauterObjet = verificationSauterObjets(objetsAutourJoueur);
                    if(!sauterObjet)
                    {
                        this.y--;
                        this.vY--;
                    }

                }
                else {
                    // On descend, car on n'est pas sur un objet
                    this.y++;

                }

                objetsAutourJoueur = getObjetsAutour();
                rentrerDansObjet = verificationRentrerDansObjets(objetsAutourJoueur);
                if(!rentrerDansObjet) this.x++;
            }
        }
        else
        {
            this.x = -1000.0;
            this.y = -1000.0;
        }
    }

    /**
     * Méthode qui permet de faire la vérification pour sauter
     * @param objetsAutourJoueur les objets autours du joueur
     * @return retourne un boolean
     */
    private boolean verificationSauterObjets(List<Objet> objetsAutourJoueur) {
        for (Objet objet : objetsAutourJoueur)
        {
            if(objet.isInside(new Joueur(this.x, this.y-1, this.map, this.reseau)))
            {
                if(objet instanceof Pique)
                {
                    this.vivant = false;
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui permet de vérifier si le joueur est sur un objet
     * @param objetsAutourJoueur les objets autours du joueur
     * @return retourne un boolean
     */
    private boolean verificationSurObjets(List<Objet> objetsAutourJoueur) {
        for (Objet objet : objetsAutourJoueur)
        {
            if(objet.isInside(new Joueur(this.x, this.y+1, this.map, this.reseau)))
            {
                if(objet instanceof Pique)
                {
                    this.vivant = false;
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui permet de savoir si le joueur rentre dans un objet
     * @param objetsAutourJoueur liste des objets autours du joueur
     * @return retourne un boolean
     */
    private boolean verificationRentrerDansObjets(List<Objet> objetsAutourJoueur) {
        for (Objet objet : objetsAutourJoueur)
        {
            if(objet.isInside(new Joueur(this.x+1, this.y, this.map, this.reseau)))
            {
                this.vivant = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui permet de sauter
     */
    public void sauter()
    {
        // On vérifie que le joueur est bien sur un bloc
        List<Objet> objetsAutours = getObjetsAutour();
        boolean surBloc = verificationSurObjets(objetsAutours);

        if(surBloc)
        {
            this.vY = 2.0;
        }
    }

    // GETTER
    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Terrain getMap() {
        return map;
    }
    public boolean getVivant()
    {
        return vivant;
    }

    public Reseau getReseau()
    {
        return reseau;
    }
}
