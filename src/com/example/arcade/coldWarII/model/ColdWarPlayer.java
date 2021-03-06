package com.example.arcade.coldWarII.model;

import java.util.ArrayList;

public class ColdWarPlayer {
	private String name;
    private int slingShotLevel;
    private int snowAmount;
    private int snowProduction;
    private ArrayList<SnowUnit> snowUnits;
    public ColdWarPlayer(String name){
    	this.name = name;
    	slingShotLevel = 1;
    	snowAmount = 1; 
    	snowProduction = 1;
    	snowUnits = new ArrayList<SnowUnit>();
    }
    public int getSnowAmount() {
		return snowAmount;
	}

	public int getSnowProduction() {
		return snowProduction;
	}

	public ArrayList<SnowUnit> getSnowUnits() {
		return snowUnits;
	}
	
	public String getName(){
		return name;
	}
    
    public void increaseSnowProduction(){
    	if(snowAmount >= snowProduction){
	    	snowAmount -= snowProduction;
	    	snowProduction++;
    	}
    }
    public void increaseSlingshot(){
    	slingShotLevel++;
    }
    public void increaseSnow(){
    	snowAmount += snowProduction;
    }
    public boolean decreaseSnowAmount(int amount){
    	if(snowAmount >= amount){
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
