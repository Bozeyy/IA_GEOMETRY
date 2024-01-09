package com.smartdash.project.modele;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.stream;

import com.smartdash.project.IA.Reseau;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class JoueurTest
{
    @Test
    public void test_deplacement_bloc_par_bloc()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test/test_map1.txt"), new Reseau());
        joueur.update();
        assertEquals(joueur.getX(), 1);
    }

    @Test
    public void test_joueur_tombe_quand_dans_air()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test/test_map2.txt"), new Reseau());
        joueur.update();
        joueur.update();
        joueur.update();

        assertEquals(joueur.getY(), 3);
    }

    @Test
    public void test_joueur_saute()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test/test_map3.txt"), new Reseau());
        joueur.sauter();
        joueur.update();

        assertEquals(joueur.getY(), 1);
    }

    @Test
    public void test_joueur_saute_atterrissage()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test/test_map3.txt"), new Reseau());
        joueur.sauter();

        joueur.update();
        joueur.update();
        assertEquals(joueur.getY(), 2);
    }

    @Test
    public void test_joueur_saute_air()
    {
        Joueur joueur = new Joueur(1,1, new Terrain("src/main/resources/terrains_test/test_map4.txt"), new Reseau());
        joueur.sauter();
        joueur.update();

        assertEquals(joueur.getY(),2);
    }

    @Test
    public void test_joueur_saute_deux_fois()
    {
        Joueur joueur = new Joueur(2,2, new Terrain("src/main/resources/terrains_test/test_map3.txt"), new Reseau());
        joueur.sauter();
        joueur.sauter();
        joueur.update();

        assertEquals(joueur.getY(), 1);
    }

    @Test
    public void test_personnage_sur_bloc()
    {
        Joueur joueur = new Joueur(0,0, new Terrain("src/main/resources/terrains_test/test_map1.txt"), new Reseau());
        joueur.update();
        joueur.update();

        assertEquals(joueur.getY(), 0);
    }

    @Test
    public void test_personnage_percute_bloc()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test/test_map5.txt"), new Reseau());
        joueur.update();
        joueur.update();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_percute_pic()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test/test_map6.txt"), new Reseau());
        joueur.update();
        joueur.update();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_sur_pic()
    {
        Joueur joueur = new Joueur(0,1, new Terrain("src/main/resources/terrains_test/test_map7.txt"), new Reseau());
        joueur.update();
        joueur.update();
        joueur.update();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_deux_bloc()
    {
        Joueur joueur = new Joueur(0,2, new Terrain("src/main/resources/terrains_test/test_map8.txt"), new Reseau());

        joueur.update();
        joueur.sauter();
        joueur.update();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_personnage_saute_sur_un_bloc()
    {

    }

    @Test
    public void test_personnage_saute_sur_deux_piques()
    {

    }

    @Test
    public void test_personnage_saute_sur_un_pique()
    {

    }
}
