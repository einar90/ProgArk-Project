package com.example.arcade.coldWarII;

public class SnowUnit {
    private ColdWarPlayer player;
    private ColdWarModel model;
    private SnowUnitSprite sprite;
    private int hardness;
    private int weight;
	private int cost;
    private SnowUnitType type;
    
    public SnowUnit(ColdWarPlayer player,SnowUnitType type,SnowUnitSprite s){
    	this.player = player;
    	this.type = type;
    	model = ColdWarModel.getInstance();
    	sprite = s;
    	initSnowUnit();
    }
    
    private void initSnowUnit() {
		switch (type) {
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
    
	public void setPlayer(ColdWarPlayer player) {
		this.player = player;
	}

	public int getHardness() {
		return hardness;
	}
	
	public void decreaseHardness(int dx){
		hardness -= dx;
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
