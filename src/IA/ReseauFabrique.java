package IA;

public class ReseauFabrique {
    public static Reseau genererReseau(){
        Reseau reseau = new Reseau();
        for (int i = 1; i <= Constantes.NB_MODULES_PAR_RESEAU; i++) {
            reseau.addModule(ModuleFabrique.genererModule(i));
        }
        return reseau;
    }
}
