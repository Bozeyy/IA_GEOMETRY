package com.smartdash.project;

import com.smartdash.project.apprentissage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Apprentissage
{
    public static void main(String[] args) {
        // faire la methode lancerThread pour lancer la paralelisation sur l'algo de la methode
        lancerNormal();
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
                Neat neatAmelioration = new NeatFinal(700, 15);

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
        Neat neatPositionAleatoire = choisirApprentissage();

        try {
            neatPositionAleatoire.lancerApprentissage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Neat choisirApprentissage () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisir un apprentissage : (1-7) :");
        System.out.println("1. Neat positions fixes");
        System.out.println("2. Neat positions modulables");
        System.out.println("3. Neat position modulables (terrains aleatoires)");
        System.out.println("4. Neat nombre de neurone modulables");
        System.out.println("5. Neat nombre de neurone modulables (terrains aleatoires)");

        int apprentissage = sc.nextInt();

        switch (apprentissage) {
            case 1:
                return new Neat();
            case 2:
                return new NeatAmelioration(500,15);
            case 3:
                return new NeatPositionAleatoire(500, 35);
            case 4:
                return new NeatFinal(500, 15);
            case 5:
                return new NeatFinalAleatoire(500, 15);
            default:
                System.out.println("Apprentissage inconnu ! veuillez resayer\n");
                return choisirApprentissage();
        }
    }
}





























