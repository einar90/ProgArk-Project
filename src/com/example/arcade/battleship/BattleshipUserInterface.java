package com.example.arcade.battleship;

import android.graphics.Canvas;
import android.graphics.Point;

import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;

import sheep.game.State;


public class BattleshipUserInterface extends State implements MiniGame {
	Map map;
	Battleship battleship1, battleship2;
    
    /**
     * Constructor
     */
    public BattleshipUserInterface(Point displaySize) {
    	map = new Map(); 
    	getSprites(displaySize);
    }

    /**
     * Draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
    	map.drawMap(canvas);
        drawSprites(canvas);
    }

    /**
     * Update
     */
    public void update(float dt) {
    	super.update(dt);
    	updateSprites(dt);
    }
    
    public void getSprites(Point displaySize){
    	battleship1 = Battleship.getBattleship1();
    	battleship2 = Battleship.getBattleship2();
    	Battleship.setInitialBattleshipPositions(new Point(displaySize.x, displaySize.y));
    }
    
    public void updateSprites(float dt){
    	battleship1.update(dt);
    	battleship2.update(dt);
    }
    
    public void drawSprites(Canvas canvas){
    	battleship1.draw(canvas);
    	battleship2.draw(canvas);
    }

    @Override
    public void launchGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void exitGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public HighscoreList getHighscoreList() {
        // TODO Auto-generated method stub
        return null;
    }

}
