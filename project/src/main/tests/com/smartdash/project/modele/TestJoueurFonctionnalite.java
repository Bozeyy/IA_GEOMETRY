package com.smartdash.project.modele;

import static org.junit.jupiter.api.Assertions.*;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import org.junit.jupiter.api.*;

public class TestJoueurFonctionnalite
{
    @Test
    public void test_deplacement_bloc_par_bloc()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map1.txt"), new Reseau());
        joueur.updateJoueur();
        assertEquals(joueur.getX(), 1);
    }

    @Test
    public void test_joueur_tombe_quand_dans_air()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map2.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertEquals(joueur.getY(), 3);
    }

    @Test
    public void test_joueur_saute()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map3.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();

        assertEquals(joueur.getY(), 1);
    }

    @Test
    public void test_joueur_saute_atterrissage()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map3.txt"), new Reseau());
        joueur.sauter();

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        assertEquals(joueur.getY(), 2);
    }

    @Test
    public void test_joueur_saute_air()
    {
        Joueur joueur = new Joueur(1,1, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map4.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();

        assertEquals(joueur.getY(),2);
    }

    @Test
    public void test_joueur_saute_deux_fois()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map3.txt"), new Reseau());
        joueur.sauter();
        joueur.sauter();
        joueur.updateJoueur();

        assertEquals(joueur.getY(), 1);
    }

    @Test
    public void test_personnage_sur_bloc()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map1.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertEquals(joueur.getY(), 0);
    }

    @Test
    public void test_personnage_percute_bloc()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map5.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_percute_pic()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map6.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_sur_pic()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map7.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_deux_bloc()
    {
        Joueur joueur = new Joueur(0,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map8.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_un_bloc()
    {
        Joueur joueur = new Joueur(0,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map9.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_deux_piques()
    {
        Joueur joueur = new Joueur(0,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map10.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_un_pique()
    {
        Joueur joueur = new Joueur(0,2, new Terrain("src/main/resources/terrains_test_fonctionnalite/test_map11.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }
}
