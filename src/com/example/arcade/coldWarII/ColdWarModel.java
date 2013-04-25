package com.example.arcade.coldWarII;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.util.Log;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class ColdWarModel implements CollisionListener {
	private static ColdWarModel instance;
    private ColdWarPlayer active, plOne, plTwo;
    private SnowUnitSpriteContainer plOneCon, plTwoCon;
    private boolean[][] plOneSnowUnits,plTwoSnowUnits;
    private PropertyChangeSupport prSup;
    public static String SNOW_PRODUCTION="snow_production136433";
    public static String SNOW_AMOUNT="snow_amount199787665165";
    public static String COLLISION="collision5548632315";
    public static String KING_COLLISION="king_collision25489621";


    private ColdWarModel() {
        plOne = new ColdWarPlayer("Arne");
        plTwo = new ColdWarPlayer("Bjarne");
        active = plOne;
        plOneSnowUnits = new boolean[3][5];
        plTwoSnowUnits = new boolean[3][5];
        prSup = new PropertyChangeSupport(this);
    }
    public static ColdWarModel getInstance(){
    	if(instance == null)
    		return instance = new ColdWarModel();
    	else 
    		return instance;
    }
    public void setSpriteContainers(SnowUnitSpriteContainer one,SnowUnitSpriteContainer two){
    	plOneCon = one;
        plTwoCon = two;
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

    public void destroySnowUnit(SnowUnit sU) {
    	SnowUnitSprite s  = sU.getSprite();
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
        			if(sua.getType().equals(SnowUnitType.MASSIVE) && a.getSpeed().getY() > 0){
        				a.setPosition(a.getX(), a.getY()+15);
        			}else if(sub.getType().equals(SnowUnitType.MASSIVE) && b.getSpeed().getY() > 0){
        				b.setPosition(b.getX(), b.getY()+15);
        			}else if(a.getSpeed().getY() > 0){
        				a.setPosition(a.getX(), a.getY()+3);
        			}else if(b.getSpeed().getY() > 0){
        				b.setPosition(b.getX(), b.getY()+3);
        			}
        			a.setSpeed(0, 0);
        			b.setSpeed(0, 0);
        		}
        	}else{
        		int suaHardness = sua.getHardness(),subHardness = sub.getHardness();
        		
        		if(sua.getType() == SnowUnitType.KING ){
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);
        			prSup.firePropertyChange(new PropertyChangeEvent(sua, KING_COLLISION, suaHardness, sua.getHardness()));
        		}
        		else if(sub.getType() == SnowUnitType.KING){
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);
        			prSup.firePropertyChange(new PropertyChangeEvent(sua, KING_COLLISION, subHardness, sua.getHardness()));
        		}
        		else{
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);        			
        		}
        	}
        }
    }
}
