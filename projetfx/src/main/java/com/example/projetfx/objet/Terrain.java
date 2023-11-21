package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;
import com.example.projetfx.util.util;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Terrain {

    public ArrayList<Objet> maps = new ArrayList<Objet>();

    public Terrain(){
        for (int i = -3; i < 400; i++) {
            maps.add(new Block(i*util.METRE, 12*util.METRE));
            maps.add(new Block(i*util.METRE, 13*util.METRE));
            maps.add(new Block(i*util.METRE, 14*util.METRE));
            maps.add(new Block(i*util.METRE, 15*util.METRE));
            maps.add(new Block(i*util.METRE, 16*util.METRE));
        }

        maps.add(new Block(20*util.METRE, 11*util.METRE));


        this.draw();
    }

    public void add(Objet o){
        maps.add(o);
    }

    public void draw(){
        for(Objet o : maps){
            o.draw();
        }
    }

    /**
     * méthode getBlockAutour
     * retourne une lsite de block autour du joueur
     * @param joueur le joueur
     */
    public ArrayList<Objet> getBlockAutour(Joueur joueur){
        ArrayList<Objet> blocks = new ArrayList<Objet>();
        for(Objet o : maps){
            if ((o.getPoseX() - joueur.x) < 2*util.METRE && (o.getPoseX() - joueur.x) > -2*util.METRE && (o.getPoseY() - joueur.y) < 1.5*util.METRE && (o.getPoseY() - joueur.y) > -1.5*util.METRE){
                blocks.add(o);
                ((Block) o).setFill(Color.RED);
            } else {
                ((Block) o).setFill(Color.GRAY);
            }
        }
        return blocks;
    }
}
