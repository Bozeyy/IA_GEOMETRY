package com.smartdash.project.IA;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestReseauUnit {

    @Test
    public void testNeuroneBloc() {
        Neurone n = new NeuroneBloc(3, 2);

        String s = n.toString();

        assertEquals(s, "Neurone Bloc{x=3, y=2, active=false}", "erreur de creation d un neurone bloc");
    }

    @Test
    public void testNeuroneNonBloc() {
        Neurone n = new NeuroneNonBloc(-3, 2);

        String s = n.toString();

        assertEquals(s, "Neurone Non Bloc{x=-3, y=2, active=false}", "erreur de creation d un neurone non bloc");
    }

    @Test
    public void testNeuronePique() {
        Neurone n = new NeuronePique(4, 1);

        String s = n.toString();

        assertEquals(s, "Neurone Pique{x=4, y=1, active=false}", "erreur de creation d un neurone pique");
    }

    @Test
    public void testNeuroneNonPique() {
        Neurone n = new NeuroneNonPique(7, 0);

        String s = n.toString();

        assertEquals(s, "Neurone Non Pique{x=7, y=0, active=false}", "erreur de creation d un neurone non pique");
    }

    @Test
    public void testNeuroneVide() {
        Neurone n = new NeuroneVide(0, 0);

        String s = n.toString();

        assertEquals(s, "Neurone Vide{x=0, y=0, active=false}", "erreur de creation d un neurone vide");
    }

    @Test
    public void testNeuroneNonVide() {
        Neurone n = new NeuroneNonVide(3, 2);

        String s = n.toString();

        assertEquals(s, "Neurone Non Vide{x=3, y=2, active=false}", "erreur de creation d un neurone non vide");
    }

    @Test
    public void testNeuroneActif() {
        Neurone n = new NeuroneActif(-3, -6);

        String s = n.toString();

        assertEquals(s, "Neurone Actif{x=-3, y=-6, active=false}", "erreur de creation d un neurone actif");
    }

    @Test
    public void testNeuroneFabrique() {
        Neurone n = NeuroneFabrique.genererNeuronne(3, 4);

        int x = n.getX();
        int y = n.getY();

        assertEquals(x, 3, "erreur creation d un neurone avec la fabrique");
        assertEquals(y, 4, "erreur creation d un neurone avec la fabrique");
    }

    /**
    @Test
    public void testModule1Fabrique() {
        Module m = ModuleFabrique.genererModule(1);

        List<Neurone> neurones = m.getNeurones();
        int x1 = neurones.get(0).getX();
        int y1 = neurones.get(0).getY();
        int x2 = neurones.get(1).getX();
        int y2 = neurones.get(1).getY();
        int x3 = neurones.get(2).getX();
        int y3 = neurones.get(2).getY();

        assertEquals(neurones.size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(x1, 0, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(y1, -1, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(x2, 1, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(y2, 0, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(x3, 1, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
        assertEquals(y3, 1, "erreur creation du module 1 de neurone aleatoire avec la fabrique");
    }

    @Test
    public void testModule2Fabrique() {
        Module m = ModuleFabrique.genererModule(2);

        List<Neurone> neurones = m.getNeurones();
        int x1 = neurones.get(0).getX();
        int y1 = neurones.get(0).getY();
        int x2 = neurones.get(1).getX();
        int y2 = neurones.get(1).getY();
        int x3 = neurones.get(2).getX();
        int y3 = neurones.get(2).getY();

        assertEquals(neurones.size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(x1, 1, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(y1, 1, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(x2, 2, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(y2, -2, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(x3, 4, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
        assertEquals(y3, 1, "erreur creation du module 2 de neurone aleatoire avec la fabrique");
    }

    @Test
    public void testModule3Fabrique() {
        Module m = ModuleFabrique.genererModule(3);

        List<Neurone> neurones = m.getNeurones();
        int x1 = neurones.get(0).getX();
        int y1 = neurones.get(0).getY();
        int x2 = neurones.get(1).getX();
        int y2 = neurones.get(1).getY();
        int x3 = neurones.get(2).getX();
        int y3 = neurones.get(2).getY();

        assertEquals(neurones.size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(x1, 3, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(y1, 2, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(x2, 4, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(y2, 0, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(x3, 0, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
        assertEquals(y3, -1, "erreur creation du module 3 de neurone aleatoire avec la fabrique");
    }*/

    @Test
    public void testModuleFabriquePerso() {
        Neurone n1 = new NeuroneBloc(3, 2);
        Neurone n2 = new NeuronePique(4, 0);
        Module m = ModuleFabrique.genererModule(new Neurone[]{n1, n2});

        String s = m.toString();

        assertEquals(s, """
                Module{
                \t\tNeurone Bloc{x=3, y=2, active=false}
                \t\tNeurone Pique{x=4, y=0, active=false}
                \t}""", "erreur creation d'un module de neurone perso avec la fabrique");
    }

    @Test
    public void testReseauFabrique() {
        Reseau r = ReseauFabrique.genererReseau();

        List<Module> modules = r.getModules();

        assertEquals(modules.size(), Constantes.NB_MODULES_PAR_RESEAU, "erreur de creation du reseau");
        assertEquals(modules.get(0).getNeurones().size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur de creation du reseau");
        assertEquals(modules.get(1).getNeurones().size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur de creation du reseau");
        assertEquals(modules.get(2).getNeurones().size(), Constantes.NB_NEURONES_PAR_MODULES, "erreur de creation du reseau");

    }

    @Test
    public void testReseauFabriquePerso() {
        Neurone n1 = new NeuroneBloc(3, 2);
        Neurone n2 = new NeuroneActif(5, 1);
        Neurone n3 = new NeuroneActif(2, 1);
        Neurone n4 = new NeuroneNonVide(5, 3);
        Neurone n5 = new NeuronePique(0, -2);

        Module m1 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
        Module m2 = ModuleFabrique.genererModule(new Neurone[]{n1, n4, n5});

        Reseau r = ReseauFabrique.genererReseau(new Module[]{m1, m2});

        String s = r.toString();

        assertEquals(s, """
                Reseau{
                \tModule{
                \t\tNeurone Bloc{x=3, y=2, active=false}
                \t\tNeurone Actif{x=5, y=1, active=false}
                \t\tNeurone Actif{x=2, y=1, active=false}
                \t}
                \tModule{
                \t\tNeurone Bloc{x=3, y=2, active=false}
                \t\tNeurone Non Vide{x=5, y=3, active=false}
                \t\tNeurone Pique{x=0, y=-2, active=false}
                \t}
                }""","erreur de creation d un reseau perso avec une fabrique");

    }

    @Test
    public void testNeuroneBlocNonActif() {
        Neurone n = new NeuroneBloc(3, 2);

        n.setActive(3, 1, "bloc");
        String s = n.toString();

        assertEquals(s, "Neurone Bloc{x=3, y=2, active=false}", "erreur d activation d un neurone bloc");
    }

    @Test
    public void testNeuroneBlocActif() {
        Neurone n = new NeuroneBloc(3, 2);

        n.setActive(3, 2, "bloc");
        String s = n.toString();

        assertEquals(s, "Neurone Bloc{x=3, y=2, active=true}", "erreur d activation d un neurone bloc");
    }

    @Test
    public void testNeuroneNonBlocNonActif() {
        Neurone n = new NeuroneNonBloc(-3, 2);

        n.setActive(-3, 2, "bloc");
        String s = n.toString();

        assertEquals(s, "Neurone Non Bloc{x=-3, y=2, active=false}", "erreur d activation d un neurone non bloc");
    }

    @Test
    public void testNeuroneNonBlocActif() {
        Neurone n = new NeuroneNonBloc(-3, 2);

        n.setActive(-3, 2, "vide");
        String s = n.toString();

        assertEquals(s, "Neurone Non Bloc{x=-3, y=2, active=true}", "erreur d activation d un neurone non bloc");
    }

    @Test
    public void testNeuronePiqueNonActif() {
        Neurone n = new NeuronePique(4, 1);

        n.setActive(4, 1, "vide");
        String s = n.toString();

        assertEquals(s, "Neurone Pique{x=4, y=1, active=false}", "erreur d activation d un neurone pique");
    }

    @Test
    public void testNeuronePiqueActif() {
        Neurone n = new NeuronePique(4, 1);

        n.setActive(4, 1, "pique");
        String s = n.toString();

        assertEquals(s, "Neurone Pique{x=4, y=1, active=true}", "erreur d activation d un neurone pique");
    }

    @Test
    public void testNeuroneNonPiqueNonActif() {
        Neurone n = new NeuroneNonPique(7, 0);

        n.setActive(7, 1, "bloc");
        String s = n.toString();

        assertEquals(s, "Neurone Non Pique{x=7, y=0, active=false}", "erreur d activation d un neurone non pique");
    }

    @Test
    public void testNeuroneNonPiqueActif() {
        Neurone n = new NeuroneNonPique(7, 0);

        n.setActive(7, 0, "bloc");
        String s = n.toString();

        assertEquals(s, "Neurone Non Pique{x=7, y=0, active=true}", "erreur d activation d un neurone non pique");
    }

    @Test
    public void testNeuroneVideNonActif() {
        Neurone n = new NeuroneVide(0, 0);

        n.setActive(-1, 1, "vide");
        String s = n.toString();

        assertEquals(s, "Neurone Vide{x=0, y=0, active=false}", "erreur d activation d un neurone vide");
    }

    @Test
    public void testNeuroneVideActif() {
        Neurone n = new NeuroneVide(0, 0);

        n.setActive(0, 0, "vide");
        String s = n.toString();

        assertEquals(s, "Neurone Vide{x=0, y=0, active=true}", "erreur d activation d un neurone vide");
    }

    @Test
    public void testNeuroneNonVideNonActif() {
        Neurone n = new NeuroneNonVide(3, 2);

        n.setActive(3, 2, "vide");
        String s = n.toString();

        assertEquals(s, "Neurone Non Vide{x=3, y=2, active=false}", "erreur d activation d un neurone non vide");
    }

    @Test
    public void testNeuroneNonVideActif() {
        Neurone n = new NeuroneNonVide(3, 2);

        n.setActive(3, 2, "pique");
        String s = n.toString();

        assertEquals(s, "Neurone Non Vide{x=3, y=2, active=true}", "erreur d activation d un neurone non vide");
    }

    @Test
    public void testNeuroneActifTrue() {
        Neurone n = new NeuroneActif(-3, -6);

        n.setActive(-3, -6, "pique");
        String s = n.toString();

        assertEquals(s, "Neurone Actif{x=-3, y=-6, active=true}", "erreur d activation d un neurone actif");
    }

    @Test
    public void testModuleNonActive() {
        Neurone n1 = new NeuroneBloc(3, 5);
        Neurone n2 = new NeuroneActif(2, 1);
        Neurone n3 = new NeuronePique(0, 0);

        Module m = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
        m.setActive(3,5, "bloc");
        m.setActive(2, 1, "bloc");
        m.setActive(0, 0, "vide");
        boolean actif = m.isActive();

        assertFalse(actif, "erreur d activation du module non souhaité");

    }

    @Test
    public void testModuleActive() {
        Neurone n1 = new NeuroneBloc(3, 5);
        Neurone n2 = new NeuroneActif(2, 1);
        Neurone n3 = new NeuroneVide(0, 0);

        Module m = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
        m.setActive(3,5, "bloc");
        m.setActive(2, 1, "bloc");
        m.setActive(0, 0, "vide");
        boolean actif = m.isActive();

        assertTrue(actif, "erreur d activation du module non souhaité");

    }

    @Test
    public void testReseauNonActif() {
        Neurone n1 = new NeuroneBloc(3, 5);
        Neurone n2 = new NeuroneActif(2, 1);
        Neurone n3 = new NeuroneVide(0, 0);
        Neurone n4 = new NeuroneNonPique(4, 0);
        Neurone n5 = new NeuroneNonBloc(1, 1);
        Neurone n6 = new NeuronePique(7, 8);


        Module m1 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
        Module m2 = ModuleFabrique.genererModule(new Neurone[]{n4, n5, n6});
        Module m3 = ModuleFabrique.genererModule(new Neurone[]{n1, n2});

        Reseau r = ReseauFabrique.genererReseau(new Module[]{m1,m2,m3});
        r.setActive(3, 5, "bloc");
        r.setActive(2, 1, "vide");
        r.setActive(0, 0, "vide");
        r.setActive(4, 0, "vide");
        r.setActive(1, 1, "bloc");
        r.setActive(7, 8, "pique");

        boolean actif = r.isActive();

        assertTrue(actif, "erreur d activation du reseau");
    }

    @Test
    public void testReseauActif() {
        Neurone n1 = new NeuroneBloc(3, 5);
        Neurone n2 = new NeuroneActif(2, 1);
        Neurone n3 = new NeuroneVide(0, 0);
        Neurone n4 = new NeuroneNonPique(4, 0);
        Neurone n5 = new NeuroneNonBloc(1, 1);
        Neurone n6 = new NeuronePique(7, 8);


        Module m1 = ModuleFabrique.genererModule(new Neurone[]{n1, n2, n3});
        Module m2 = ModuleFabrique.genererModule(new Neurone[]{n4, n5, n6});
        Module m3 = ModuleFabrique.genererModule(new Neurone[]{n1, n2});

        Reseau r = ReseauFabrique.genererReseau(new Module[]{m1,m2,m3});
        r.setActive(3, 5, "bloc");
        r.setActive(2, 1, "vide");
        r.setActive(0, 0, "vide");
        r.setActive(4, 0, "vide");
        r.setActive(1, 1, "vide");
        r.setActive(7, 8, "pique");

        boolean actif = r.isActive();

        assertTrue(actif, "erreur d activation du reseau");
    }

}
