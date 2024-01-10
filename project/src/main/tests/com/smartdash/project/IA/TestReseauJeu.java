package com.smartdash.project.IA;

import com.smartdash.project.modele.Jeu;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.JoueurAvecCompteur;
import com.smartdash.project.modele.Terrain;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestReseauJeu {

    @Test
    public void testReseauEteinds() {
        Neurone n1 = new NeuronePique(1, 0);
        Neurone n2 = new NeuroneNonPique(0, 1);
        Reseau r = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n1, n2})});

        JoueurAvecCompteur joueur = new JoueurAvecCompteur(0,1, new Terrain("src/main/resources/terrains_test_reseaux/test_mapvide.txt"), r);
        for (int i = 0; i < 15; i++) {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if(sauter)
            {
                joueur.sauter();
            }
            joueur.update();
        }

        int saut = joueur.getNbSauts();
        boolean vivant = joueur.getVivant();

        assertEquals(saut, 0, "le joueur saute");
        assertTrue(vivant, "le joueur est mort");
    }

    @Test
    public void testReseau1NeuronePique() {
        Neurone n1 = new NeuronePique(1, 0);
        Reseau r = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n1})});

        JoueurAvecCompteur joueur = new JoueurAvecCompteur(0,1, new Terrain("src/main/resources/terrains_test_reseaux/test_map2.txt"), r);
        for (int i = 0; i < joueur.getMap().getLongueur(); i++) {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if(sauter)
            {
                joueur.sauter();
            }
            joueur.update();
        }

        int saut = joueur.getNbSauts();
        boolean vivant = joueur.getVivant();

        System.out.println(saut);

        assertEquals(saut, 2, "le joueur saute");
        assertFalse(vivant, "le joueur est vivant");
    }

    @Test
    public void testReseau2Neurone() {
        Neurone n1 = new NeuroneBloc(2, -2);
        Neurone n2 = new NeuroneNonVide(3, -2);
        Reseau r = ReseauFabrique.genererReseau(new Module[]{ModuleFabrique.genererModule(new Neurone[]{n1, n2})});

        JoueurAvecCompteur joueur = new JoueurAvecCompteur(0,2, new Terrain("src/main/resources/terrains_test_reseaux/test_map3.txt"), r);
        for (int i = 0; i < joueur.getMap().getLongueur(); i++) {
            joueur.initialiserReseauActive();
            boolean sauter = joueur.getReseau().isActive();

            if(sauter)
            {
                joueur.sauter();
            }
            joueur.update();
        }

        int saut = joueur.getNbSauts();
        boolean vivant = joueur.getVivant();

        System.out.println(saut);

        assertEquals(saut, 1, "le joueur saute");
        assertTrue(vivant, "le joueur est mort");
    }
}
