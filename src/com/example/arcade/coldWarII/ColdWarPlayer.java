package com.example.arcade.coldWarII;

import java.util.ArrayList;

public class ColdWarPlayer {
	private String name;
    private int slingShotLevel;
    private int snowAmount;
    private int snowProduction;
    private ArrayList<SnowUnit> snowUnits;
    private ArrayList<ColdWarAction> actionHistory;

    public ColdWarPlayer(String name){
    	this.name = name;
    	slingShotLevel = 1;
    	snowAmount = 1;
    	snowProduction = 1;
    	snowUnits = new ArrayList<SnowUnit>();
    	actionHistory = new ArrayList<ColdWarAction>();
    }
    
    public void increaseSnowProduction(){
    	snowProduction++;
    }
    public void increaseSlingshot(){
    	slingShotLevel++;
    }
    public void increaseSnow(){
    	snowAmount += snowProduction;
    }
    public boolean decreaseSnowAmount(int amount){
    	if(snowAmount > amount){
    		snowAmount -= amount;
    		return true;
    	}	
    	return false;
    }
    public void addSnowUnit(SnowUnit u){
    	snowUnits.add(u);
    }
    public void removeSnowUnit(SnowUnit u){
    	snowUnits.remove(u);
    }
}
