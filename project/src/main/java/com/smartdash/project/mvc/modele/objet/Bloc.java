package com.smartdash.project.mvc.modele.objet;

public class Bloc extends Objet{
    public Bloc(int x, int y){
        super(x,y);
    }


    @Override
    public String getType() {
        return "bloc";
    }
}
