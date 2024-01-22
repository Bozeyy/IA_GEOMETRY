package com.smartdash.project.IA.util;

import com.smartdash.project.IA.ReseauFabrique;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.modele.Joueur;
import com.smartdash.project.modele.Terrain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnregistrement {

    String pathname = "src/main/resources/enregistrement/17-01-2024_12-12-01";

    @Test
    public void test_stringToPopulation_taille() throws IOException {
        List<Joueur> population = Enregistrement.stringToPopulation(pathname + "/generation_0.txt");

        assertEquals(1000, population.size());
    }

    @Test
    public void test_stringToPopulation_wrong_pathname() throws IOException {
        List<Joueur> population = Enregistrement.stringToPopulation(pathname + "/generation_1111.txt");

        assertNull(population);

    }

    @Test
    public void test_stringToPopulation_wrong_pathname2() throws IOException {
        List<Joueur> population = Enregistrement.stringToPopulation(pathname + "/generation_0");

        assertNull(population);

    }

    @Test
    public void test_stringToPopulation_gen0_reseau_premier_joueur() throws Exception {

        Joueur j = Enregistrement.recupererJoueurGeneration(pathname + "/generation_0.txt", 0);

        String reseau = j.getReseau().toString();

        String excpeted = "Reseau{\n" +
                "\tModule{\n" +
                "\t\tNeurone Vide{x=0, y=-1, active=false}\n" +
                "\t\tNeurone Actif{x=1, y=0, active=false}\n" +
                "\t\tNeurone Non Vide{x=2, y=0, active=false}\n" +
                "\t}\n" +
                "\tModule{\n" +
                "\t\tNeurone Pique{x=3, y=0, active=false}\n" +
                "\t\tNeurone Non Vide{x=3, y=1, active=false}\n" +
                "\t\tNeurone Vide{x=1, y=1, active=false}\n" +
                "\t}\n" +
                "\tModule{\n" +
                "\t\tNeurone Non Vide{x=2, y=-2, active=false}\n" +
                "\t\tNeurone Actif{x=1, y=1, active=false}\n" +
                "\t\tNeurone Non Vide{x=2, y=0, active=false}\n" +
                "\t}\n" +
                "}";

        assertEquals(excpeted, reseau);

    }

    @Test
    public void test_stringToPopulation_gen0_reseau_dernier_joueur() throws Exception {

        Joueur j = Enregistrement.recupererJoueurGeneration(pathname + "/generation_0.txt", 999);

        String reseau = j.getReseau().toString();

        String excpeted = "Reseau{\n" +
                "\tModule{\n" +
                "\t\tNeurone Actif{x=0, y=-1, active=false}\n" +
                "\t\tNeurone Non Pique{x=1, y=0, active=false}\n" +
                "\t\tNeurone Vide{x=2, y=0, active=false}\n" +
                "\t}\n" +
                "\tModule{\n" +
                "\t\tNeurone Bloc{x=3, y=0, active=false}\n" +
                "\t\tNeurone Actif{x=3, y=1, active=false}\n" +
                "\t\tNeurone Non Vide{x=1, y=1, active=false}\n" +
                "\t}\n" +
                "\tModule{\n" +
                "\t\tNeurone Non Pique{x=2, y=-2, active=false}\n" +
                "\t\tNeurone Non Pique{x=1, y=1, active=false}\n" +
                "\t\tNeurone Bloc{x=2, y=0, active=false}\n" +
                "\t}\n" +
                "}";

        assertEquals(excpeted, reseau);

    }

    @Test
    public void test_creation_dossier_gen() throws Exception {
        int nbDossier = Objects.requireNonNull(new File("src/main/resources/enregistrement").list()).length;


        String pathname = Enregistrement.debutEnregistrement();
        nbDossier++;

        int nbDossierApres = Objects.requireNonNull(new File("src/main/resources/enregistrement").list()).length;

        assertEquals(nbDossier, nbDossierApres);

        new File(pathname).delete();
    }

    @Test
    public void test_creation_fichier_gen() throws Exception {
        String pathname = Enregistrement.debutEnregistrement();

        int nbIndividu = 1;
        List<Joueur> population = new ArrayList<>(nbIndividu);

        for (int i = 0; i < nbIndividu; i++) {
            population.add(new Joueur(ReseauFabrique.genererReseau()));
        }

        Enregistrement.generationEnregistrement(pathname, 0, population);

        File f = new File(pathname + "/generation_0.txt");

        assertTrue(f.exists());

        f.delete();
        new File(pathname).delete();
    }

}
