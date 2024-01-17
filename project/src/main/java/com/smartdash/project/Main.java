package com.smartdash.project;

import com.smartdash.project.apprentissage.Neat;

public class Main
{
    public static void main(String[] args) {
        Neat neat = new Neat();

        try {
            neat.lancerApprentissage();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
