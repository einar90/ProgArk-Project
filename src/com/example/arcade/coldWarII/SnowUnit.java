package com.example.arcade.coldWarII;

public class SnowUnit {
    private ColdWarPlayer player;
    private int hardness;
    private int weight;
	private int cost;
    private SnowUnitType type;
    
    public SnowUnit(ColdWarPlayer player,SnowUnitType type){
    	this.player = player;
    	this.type = type;
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
    
    protected boolean isHit() {
        //TODO: ... ???
        return false;
    }

    protected void destroy() {
        //TODO: ... ???
    }

}
