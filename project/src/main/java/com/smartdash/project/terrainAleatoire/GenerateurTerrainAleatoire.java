package com.smartdash.project.terrainAleatoire;

import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.Objet;
import com.smartdash.project.mvc.modele.objet.ObjetTrajectoire;
import com.smartdash.project.mvc.modele.objet.Vide;
import com.smartdash.project.mvc.modele.objet.piques.Pique;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateurTerrainAleatoire {

    private Terrain terrain;
    
    private ArrayList<Objet> objetsReserves = new ArrayList<Objet>();

    private List<ObjetTrajectoire> trajectoires;

    public static void main(String[] args) throws Exception {
        GenerateurTerrainAleatoire g = new GenerateurTerrainAleatoire();
        Terrain t = g.genererTerrainAleatoire();
        t.afficherTerrain();
    }

    public Terrain genererTerrainAleatoire() throws Exception {
        terrain = new Terrain();
        reserverSolNiveau();
        genererTrajectoire();
        genererObjetsObligatoires();
        genererTerrain();

        return terrain;
    }


    private void reserverSolNiveau() {
        for (int i = 0; i < this.terrain.getLongueur(); i++) {
            this.objetsReserves.add(new Bloc(i, terrain.getLargeur()-6));
        }
    }

    private void genererObjetsObligatoires() {

        for (int i = 0; i < this.trajectoires.size()-1; i++) {
            ObjetTrajectoire objetCourant = this.trajectoires.get(i);
            ObjetTrajectoire objetSuivant = this.trajectoires.get(i+1);

            int yCourant = objetCourant.getY();
            int ySuivant = objetSuivant.getY();

            Objet objet;

            // si le joueur ne monte pas et ne tombe on met un bloc en dessous
            if (ySuivant == yCourant) {
                objet = new Bloc(objetCourant.getX(), objetCourant.getY()+1);
                if (! this.objetsReserves.contains(objet)) {
                    this.objetsReserves.add(objet);
                }
            }

            // si le joueur descend on met une case vide en dessous
            if (ySuivant > yCourant) {
                objet = new Vide(objetCourant.getX(), objetCourant.getY()+1);
                this.objetsReserves.add(objet);
            }

            // si le joueur monte on met une case vide au dessus
            if (ySuivant < yCourant) {
                objet = new Vide(objetCourant.getX(), objetCourant.getY()-1);
                this.objetsReserves.add(objet);
                // si le joueur n est pas en debut de saut on met une case non bloc en dessous pour eviter qu'il puisse reactiver un saut
            }

            // si il appuie sur sauter on bloc le chemin
            if (objetCourant.isSaut()) {
                objet = new Bloc(objetCourant.getX(), objetCourant.getY()+1);
                if (! this.objetsReserves.contains(objet)) {
                    this.objetsReserves.add(objet);
                }
                ajouterPiege(objetCourant, objetSuivant);
                // piege + bloc en dessous
            }
        }
    }

    private void ajouterPiege(ObjetTrajectoire objetCourant, ObjetTrajectoire objetSuivant) {
        // si on saute a la trajectoire suivante, on ne met pas de piege pour laisser la place au sol
        if (!objetSuivant.isSaut()) {
            if (!this.objetsReserves.contains(new Bloc(objetCourant.getX()+1, objetCourant.getY()+1))) {

                Random rand = new Random();
                int numPiege = rand.nextInt(3);
                switch (numPiege) {
                    case 0:
                        //generation piege pique
                        Objet piege = new Pique(objetCourant.getX() + 1, objetCourant.getY());
                        Objet bloc = new Bloc(objetCourant.getX() + 1, objetCourant.getY() + 1);
                        this.objetsReserves.add(piege);
                        this.objetsReserves.add(bloc);
                        break;

                    case 1:
                        // generation piege vide
                        Objet vide = new Vide(objetCourant.getX() + 1, objetCourant.getY() + 1);
                        this.objetsReserves.add(vide);
                        break;
                }
            } else {
                Objet piege = new Pique(objetCourant.getX() + 1, objetCourant.getY());
                this.objetsReserves.add(piege);
            }
        }


    }

    private void genererTerrain() throws Exception {
        for (int x = 0; x < terrain.getLongueur(); x++) {
            for (int y = 0; y < terrain.getLargeur(); y++) {
                Objet o;
                if (estTrajectoire(x,y)) {
//                    o = getObjetTrajectoire(x,y);
                    o = new Vide(x,y);
                    this.terrain.addObjet(o);
                } else {
                    if (objetExistant(x, y)) {
                        o = getObjetExistant(x, y);
                        this.terrain.addObjet(o);
                    }
                }
            }
        }

        // on parcourt tous les objets trajectoires pour generer les objets aleatoires autour
        for (ObjetTrajectoire ot : this.trajectoires) {
            for (int y = 0; y < terrain.getLargeur(); y++) {
                if (y >= ot.getY()-4 && y <= ot.getY()+4 && y < terrain.getLargeur() -6) {
                    if (!objetExistant(ot.getX(), y) && y != ot.getY() && y < terrain.getLargeur() - 6) {
                        this.terrain.addObjet(creerBlocAleatoire(ot.getX(), y));
                    }
                } else if (!objetExistant(ot.getX(), y)) {
                    this.terrain.addObjet(new Vide(ot.getX(), y));
                }
            }

        }
    }

    private Objet creerBlocAleatoire(int x, int y) {
        Random rand = new Random();
        // 60% vide 20% bloc 20% pique
        int propa = rand.nextInt(100);
        if (propa < 60) {
            return new Vide(x,y);
        } else {
            if (propa < 80) {
                return  new Bloc(x,y);
            } else {
                return new Pique(x,y);
            }
        }

    }


    private boolean estTrajectoire(int x, int y) {
        for (ObjetTrajectoire o : this.trajectoires) {
            if (o.getX() == x && o.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean objetExistant(int x, int y) {
        for (Objet o : this.objetsReserves) {
            if (o.getX() == x && o.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private Objet getObjetTrajectoire(int x, int y) throws Exception {
        for (ObjetTrajectoire o : this.trajectoires) {
            if (o.getX() == x && o.getY() == y) {
                return o;
            }
        }
        throw new Exception("Coordonnée non existante dans trajectoire");
    }

    private Objet getObjetExistant(int x, int y) throws Exception {
        for (Objet o : this.objetsReserves) {
            if (o.getX() == x && o.getY() == y) {
                return o;
            }
        }
        throw new Exception("Coordonnée non existante dans objetsExistants");
    }

    private void genererTrajectoire() {
        Trajectoire traj = new Trajectoire(this.terrain);
        this.trajectoires = traj.jouer();
        boolean b = this.trajectoires.get(0) instanceof Vide;
        System.out.println(b);

    }
}
