package dk.monopoly.entities;

import java.io.Serializable;

public abstract class Owner implements Serializable {
    protected String name = "";

    public abstract String getName();
    public abstract void setName(String name);
}
