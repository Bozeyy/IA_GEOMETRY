package com.smartdash.project.demo;

import com.smartdash.project.apprentissage.NeatAmelioration;
import com.smartdash.project.apprentissage.NeatFinal;
import com.smartdash.project.apprentissage.NeatPosition;
import com.smartdash.project.apprentissage.NeatVariation;
import com.smartdash.project.mvc.modele.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) {
        lancerThread(4);
//        lancerNormal();
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
                NeatFinal neatAmelioration = new NeatFinal(700, 10);

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
         NeatFinal neat = new NeatFinal(500,10);

        try {
            neat.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}





























