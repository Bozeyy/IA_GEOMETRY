package com.smartdash.project.IA.neurones;

public class NeuroneBloc extends Neurone{
    public NeuroneBloc(int x, int y) {
        super(x, y);
    }

    @Override
    public void setActive(int x, int y, String type) {
        if (this.x == x && this.y == y) {
            this.active = type.equals("bloc");
        }
    }

    public String toString() {
        return "Neurone Bloc" + super.toString();
    }

    @Override
    public char getType() {
        return 'b';
    }

}
