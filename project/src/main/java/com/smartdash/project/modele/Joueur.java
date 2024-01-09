package com.smartdash.project.modele;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Joueur
{
    private int x;
    private int y;
    private int vY;
    private final Terrain map;
    private boolean vivant;
    private Reseau reseau;

    public Joueur(int x, int y, Terrain mapJeu, Reseau reseau)
    {
        this.x = x;
        this.y = y;
        this.vY = 0;
        this.reseau = reseau;
        this.map = mapJeu;
        this.vivant = true;
    }

    /**
     * Méthode qui permet d'initialiser les positions des objets autours du joueur
     */
    public void initialiserReseauActive()
    {
        int x;
        int y;
        String type;

        List<Objet> objets = new ArrayList<>();

        for (Objet objet : this.map.getMap()) {
            int distanceX = Math.abs(objet.getX() - this.getX());
            int distanceY = Math.abs(objet.getY() - this.getY());

            // Récupération des objets dans une zone de 3x3 autour du joueur
            if (distanceX <= 2 && distanceY <= 2) {
                objets.add(objet);
            }
        }

        for(Objet objet : objets)
        {
            x = objet.getX() - this.x;
            y = objet.getY() - this.y;
            type = objet.getType();

            reseau.setActive(x,y,type);
        }

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

                            rentrerDansObjet = verificationRentrerDansObjets(objetsAutourJoueur);
                            if(!rentrerDansObjet) this.x++;

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

                        rentrerDansObjet = verificationRentrerDansObjets(objetsAutourJoueur);
                        if(!rentrerDansObjet) this.x++;

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
            this.x = -1000;
            this.y = -1000;
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
                if(!(objet instanceof Vide))
                {
                    this.vivant = false;
                }

                return !(objet instanceof Vide);
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

                return !(objet instanceof Vide);
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
                if(!(objet instanceof Vide)) this.vivant = false;
                return !(objet instanceof Vide);
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

        if(surBloc && vY==0)
        {
            this.vY = 1;
        }
    }

    // GETTER
    public int getX() {
        return x;
    }

    public int getY() {
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
    public int getvY()
    {
        return vY;
    }
}
