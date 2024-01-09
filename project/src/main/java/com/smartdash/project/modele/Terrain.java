package com.smartdash.project.modele;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private ArrayList<Objet> map = new ArrayList<>();
    private int longueur = 0;
    private int largeur = 0;

    /**
     * Constructeur qui permet de construire un terrain avec une longueur prédéfini
     * @param longueur longueur du niveau
     */
    public Terrain(int longueur)
    {
        // Générations des blocs de la map
        for(int i=-10; i<longueur; i++)
        {
            this.map.add((new Bloc(i,11)));
            this.map.add((new Bloc(i,12)));
            this.map.add((new Bloc(i,13)));
            this.map.add((new Bloc(i,14)));
        }
    }

    /**
     * Constructeur par défaut
     */
    public Terrain()
    {
        // Générations des blocs de la map
        for(int i=-10; i<=100; i++)
        {
            this.map.add((new Bloc(i,11)));
            this.map.add((new Bloc(i,12)));
            this.map.add((new Bloc(i,13)));
            this.map.add((new Bloc(i,14)));
        }
    }

    /**
     * Constructeur grâce a un fichier texte
     * @param fileName nom du fichier texte
     */
    public Terrain(String fileName)
    {
        //On charge la map avec la méthode chargerMap
        this.map = chargerMap(fileName);
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
                    longueur = currentLineLenght + 1;
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

    public ArrayList<Objet> getMap() {
        return map;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }
}
