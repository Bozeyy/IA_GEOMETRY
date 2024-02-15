package com.smartdash.project.IA;

import com.smartdash.project.IA.neurones.Neurone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reseau implements Cloneable {
    private List<Module> modules;
    private Random rand = new Random();

    /**
     * Constructeur qui permet de créer un réseau
     */
    public Reseau() {
        this.modules = new ArrayList<>();
    }

    /**
     * Méthode qui permet de savoir si le réseau est active
     * @return retourne le boolean
     */
    public boolean isActive() {
        return this.modules.stream().anyMatch(Module::isActive);
    }

    /**
     * Méthode qui permet de setActive
     * @param x coordonnée x
     * @param y coordonnée y
     * @param type type du neurone
     */
    public void setActive(int x, int y, String type) {
        modules.forEach(module -> module.setActive(x, y, type));
    }

    /**
     * Méthode qui permet d'ajouter un neurone aléatoire
     */
    public void ajouterNeuroneAleatoire() {
        Neurone n = NeuroneFabrique.genererNeuronnePositionAleatoire();
        int indiceModule = rand.nextInt(this.modules.size());
        //possibilité d'avoir une proba d'ajouter dans un module de nbneuronemodule/nbneuronereseau

        if (indiceModule == this.modules.size()) {
            Module module = ModuleFabrique.genererModule(new Neurone[]{n});
            this.addModule(module);
        } else {
            this.modules.get(indiceModule).addNeurone(n);
        }
    }

    public void supprimerNeuroneAleatoire() {
        int indiceModule = rand.nextInt(this.modules.size());

        Module mod = this.modules.get(indiceModule);
        int indiceNeurone = rand.nextInt(mod.getNeurones().size());

        this.modules.get(indiceModule).deleteNeurone(indiceNeurone);
        if (this.modules.get(indiceModule).getNeurones().isEmpty()) {
            this.modules.remove(indiceModule);
        }
    }

    /**
     * Méthode qui permet d'ajouter un nouveau module
     * @param module module du réseau
     */
    public void addModule(Module module) {
        this.modules.add(module);
    }

    /**
     * Méthode qui permet de réinitialiser tous les modules
     */
    public void renitialiser() {
        modules.forEach(Module::renitialiser);
    }

    /**
     * Méthode qui permet d'afficher à la console un réseau
     * @return le string du réseau
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Reseau{\n");
        for (Module module : this.modules) {
            res.append("\t").append(module.toString()).append("\n");
        }
        res.append("}");
        return res.toString();
    }

    /**
     * Méthode qui permet de cloner un réseau
     * @return retourne un réseau
     */
    @Override
    public Reseau clone() {
        try {
            Reseau clone = (Reseau) super.clone();
            List<Module> clonedModules = new ArrayList<>();

            for (Module module : modules) {
                clonedModules.add(module.clone());
            }

            clone.modules = clonedModules;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public List<Module> getModules() {
        return modules;
    }

    public int getNbNeurone() {
        return this.modules.stream().mapToInt(module -> module.getNeurones().size()).sum();
    }

    public int nbModulesActifs() {
        return (int) this.modules.stream().filter(Module::isActive).count();
    }


}
