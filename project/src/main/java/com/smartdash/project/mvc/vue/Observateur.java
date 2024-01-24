package com.smartdash.project.mvc.vue;

import com.smartdash.project.mvc.modele.Sujet;

public interface Observateur {
    public void actualiser(Sujet sujet);
}
