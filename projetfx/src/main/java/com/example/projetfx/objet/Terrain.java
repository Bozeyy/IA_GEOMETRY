package com.example.projetfx.objet;

import com.example.projetfx.util.util;

import java.util.ArrayList;

public class Terrain {

    public ArrayList<Objet> maps = new ArrayList<Objet>();

    public Terrain(){
        for (int i = 0; i < 20; i++) {
            maps.add(new Block(i*util.METRE, 12*util.METRE));
        }


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
}
