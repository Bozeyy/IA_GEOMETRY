package com.smartdash.project.IA;

import com.smartdash.project.apprentissage.util.Enregistrement;

public class ReseauFabrique {
    /**
     * Génère un réseau à partir des constantes définies
     * @return retourne un réseau
     */
    public static Reseau genererReseau(){
        Reseau reseau = new Reseau();
        for (int i = 1; i <= Constantes.NB_MODULES_PAR_RESEAU; i++) {
            reseau.addModule(ModuleFabrique.genererModule(i));
        }
        return reseau;
    }

    /**
     * Génère un réseau à partir de modules deja défini
     * @param modules
     */
    public static Reseau genererReseau(Module[] modules) {
        Reseau reseau = new Reseau();
        for (Module m : modules) {
            reseau.addModule(m);
        }
        return reseau;
    }

    /**
     * Génère un réseau avec des positions aléatoires
     * @return retourne un réseau
     */
    public static Reseau genererReseauPosAleatoire() {
        Reseau reseau = new Reseau();
        for (int i = 1; i <= Constantes.NB_MODULES_PAR_RESEAU; i++) {
            reseau.addModule(ModuleFabrique.genererModulePosAleatoire());
        }
        return reseau;
    }

    /**
     * Méthode qui permet de récupérer un réseau
     * @param chemin chemin du réseau
     * @param i le réseau voulu
     * @return retourne un réseau
     * @throws Exception exception IO
     */
    public static Reseau recupererReseau(String chemin, int i) throws Exception {
        Reseau r = Enregistrement.recupererJoueurGeneration(chemin, i).getReseau().clone();
        return r;
    }
}
