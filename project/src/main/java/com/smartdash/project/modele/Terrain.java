package com.smartdash.project.modele;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;

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

    /**
     * Constructeur qui permet de construire un terrain avec pleins de petit terrain
     * @param nbTerrain nombre de terrains que l'on va générer
     */
    public Terrain(int nbTerrain)
    {
        this.map = genererTerrainAleatoire(nbTerrain);
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

        //On récupère le nombre de terrain
        int nbTerrainsApprentissage =  Objects.requireNonNull(new File("src/main/resources/apprentissage").listFiles()).length;

        //tester si le nombre de terrain à mélanger est supérieur à 0 et s'il depasse pas le nombre de terrain
        if(nombreTerrainMelange < 0){
            throw new IllegalArgumentException("Le nombre de terrain à mélanger est inférieur à 0");
        }

        if(nombreTerrainMelange > nbTerrainsApprentissage){
            throw new IllegalArgumentException("Le nombre de terrain à mélanger est supérieur au nombre de terrain disponible");
        }

        //On récupère les terrains d'apprentissage
        //On prend aléatoirement les terrains à mélanger en fonction du nombre de terrain à mélanger
        //on les ajoute le nom des terrains tirés aléatoirement dans une liste
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                int finalI = i;
                int finalJ1 = j;
                boolean isObjet = map.stream()
                        .anyMatch(objet -> objet.getX() == finalJ1 && objet.getY() == finalI);

                if (isObjet) {
                    // Vérifier le type d'objet à cette position
                    int finalI1 = i;
                    int finalJ = j;
                    boolean isBloc = map.stream()
                            .anyMatch(objet -> objet.getX() == finalJ && objet.getY() == finalI1 && objet instanceof Bloc);

                    int finalI2 = i;
                    int finalJ2 = j;
                    boolean isPique = map.stream()
                            .anyMatch(objet -> objet.getX() == finalJ2 && objet.getY() == finalI2 && objet instanceof Pique);

                    int finalJ3 = j;
                    int finalI3 = i;
                    boolean isVide = map.stream()
                            .anyMatch(objet -> objet.getX() == finalJ3 && objet.getY() == finalI3 && objet instanceof Vide);


                    if (isBloc) {
                        sb.append("B");
                    } else if (isPique) {
                        sb.append("P");
                    } else if (isVide) {
                        sb.append(".");
                    }
                } else {
                    sb.append(" "); // Si pas d'objet, afficher un espace
                }
            }
            sb.append(System.lineSeparator()); // Nouvelle ligne pour chaque ligne de la carte
        }

        return sb.toString();
    }
*/

    public ArrayList<Objet> getMap() {
        return map;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setMap(ArrayList<Objet> map) {
        this.map = map;
    }
}
