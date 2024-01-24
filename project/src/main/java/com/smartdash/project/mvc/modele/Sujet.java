package com.smartdash.project.mvc.modele;

import com.smartdash.project.mvc.vue.Observateur;

public interface Sujet {
    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);
    public void notifierObservateurs();
}
