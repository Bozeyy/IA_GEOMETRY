package com.smartdash.project.modele;

import com.smartdash.project.IA.Reseau;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestJoueurSituation
{

    @Test
    public void test_terrain10_saut_reussi_apres_saut(){
        Joueur joueur = new Joueur(2,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        assertEquals(joueur.getY(), 3);
        assertEquals(joueur.getX(), 4);

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertEquals(joueur.getX(), 10);
        assertEquals(joueur.getY(), 5);


    }

    @Test
    public void test_terrain10_atterrissage_apres_saut(){
        Joueur joueur = new Joueur(2,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        assertEquals(joueur.getY(), 3);
        assertEquals(joueur.getX(), 4);

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertEquals(joueur.getX(), 7);
        assertEquals(joueur.getY(), 5);


    }

}
