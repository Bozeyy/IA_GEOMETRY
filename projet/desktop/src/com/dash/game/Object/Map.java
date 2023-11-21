package com.dash.game.Object;

import java.util.ArrayList;

public class Map {

    public ArrayList<Objet> maps = new ArrayList<Objet>();

    public Map(){
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
