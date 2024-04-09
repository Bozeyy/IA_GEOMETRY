package com.smartdash.project;

import com.smartdash.project.apprentissage.*;

import java.util.ArrayList;
import java.util.List;

public class Apprentissage
{
    public static void main(String[] args) {
        lancerThread(4);
    }

    /**
     * Méthode qui permet de lancer plusieurs apprentissages simultanément
     * @param nombreInstances nombre d'apprentissage
     */
    public static void lancerThread(int nombreInstances)
    {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < nombreInstances; i++) {
             Thread apprentissageThread = new Thread(() -> {
                NeatFinal neatAmelioration = new NeatFinal(700, 15);

                try {
                    neatAmelioration.lancerApprentissage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });

            threads.add(apprentissageThread);
            apprentissageThread.start();
        }

        // Attendre que tous les threads se terminent
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode qui permet de lancer un seul apprentissage
     */
    public static void lancerNormal()
    {
        NeatPositionAleatoire neatPositionAleatoire = new NeatPositionAleatoire(500, 35);

        try {
            neatPositionAleatoire.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}





























