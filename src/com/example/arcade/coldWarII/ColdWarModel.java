package com.example.arcade.coldWarII;

import android.util.Log;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class ColdWarModel implements CollisionListener {
    private boolean menu, placing,attack;
    private ColdWarPlayer active, plOne, plTwo;
    private SnowUnitSpriteContainer plOneCon, plTwoCon;
    private boolean[][] plOneSnowUnits,plTwoSnowUnits;


    public ColdWarModel(SnowUnitSpriteContainer one, SnowUnitSpriteContainer two) {
        menu = true;
        placing = false;
        attack = false;
        plOne = new ColdWarPlayer("Arne");
        plTwo = new ColdWarPlayer("Bjarne");
        active = plOne;
        plOneCon = one;
        plTwoCon = two;
        plOneSnowUnits = new boolean[3][5];
        plTwoSnowUnits = new boolean[3][5];
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
    public void increaseSnowProduction(){
    	if(active != null)
    		active.increaseSnowProduction();
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
    public void reverseMenu() {
    	if (menu)
    		menu = false;
    	else
    		menu = true;
    }
    public void reverseAttack() {
        if (attack)
        	attack = false;
        else
        	attack = true;
    }

    public void reversePlacing() {
        if (placing)
            placing = false;
        else
            placing = true;
    }

    public boolean isPlacing() {
        return placing;
    }
    
    public boolean isAttack() {
        return attack;
    }

    public boolean isMenu() {
        return menu;
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

    public void destroySnowUnit(SnowUnitSprite s) {
        if (plOneCon.contains(s))
            plOneCon.removeSprite(s);
        else if (plTwoCon.contains(s))
            plTwoCon.removeSprite(s);
        s.die();
    }

    @Override
    public void collided(Sprite a, Sprite b) {
        SnowUnitSprite sa = (SnowUnitSprite) a;
        SnowUnitSprite sb = (SnowUnitSprite) b;
        sa.die();
        sb.die();
        Log.d("Collision!", "Model, Collision" + a.getClass().getName() + " collided with " + b.getClass().getName());
    }
}
