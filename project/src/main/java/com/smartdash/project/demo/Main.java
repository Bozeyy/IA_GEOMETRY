package com.smartdash.project.demo;

import com.smartdash.project.apprentissage.Neat;
import com.smartdash.project.apprentissage.NeatAmelioration;

public class Main
{
    public static void main(String[] args) {
        Neat neat = new Neat(10);
        NeatAmelioration neatAmelioration = new NeatAmelioration(100);

        try {
            neat.lancerApprentissage();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
