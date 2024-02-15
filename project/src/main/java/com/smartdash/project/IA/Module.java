package com.smartdash.project.IA;

import com.smartdash.project.IA.neurones.Neurone;

import java.util.ArrayList;
import java.util.List;

public class Module implements Cloneable {
    private List<Neurone> neurones;


    /**
     * Constructeur
     */
    public Module() {
        this.neurones = new ArrayList<>(Constantes.NB_NEURONES_PAR_MODULES);
    }


    /**
     * Méthode qui permet de savoir si le module est activé
     * @return retourne un boolean pour savoir si le module est activé
     */
    public boolean isActive() {
        return this.neurones.stream().allMatch(Neurone::isActive);
    }

    /**
     * Méthode qui permet de modifier l'état des neurones du module
     * @param x position x
     * @param y position y
     * @param type type du neurone
     */
    public void setActive(int x, int y, String type) {
        this.neurones.forEach(neurone -> neurone.setActive(x, y, type));
    }

    /**
     * Méthode qui permet de réinitialiser l'état d'un module
     */
    public void renitialiser() {
        neurones.forEach(neurone -> neurone.active = false);
    }

    /**
     * Méthode qui permet d'ajouter des neurones à un module
     * @param neurone neurone à ajouter
     */
    public void addNeurone(Neurone neurone) {
        if (!this.neurones.contains(neurone)) {
            this.neurones.add(neurone);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Module{\n");
        for (Neurone neurone : this.neurones) {
            res.append("\t\t").append(neurone.toString()).append("\n");
        }
        res.append("\t}");

        return res.toString();
    }

    @Override
    public Module clone() {
        try {
            Module clone = (Module) super.clone();
            List<Neurone> clonedNeurones = new ArrayList<>();
            for (Neurone n : neurones) {
                clonedNeurones.add(n.clone());
            }
            clone.neurones = clonedNeurones;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public List<Neurone> getNeurones() {
        return neurones;
    }

    public void deleteNeurone(int indiceNeurone) {
        this.neurones.remove(indiceNeurone);
    }
}
