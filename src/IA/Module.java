package IA;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private boolean active;
    private List<Neurone> neurones;

    public Module() {
        this.neurones = new ArrayList<>(3);
        this.active = false;
    }

    public boolean isActive(Object object) {
        this.active = this.neurones.stream().allMatch(neurone -> neurone.isActive(object));
        return this.active;
    }

}
