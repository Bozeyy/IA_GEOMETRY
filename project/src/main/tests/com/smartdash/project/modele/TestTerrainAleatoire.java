package com.smartdash.project.modele;

import com.smartdash.project.mvc.modele.Terrain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTerrainAleatoire {

    @Test
    public void test_taille()
    {
        Terrain terrain = new Terrain();
        terrain.setMap(terrain.genererTerrainAleatoire(10));

        assertEquals(terrain.getMap().size(),25000);
    }

    @Test
    public void test_longueur()
    {
        Terrain terrain = new Terrain();
        terrain.setMap(terrain.genererTerrainAleatoire(5));

        assertEquals(terrain.getLongueur(),625);
    }

    @Test
    public void test_largeur()
    {
        Terrain terrain = new Terrain();
        terrain.setMap(terrain.genererTerrainAleatoire(5));

        assertEquals(terrain.getLargeur(),20);
    }

    @Test
    public void test_nombre_inf()
    {

        try {
            Terrain terrain = new Terrain();
            terrain.setMap(terrain.genererTerrainAleatoire(-1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"Le nombre de terrain à mélanger est inférieur à 0");
        }

    }

    @Test
    public void test_nombre_sup()
    {

        try {
            Terrain terrain = new Terrain();
            terrain.setMap(terrain.genererTerrainAleatoire(20));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"Le nombre de terrain à mélanger est supérieur au nombre de terrain disponible");
        }

    }

}
