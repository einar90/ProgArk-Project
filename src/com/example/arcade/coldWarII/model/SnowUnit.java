package com.example.arcade.coldWarII.model;

import com.example.arcade.coldWarII.controller.ColdWarController;
import com.example.arcade.coldWarII.view.SnowUnitSprite;

public class SnowUnit {
    private ColdWarPlayer player;
    private ColdWarController model;
    private SnowUnitSprite sprite;
    private int hardness;
    private int weight;
	private int cost;
    private SnowUnitType type;
    
    public SnowUnit(ColdWarPlayer player,SnowUnitType type,SnowUnitSprite s){
    	this.player = player;
    	this.type = type;
    	model = ColdWarController.getInstance();
    	sprite = s;
    	initSnowUnit();
    }
    
    private void initSnowUnit() {
		switch (type) {
		case KING:
			hardness = 3;
			break;
		case ICECUBE:
			hardness = 2;
			weight = 4;
			cost = 2;
			break;
		case ICEWALL:
			hardness = 4;
			weight = 8;
			cost = 4;
			break;
		case MASSIVE:
			hardness = 4;
			weight = 8;
			cost = 4;
			break;
		case SNOWBALL:
			hardness = 1;
			weight = 2;
			cost = 1;
			break;
		default:
			break;
		}
	}
    public ColdWarPlayer getPlayer() {
		return player;
	}

    public SnowUnitSprite getSprite(){
    	return sprite;
    }
    
	public void setHardness(int hardness){
		this.hardness = hardness;
	}
	public int getHardness() {
		return hardness;
	}
	
	public void decreaseHardness(int dx){
		if(dx > 0) hardness -= dx;
		if(hardness <= 0)
			destroy();
	}

	public int getWeight() {
		return weight;
	}

	public SnowUnitType getType() {
		return type;
	}
    public int getCost(){
    	return cost;
    }

    protected void destroy() {
        model.destroySnowUnit(this);
    }

}
