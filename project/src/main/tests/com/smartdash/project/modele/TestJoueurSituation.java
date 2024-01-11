package com.smartdash.project.modele;

import com.smartdash.project.IA.Reseau;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestJoueurSituation
{

    @Test
    public void test_terrain1_saut_reussi_avant_bloc() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test1.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain2_saut_reussi_avant_pique() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test2.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain2_saut_reussi_avant_avant_pique() {
        Joueur joueur = new Joueur(2, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test2.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain3_saut_reussi_avant_2_pique() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test3.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain3_saut_rate_avant_avant_2_pique() {
        Joueur joueur = new Joueur(2, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test3.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain4_saut_impossible_avant_3_pique() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test4.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain5_atterissage_pique_sol() {
        Joueur joueur = new Joueur(4, 1, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test5.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain5_saut_reussi_pique_sol() {
        Joueur joueur = new Joueur(4, 1, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test5.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain6_mort_vide_sol() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test6.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain7_atterissage_2_vide_sol() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test7.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain8_saut_avant_avant_pique_air() {
        Joueur joueur = new Joueur(0, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain8_saut_avant_pique_air() {
        Joueur joueur = new Joueur(1, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain8_saut_sous_pique_air() {
        Joueur joueur = new Joueur(2, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain8_bis_saut_avant_avant_avant_pique_air() {
        Joueur joueur = new Joueur(0, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8_bis.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain8_bis_saut_avant_avant_pique_air() {
        Joueur joueur = new Joueur(1, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8_bis.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain8_bis_saut_avant_pique_air() {
        Joueur joueur = new Joueur(2, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8_bis.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain8_bis_saut_sous_pique_air() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test8_bis.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();


        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain9_mort_entre_pique1() {
        Joueur joueur = new Joueur(4, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test9.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain9_mort_entre_pique2() {
        Joueur joueur = new Joueur(3, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test9.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain9_mort_entre_pique3() {
        Joueur joueur = new Joueur(2, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test9.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain9_mort_entre_pique4() {
        Joueur joueur = new Joueur(1, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test9.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());
    }

    @Test
    public void test_terrain9_saut_reussi() {
        Joueur joueur = new Joueur(0, 2, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test9.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();


        assertTrue(joueur.getVivant());
    }







    @Test
    public void test_terrain10_saut_reussi_apres_saut(){
        Joueur joueur = new Joueur(2,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        assertTrue(joueur.getVivant());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());


    }

    @Test
    public void test_terrain10_atterrissage_apres_saut(){
        Joueur joueur = new Joueur(2,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());


    }

    @Test
    public void test_terrain10_saut_tot(){
        Joueur joueur = new Joueur(1,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain10_saut_tard(){
        Joueur joueur = new Joueur(3,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test10.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain11_saut_reussi_apres_saut_et_pique(){
        Joueur joueur = new Joueur(2,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test11.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());
    }

    @Test
    public void test_terrain12_saut_rate_sous_bloc(){
        Joueur joueur = new Joueur(5,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test12.txt"), new Reseau());
        joueur.sauter();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain12_saut_reussi_apres_bloc(){
        Joueur joueur = new Joueur(5,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test12.txt"), new Reseau());
        joueur.updateJoueur();
        joueur.sauter();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain13_saut_sous_bloc(){
        Joueur joueur = new Joueur(5,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test13.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain13_saut_avant_bloc(){
        Joueur joueur = new Joueur(4,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test13.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain14_saut_reussi_bloc_haut(){
        Joueur joueur = new Joueur(0,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test14.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain15_saut_reussi_pique_haut(){
        Joueur joueur = new Joueur(0,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test15.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain16_atterissage_pique(){
        Joueur joueur = new Joueur(7,4, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test16.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain17_atterissage_apres_pique(){
        Joueur joueur = new Joueur(7,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test17.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain18_atterissage_pique(){
        Joueur joueur = new Joueur(7,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test18.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain19_atterissage_pique(){
        Joueur joueur = new Joueur(7,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test19.txt"), new Reseau());

        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain19_saut_fin_reussi(){
        Joueur joueur = new Joueur(7,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test19.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain19_saut_milieu_reussi(){
        Joueur joueur = new Joueur(6,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test19.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }

    @Test
    public void test_terrain19_saut_debut_rate(){
        Joueur joueur = new Joueur(5,3, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test19.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain20_saut_rate_pique(){
        Joueur joueur = new Joueur(4,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test20.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertFalse(joueur.getVivant());

    }

    @Test
    public void test_terrain21_saut_reussi_apres_saut_pique(){
        Joueur joueur = new Joueur(4,5, new Terrain("project/src/main/resources/terrains_test_situation/terrain_test21.txt"), new Reseau());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

        joueur.sauter();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();
        joueur.updateJoueur();

        assertTrue(joueur.getVivant());

    }



}
