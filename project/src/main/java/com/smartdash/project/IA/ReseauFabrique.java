package com.smartdash.project.IA;

public class ReseauFabrique {

    /**
     * genere un reseau a partir des constantes definies
     * @return
     */
    public static Reseau genererReseau(){
        Reseau reseau = new Reseau();
        for (int i = 1; i <= Constantes.NB_MODULES_PAR_RESEAU; i++) {
            reseau.addModule(ModuleFabrique.genererModule(i));
        }
        return reseau;
    }

    /**
     * genere un Reseau a partir de modules deja défini
     * @param modules
     */
    public static Reseau genererReseau(Module[] modules) {
        Reseau reseau = new Reseau();
        for (Module m : modules) {
            reseau.addModule(m);
        }
        return reseau;
    }
}
