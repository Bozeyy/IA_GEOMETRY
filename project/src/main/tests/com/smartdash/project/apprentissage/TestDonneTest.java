package com.smartdash.project.apprentissage;

import com.smartdash.project.IA.Reseau;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.modele.Joueur;
import com.smartdash.project.mvc.modele.Terrain;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestDonneTest {
    private static NeatFinal neatFinal = new NeatFinal(15,5);
    private static List<Joueur> population;
    private static Random random = new Random();

    @BeforeAll
    public static void beforeTest() throws Exception {
        population = new ArrayList<>(List.of(new Joueur[]{Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 0), Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 1), Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 2), Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 3), Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 4), Enregistrement.recupererJoueurGeneration("src/main/resources/enregistrement/meilleurs/generation_2056.txt", 5)}));

        for(Joueur joueur : population)
        {
            joueur.setScorePartie(random.nextInt(15));
            joueur.setScoreApprentissage(random.nextInt(15));
        }
    }

    @Test
    public void testModificationPopulation() throws Exception {
        List<Joueur> populationCopie = new ArrayList<>();
        for(Joueur joueur : population) {
            populationCopie.add(new Joueur(joueur.getReseau().clone()));
        }

        // On fait jouer alors tous les joueurs sur 10 terrains aléatoires
        for(Joueur joueur : populationCopie)
        {
            neatFinal.moyenneScoreDonneeTest(joueur,10);
        }

        Joueur j1 = population.get(2);
        System.out.println(j1.getScorePartie());
        Joueur j2 = populationCopie.get(2);
        System.out.println(j2.getScorePartie());

        assertNotEquals(j1.getScoreApprentissage(), j2.getScoreApprentissage());
    }
}
