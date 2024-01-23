package com.smartdash.project.modele;

import com.smartdash.project.vue.Observateur;

public interface Sujet {
    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);
    public void notifierObservateurs();
}
