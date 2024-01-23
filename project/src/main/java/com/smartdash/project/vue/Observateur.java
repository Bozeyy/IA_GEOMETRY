package com.smartdash.project.vue;

import com.smartdash.project.modele.Sujet;

public interface Observateur {
    public void actualiser(Sujet sujet);
}
