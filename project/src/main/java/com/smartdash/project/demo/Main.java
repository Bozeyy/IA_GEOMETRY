package com.smartdash.project.demo;

import com.smartdash.project.apprentissage.NeatAmelioration;
import com.smartdash.project.apprentissage.NeatPosition;
import com.smartdash.project.mvc.modele.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) {
        lancerThread(5);
    }

    public static void lancerThread(int nombreInstances)
    {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < nombreInstances; i++) {
            Thread apprentissageThread = new Thread(() -> {
                NeatAmelioration neatAmelioration = new NeatAmelioration(5000, 15);

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

    public static void lancerNormal()
    {
         NeatPosition neatAmelioration = new NeatPosition(new Terrain("src/main/resources/apprentissage/terrain15.txt"),150);

        try {
            neatAmelioration.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
