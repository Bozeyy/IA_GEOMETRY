package com.smartdash.project.demo;

import com.smartdash.project.apprentissage.NeatAmelioration;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) {
        lancerNormal();
    }

    public static void lancerThread()
    {
        int nombreInstances = 5; // Nombre d'instances de NeatAmelioration à créer

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < nombreInstances; i++) {
            final int instanceIndex = i;
            Thread apprentissageThread = new Thread(() -> {
                NeatAmelioration neatAmelioration = new NeatAmelioration(10, 5);
                try {
                    neatAmelioration.lancerApprentissage();
                } catch (Exception e) {
                    System.out.println("Erreur dans l'instance " + instanceIndex + ": " + e.getMessage());
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

    public static void lancerNormal()
    {
        NeatAmelioration neatAmelioration = new NeatAmelioration(2000, 15);

        try {
            neatAmelioration.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
