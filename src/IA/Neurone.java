package IA;

public abstract class Neurone {
    private int x;
    private int y;
    private boolean active;

    public Neurone(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    public boolean isActive(Object object) {
        return true;
    }

}
