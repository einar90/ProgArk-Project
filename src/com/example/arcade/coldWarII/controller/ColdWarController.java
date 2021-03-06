package com.example.arcade.coldWarII.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.example.arcade.coldWarII.model.ColdWarPlayer;
import com.example.arcade.coldWarII.model.SnowUnit;
import com.example.arcade.coldWarII.model.SnowUnitType;
import com.example.arcade.coldWarII.view.SnowUnitSprite;
import com.example.arcade.coldWarII.view.SnowUnitSpriteContainer;

import android.util.Log;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class ColdWarController implements CollisionListener {
	private static ColdWarController instance;
    private ColdWarPlayer active, plOne, plTwo;
    private SnowUnitSpriteContainer plOneCon, plTwoCon, all;
    private PropertyChangeSupport prSup;
    public static String SNOW_PRODUCTION="snow_production136433";
    public static String SNOW_AMOUNT="snow_amount199787665165";
    public static String COLLISION="collision5548632315";
    public static String KING_COLLISION="king_collision25489621";


    private ColdWarController() {
        plOne = new ColdWarPlayer("Arne");
        plTwo = new ColdWarPlayer("Bjarne");
        active = plOne;
        prSup = new PropertyChangeSupport(this);
    }
    public static ColdWarController getInstance(){
    	if(instance == null)
    		return instance = new ColdWarController();
    	else 
    		return instance;
    }
    public void setSpriteContainers(SnowUnitSpriteContainer one,SnowUnitSpriteContainer two, SnowUnitSpriteContainer all){
    	plOneCon = one;
        plTwoCon = two;
        this.all = all;
    }
    public void addPropertyChangeListener(PropertyChangeListener l){
    	prSup.addPropertyChangeListener(l);
    }
    public int getSnowAmount(){
    	return active.getSnowAmount();
    }
    public int getSnowProduction(){
    	return active.getSnowProduction();
    }
    public void decreaseSnowAmount(SnowUnitType type){
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
    	if(active != null && active.getSnowAmount() >= active.getSnowProduction()){
    		active.increaseSnowProduction();
    		PropertyChangeEvent event = new PropertyChangeEvent(active, SNOW_PRODUCTION, old,active.getSnowProduction());
    		prSup.firePropertyChange(event);    		
    	}
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
    	SnowUnitSprite sprite  = sU.getSprite();
        if (plOneCon.contains(sprite))
            plOneCon.removeSprite(sprite);
        if (plTwoCon.contains(sprite))
            plTwoCon.removeSprite(sprite);
        all.removeSprite(sprite);
        sprite.die();
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
        		int suaHardness = sua.getHardness(), subHardness = sub.getHardness();
        		if(sua.getType().equals(SnowUnitType.KING)){
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);
        			prSup.firePropertyChange(new PropertyChangeEvent(sua, KING_COLLISION, suaHardness, sua.getHardness()));
        		}else if(sub.getType().equals(SnowUnitType.KING)){
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);
        			prSup.firePropertyChange(new PropertyChangeEvent(sub, KING_COLLISION, subHardness, sub.getHardness()));
        		}else{
        			sua.decreaseHardness(subHardness);
        			sub.decreaseHardness(suaHardness);        			
        		}
        	}
        }
    }
}
