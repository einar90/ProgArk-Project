package com.example.arcade.coldWarII;

import java.util.ArrayList;

import android.graphics.Canvas;

import sheep.game.Sprite;
import sheep.game.SpriteContainer;

public class SnowUnitSpriteContainer implements SpriteContainer {
    private ArrayList<Sprite> sprites;

    public SnowUnitSpriteContainer() {
        sprites = new ArrayList<Sprite>();
    }

    @Override
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    @Override
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public boolean contains(SnowUnitSprite s) {
        return sprites.contains(s);
    }
    
}
