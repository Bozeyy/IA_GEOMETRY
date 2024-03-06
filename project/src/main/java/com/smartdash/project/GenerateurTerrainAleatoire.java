package com.smartdash.project;

import com.smartdash.project.mvc.modele.Terrain;
import com.smartdash.project.mvc.modele.objet.Objet;

import java.util.ArrayList;

public class GenerateurTerrainAleatoire {

    private Terrain terrain;
    
    private ArrayList<Objet> objetsReserves = new ArrayList<Objet>();

    public static void main(String[] args) {
        GenerateurTerrainAleatoire g = new GenerateurTerrainAleatoire();
        Terrain t = g.genererTerrainAleatoire();
        t.afficherTerrain();
    }

    public Terrain genererTerrainAleatoire() {
        terrain = new Terrain();
        
        genererTrajectoire();
        genererSolObligatoire();

        return terrain;
    }

    private void genererSolObligatoire() {
    }

    private void genererTrajectoire() {
        // en gros tu remplis les cases vides de la trajectoire dans objetReserves
        throw new Error();
    }
}
