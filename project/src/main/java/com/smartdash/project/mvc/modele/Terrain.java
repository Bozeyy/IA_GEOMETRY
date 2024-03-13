package com.smartdash.project.mvc.modele;

import com.smartdash.project.mvc.modele.objet.*;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.piques.PiqueDroit;
import com.smartdash.project.mvc.modele.objet.piques.PiqueGauche;
import com.smartdash.project.mvc.modele.objet.piques.PiqueRetourne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Terrain {
    private ArrayList<Objet> map = new ArrayList<>();
    private int longueur = 0;
    private int largeur = 0;
    private String nomFichier = "";


    /**
     * Constructeur qui permet de construire un terrain avec pleins de petit terrain
     * @param nbTerrain nombre de terrains que l'on va générer
     */
    public Terrain(int nbTerrain)
    {
        this.map = genererTerrainAleatoire(nbTerrain);
    }


    public Terrain() {
        this.longueur = 99;
        this.largeur = 25;
    }

    /**
     * Constructeur grâce a un fichier texte
     * @param fileName nom du fichier texte
     */
    public Terrain(String fileName)
    {
        //On charge la map avec la méthode chargerMap
        this.map = chargerMap(fileName);
        this.nomFichier = fileName;
    }


    /**
     * Méthode qui permet de générer un terrain à partir d'un fichier texte
     * Pour générer grâce à un fichier texte, les B représente les blocs et les P des piques
     * @param nameFile nom du fichier
     * @return retourne une liste d'objets {bloc/pique}
     */
    private ArrayList<Objet> chargerMap(String nameFile)
    {
        ArrayList<Objet> objets = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile));

            String line;
            int x = 0;
            int y = 0;
            while((line=bufferedReader.readLine()) != null)
            {
                int currentLineLenght = line.length();
                if(currentLineLenght>longueur)
                {
                    longueur = currentLineLenght;
                }

                x=0;
                for(char c: line.toCharArray())
                {
                    if(c == 'B'){
                        Bloc bloc = new Bloc(x,y);
                        objets.add(bloc);
                    }
                    if(c == 'P')
                    {
                        Pique pique = new Pique(x,y);
                        objets.add(pique);
                    }
                    if (c == 'R')
                    {

                        PiqueRetourne piqueRetourne = new PiqueRetourne(x,y);
                        objets.add(piqueRetourne);
                    }
                    if(c == 'G')
                    {
                        PiqueGauche piqueGauche = new PiqueGauche(x,y);
                        objets.add(piqueGauche);
                    }
                    if(c=='D')
                    {
                        PiqueDroit piqueDroit = new PiqueDroit(x,y);
                        objets.add(piqueDroit);
                    }
                    if(c == '.')
                    {
                        Vide vide = new Vide(x,y);
                        objets.add(vide);
                    }
                    x++;
                }
                y++;
            }
            largeur = y;
        }
        catch (IOException e)
        {
            System.out.println("Erreur lors du chargement de la map");
            e.printStackTrace();
        }

        return objets;
    }

    public ArrayList<Objet> genererTerrainAleatoire(int nombreTerrainMelange){
        //créer la liste des objets
        ArrayList<Objet> objets = new ArrayList<>();

        //On récupère le nombre de terrains
        int nbTerrainsApprentissage = getNbTerrainsApprentissage(nombreTerrainMelange);

        //On récupère les terrains d'apprentissage
        //On prend aléatoirement les terrains à mélanger en fonction du nombre de terrains à mélanger
        //On les ajoute le nom des terrains tirés aléatoirement dans une liste
        List<ArrayList<Objet>> terrainsAMelanger = new ArrayList<>(nombreTerrainMelange);
        List<String> nomterrainsAMelanger = new ArrayList<>(nombreTerrainMelange);

        while(nombreTerrainMelange > 0){
            int random = (int) (Math.random() * nbTerrainsApprentissage) + 1;
            String terrain = "src/main/resources/apprentissage/terrain" + random + ".txt";
            if(!nomterrainsAMelanger.contains(terrain)){
                nomterrainsAMelanger.add(terrain);
                terrainsAMelanger.add(chargerMap(terrain));
                nombreTerrainMelange--;
            }
        }

        //On ajoute aux coordonnées X des objects 100 fois leur position dans la liste pour coller les terrains
        //On ajoute les objets dans la liste des objets
        for(int i = 0; i < terrainsAMelanger.size(); i++){
            ArrayList<Objet> terrain = terrainsAMelanger.get(i);
            for(Objet objet : terrain){
                objet.setX(objet.getX() + 100 * i);
                objets.add(objet);
            }
        }

        //On met à jour la longueur et la largeur
        largeur = 20;
        longueur = objets.size()/largeur;

        //On retourne la liste des objets
        return objets;
    }

    /**
     * Méthode qui permet de retourner le nombre de terrains d'apprentissage présent dans un dossier
     * @param nombreTerrainMelange nombre de terrains à mélanger
     * @return retourne le nombre de terrains
     */
    private static int getNbTerrainsApprentissage(int nombreTerrainMelange) {
        int nbTerrainsApprentissage =  Objects.requireNonNull(new File("src/main/resources/apprentissage").listFiles()).length;

        //tester si le nombre de terrains à mélanger est supérieur à 0 et s'il ne dépasse pas le nombre de terrains
        if(nombreTerrainMelange < 0){
            throw new IllegalArgumentException("Le nombre de terrain à mélanger est inférieur à 0");
        }

        if(nombreTerrainMelange > nbTerrainsApprentissage){
            throw new IllegalArgumentException("Le nombre de terrain à mélanger est supérieur au nombre de terrain disponible");
        }
        return nbTerrainsApprentissage;
    }


    public ArrayList<Objet> getMap() {
        return map;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setMap(ArrayList<Objet> map) {
        this.map = map;
    }

    public void addObjet(Objet o) {
        this.map.add(o);
    }

    public void afficherTerrain() {
        System.out.println(this.map.size());
        for (int i = 0; i < getLargeur(); i++) {
            for (int j = 0; j < getLongueur(); j++) {
                int finalI = i;
                int finalJ = j;

                boolean isBloc = getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Bloc);

                boolean isPique = getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Pique);

                boolean isVide = getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof Vide);

                boolean isObjetTrajectoire = getMap().stream()
                        .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI && objet instanceof ObjetTrajectoire);


                if (isBloc) {
                    System.out.print("B");
                } else if (isPique) {
                    System.out.print("P");
                } else if (isObjetTrajectoire) {
                    System.out.print("T");
                } else if (isVide) {
                    System.out.print("."); // Afficher une case vide
                }
            }
            System.out.println(); // Nouvelle ligne pour chaque ligne de la carte
        }

    }
}
