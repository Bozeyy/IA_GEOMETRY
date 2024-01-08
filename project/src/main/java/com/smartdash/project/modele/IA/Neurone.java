package com.smartdash.project.modele.IA;

public abstract class Neurone {
    private int x;
    private int y;
    private boolean active;

    public Neurone(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = false;
    }

    public abstract boolean isActive(Object object);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Neurone) {
            Neurone neurone = (Neurone) obj;
            return this.x == neurone.x && this.y == neurone.y;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.getClass().getSimpleName())
                .append("{")
                .append("x=").append(x)
                .append(", y=").append(y)
                .append(", active=").append(active)
                .append("}");
        return res.toString();
    }
}
