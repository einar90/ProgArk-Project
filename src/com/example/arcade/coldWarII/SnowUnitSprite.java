package com.example.arcade.coldWarII;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class SnowUnitSprite extends Sprite {
	private SnowUnitType type;
    private SnowUnit unit;

    public SnowUnitSprite(Image a,ColdWarPlayer player,SnowUnitType t) {
        super(a);
        type = t;
        unit = new SnowUnit(player, type);
    }

	
}