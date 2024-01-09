package com.smartdash.project.modele;

import com.smartdash.project.modele.objet.Bloc;
import com.smartdash.project.modele.objet.Objet;
import com.smartdash.project.modele.objet.Pique;
import com.smartdash.project.modele.objet.Vide;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTerrain
{
    /**
     * Méthode qui permet de tester le nombre d'objets sur le terrain
     */
    @Test
    public void test_nombre_objet_terrain()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map1.txt");
        int nbObjets = terrain.getMap().size();

        assertEquals(nbObjets, 12);
    }

    /**
     * Méthode qui permet de vérifier le bon type du terrain
     */
    @Test
    public void test_type_objet_terrain()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map12.txt");
        Objet objet = terrain.getMap().get(0);

        assertTrue(objet instanceof Bloc);
    }

    /**
     * Méthode qui permet de vérifier le bon type du terrain
     */
    @Test
    public void test_type_objet_terrain2()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map12.txt");
        Objet objet = terrain.getMap().get(1);

        assertTrue(objet instanceof Vide);
    }

    /**
     * Méthode qui permet de vérifier le bon type du terrain
     */
    @Test
    public void test_type_objet_terrain3()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map12.txt");
        Objet objet = terrain.getMap().get(2);

        assertTrue(objet instanceof Pique);
    }

    /**
     * Methode qui permet de vérifier la longueur de la map
     */
    @Test
    public void test_longueur_map()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map1.txt");
        int longueur = terrain.getLongueur();

        assertEquals(longueur, 7);
    }

    /**
     * Méthode qui permet de vérifier la largeur de la map
     */
    @Test
    public void test_largeur_map()
    {
        Terrain terrain = new Terrain("src/main/resources/terrains_test/test_map1.txt");
        int longueur = terrain.getLargeur();

        assertEquals(longueur, 2);
    }
}
