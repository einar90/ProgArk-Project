package com.example.arcade.coldWarII;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.util.Log;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class ColdWarModel implements CollisionListener {
    private ColdWarPlayer active, plOne, plTwo;
    private SnowUnitSpriteContainer plOneCon, plTwoCon;
    private boolean[][] plOneSnowUnits,plTwoSnowUnits;
    private PropertyChangeSupport prSup;
    public static String SNOW_PRODUCTION="snow_production";
    public static String SNOW_AMOUNT="snow_amount";


    public ColdWarModel(SnowUnitSpriteContainer one, SnowUnitSpriteContainer two) {
        plOne = new ColdWarPlayer("Arne");
        plTwo = new ColdWarPlayer("Bjarne");
        active = plOne;
        plOneCon = one;
        plTwoCon = two;
        plOneSnowUnits = new boolean[3][5];
        plTwoSnowUnits = new boolean[3][5];
        prSup = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener l){
    	prSup.addPropertyChangeListener(l);
    }
    public void setGridOccupied(int[] array){
    	if(active == plOne){
    		for (int i = 0; i < array.length; i+=2) {
    			Log.d("PlaceButton", "array: "+(array[i+1]-1)+", "+(array[i]-1));
				plOneSnowUnits[array[i+1]-2][array[i]-2] = true;
			}
    	}
    	else{
    		for (int i = 0; i < array.length; i+=2) {
				plTwoSnowUnits[array[i+1]-1][array[i]-1] = true;
			}
    	}
    }
    public int getSnowAmount(){
    	return active.getSnowAmount();
    }
    public int getSnowProduction(){
    	return active.getSnowProduction();
    }
    public void dereaseSnowAmount(SnowUnitType type){
    	int old = active.getSnowAmount();
    	if(active != null){
    		int amount = 0;
    		switch (type) {
			case MASSIVE:
				amount = 4;
				break;
			case SNOWBALL:
				amount = 1;
				break;
			case ICECUBE:
				amount = 2;
				break;
			case ICEWALL:
				amount = 4;
				break;

			default:
				break;
			}
    		active.decreaseSnowAmount(amount);
    		PropertyChangeEvent event = new PropertyChangeEvent(active, SNOW_AMOUNT, old,active.getSnowAmount());
    		prSup.firePropertyChange(event);    		
    	}
    }
    public void increaseSnowProduction(){
    	int old = active.getSnowProduction();
    	if(active != null){
    		active.increaseSnowProduction();
    		PropertyChangeEvent event = new PropertyChangeEvent(active, SNOW_PRODUCTION, old,active.getSnowProduction());
    		prSup.firePropertyChange(event);    		
    	}
    }
    public boolean isGridEmpty(int x,int y){
    	if(active == plOne)
    		return !plOneSnowUnits[y-1][x-1];
    	else
    		return !plTwoSnowUnits[y-1][x-1];
    }
    public ColdWarPlayer getActivePlayer(){
    	return active;
    }
    public ColdWarPlayer getPlayerOne(){
    	return plOne;
    }
    public ColdWarPlayer getPlayerTwo(){
    	return plTwo;
    }
    
    
//    public void reverseMenu() {
//    	if (menu)
//    		menu = false;
//    	else
//    		menu = true;
//    }
//    public void reverseAttack() {
//        if (attack)
//        	attack = false;
//        else
//        	attack = true;
//    }
//
//    public void reversePlacing() {
//        if (placing)
//            placing = false;
//        else
//            placing = true;
//    }

//    public boolean isPlacing() {
//        return placing;
//    }
//    
//    public boolean isAttack() {
//        return attack;
//    }

//    public boolean isMenu() {
//        return menu;
//    }

    /**
     * @return
     */
    public boolean isPlayerOne() {
        if (active != null && active.equals(plOne))
            return true;
        return false;
    }

    public void changePlayer() {
    	active.increaseSnow();
        if (active != null) {
            if (active.equals(plOne))
                active = plTwo;
            else
                active = plOne;
        }
    }

    public void destroySnowUnit(SnowUnitSprite s) {
        if (plOneCon.contains(s))
            plOneCon.removeSprite(s);
        else if (plTwoCon.contains(s))
            plTwoCon.removeSprite(s);
        s.die();
    }

    @Override
    public void collided(Sprite a, Sprite b) {
        if(a instanceof SnowUnitSprite && b instanceof SnowUnitSprite){
        	SnowUnitSprite sa = (SnowUnitSprite)a;
        	SnowUnitSprite sb = (SnowUnitSprite)b;
        	
        	SnowUnit sua = sa.getSnowUnit();
        	SnowUnit sub = sb.getSnowUnit();
        	
        	if(sua.getPlayer().equals(sub.getPlayer())){
        		if(a.getSpeed().getY() > 0 && b.getSpeed().getY() > 0){
        			//nada
        		}else if(a.getSpeed().getY() > 0 || b.getSpeed().getY() > 0){
        			a.setSpeed(0, 0);
        			b.setSpeed(0, 0);
        		}
        	}else{
        		sua.decreaseHardness(sub.getHardness());
        		sub.decreaseHardness(sua.getHardness());
        	}
        }
    }
}
