package com.smartdash.project.mvc.modele;

import com.smartdash.project.IA.Constantes;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.Objet;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.Vide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class  Joueur
{
    protected int x;
    protected int y;
    protected int vY;
    protected Terrain map;
    protected Reseau reseau;
    protected boolean vivant;
    protected  boolean fin;
    protected boolean saut;
    protected double scorePartie;
    protected double scoreApprentissage;


    /**
     * Constructeur avec cordonnées
     * @param x, cordonnée du joueur x
     * @param y, cordonnée du joueur y
     * @param mapJeu, terrain autour du joueur
     * @param reseau, réseau de neurone du joueur
     */
    public Joueur(int x, int y, Terrain mapJeu, Reseau reseau)
    {
        this.x = x;
        this.y = y;
        this.vY = 0;
        this.reseau = reseau;
        this.map = mapJeu;
        this.vivant = true;
        this.fin = false;
        this.scorePartie = 0;
        this.scoreApprentissage = 0;
    }

    /**
     * Constructeur sans coordonner
     * @param mapJeu, terrain autour du joueur
     * @param reseau, réseau de neurone du joueur
     */
    public Joueur (Terrain mapJeu, Reseau reseau) {
        this.vY = 0;
        this.reseau = reseau;
        this.map = mapJeu;
        this.vivant = true;
        this.fin = false;
        this.saut = false;
        this.x = 0;
        this.y = mapJeu.getLargeur()-7;
        this.scorePartie = 0;
        this.scoreApprentissage = 0;
    }

    /**
     * Constructeur avec seulement un réseau
     * @param reseau réseau du joueur
     */
    public Joueur(Reseau reseau)
    {
        this.vY = 0;
        this.reseau = reseau;
        this.vivant = true;
        this.fin = false;
        this.x = 0;
        this.scorePartie = 0;
        this.scoreApprentissage = 0;
    }


    /**
     * Méthode qui permet d'initialiser les positions des objets autours du joueur pour les envoyer au réseau de neurones
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
            if (distanceX <= Constantes.X_NEURONES_MAX && distanceY <= Constantes.Y_NEURONES_MAX && distanceX >= Constantes.X_NEURONES_MIN && distanceY >= Constantes.Y_NEURONES_MIN) {
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
    public List<Objet> getObjetsAutour() {
        return this.map.getMap().stream()
                .filter(objet -> Math.abs(this.getX() - objet.getX()) < 2 && Math.abs(this.getY() - objet.getY()) < 2)
                .collect(Collectors.toList());
    }

    /**
     * Méthode qui permet d'actualiser le personnage
     */
    public void updateJoueur()
    {
        // On vérifie que le joueur est bien vivant
        if(this.vivant && !this.fin)
        {
            // On initialise les booleans
            boolean rentrerDansObjet = false, surObjet = false, sauterObjet = false;

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

            // On vérifie si la partie est fini
            this.fin = verificationFinDePartie();
        }
    }

    /**
     * Méthode qui permet de vérifier si une partie est fini
     * @return la fin de partie
     */
    private boolean verificationFinDePartie() {
        return this.x+1 == this.map.getLongueur();
    }

    /**
     * Méthode qui permet de faire la vérification pour sauter
     * @param objetsAutourJoueur les objets autours du joueur
     * @return retourne un boolean
     */
    public boolean verificationSauterObjets(List<Objet> objetsAutourJoueur) {
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
    protected boolean verificationSurObjets(List<Objet> objetsAutourJoueur) {
        boolean surObjet = false;

        for (Objet objet : objetsAutourJoueur)
        {
            if(objet.isInside(new Joueur(this.x, this.y+1, this.map, this.reseau)))
            {
                if(objet instanceof Pique && vY == 0)
                {
                    this.vivant = false;
                }

                surObjet =  (objet instanceof Bloc);
            }
        }

        this.saut = !surObjet;
        return surObjet;
    }

    /**
     * Méthode qui permet de savoir si le joueur rentre dans un objet
     * @param objetsAutourJoueur liste des objets autours du joueur
     * @return retourne un boolean
     */
    protected boolean verificationRentrerDansObjets(List<Objet> objetsAutourJoueur) {
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

        if(surBloc)
        {
            this.vY = 2;
        }
    }

    /**
     * Méthode qui permet de réinitialiser le joueur à 0 pour recommencer une partie
     */
    public void renitialiser() {
        // On met à jour le joueur
        this.vivant = true;
        this.fin = false;
        this.x = 0;
        this.y = this.map.getLargeur()-7;
        this.vY = 0;
        this.reseau.renitialiser();
        this.scorePartie = 0;
        this.scoreApprentissage = 0;
    }


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

    public double getScorePartie() {
        return scorePartie;
    }
    public double getScoreApprentissage() {return scoreApprentissage;}

    public boolean isSaut(){return saut;}

    public void setScorePartie(double x) {
        this.scorePartie = x;
    }
    public void setMap(Terrain terrain)
    {
        this.map = terrain;
        this.y = map.getLargeur()-7;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScoreApprentissage(double score) {
        this.scoreApprentissage = score;
    }
}
