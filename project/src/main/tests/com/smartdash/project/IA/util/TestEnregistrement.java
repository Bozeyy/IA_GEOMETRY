package com.smartdash.project.IA.util;

import com.smartdash.project.IA.ReseauFabrique;
import com.smartdash.project.apprentissage.util.Enregistrement;
import com.smartdash.project.mvc.modele.Joueur;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnregistrement {
    String pathname = "src/main/resources/enregistrement/enregistrement_test";

    @Test
    public void test_stringToPopulation_taille() throws Exception {
        List<Joueur> population = Enregistrement.stringToPopulation(pathname + "/generation_0.txt");

        assertEquals(1000, population.size());
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
}
