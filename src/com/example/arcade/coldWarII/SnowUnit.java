package com.example.arcade.coldWarII;

public abstract class SnowUnit {
    protected ColdWarPlayer player;
    protected int hardness;
    protected int weight;

    protected boolean isHit() {
        //TODO: ... ???
        return false;
    }

    protected void destroy() {
        //TODO: ... ???
    }

    protected abstract int getCost();
}
