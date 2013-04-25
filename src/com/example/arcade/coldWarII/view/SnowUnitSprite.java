package com.example.arcade.coldWarII.view;

import com.example.arcade.coldWarII.model.ColdWarPlayer;
import com.example.arcade.coldWarII.model.SnowUnit;
import com.example.arcade.coldWarII.model.SnowUnitType;

import sheep.game.Sprite;
import sheep.graphics.Image;

public class SnowUnitSprite extends Sprite {
	private SnowUnitType type;
    private SnowUnit unit;

    public SnowUnitSprite(Image a,ColdWarPlayer player,SnowUnitType t) {
        super(a);
        type = t;
        unit = new SnowUnit(player,type,this);
    }

	public SnowUnit getSnowUnit(){
		return unit;
	}
	
}