package com.example.projetfx.objet;

import java.util.ArrayList;

public class Terrain {

    public ArrayList<Objet> maps = new ArrayList<Objet>();

    public Terrain(){
        // TODO
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
