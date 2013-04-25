package com.example.arcade.battleship;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.R;

import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;


public class BattleshipUserInterface extends State implements MiniGame {
	/** 
	 * Fields
	 */
    public Map map;
	
	
	// Each player has 5 battleships with the length: 1,2,3,4,5.
	public static ArrayList<Battleship>player1Battleships = Battleship.getBattleshipsPlayer1();
	public static ArrayList<Battleship>player2Battleships = Battleship.getBattleshipsPlayer2();	
	public static final int numberOfBattleships = player1Battleships.size();
	
	// Explosion.
	public static final int maximumExplosions = 1+2+3+4+5;
	public static final Image explosionImage = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.thermite);
	public static ArrayList<Sprite> explosionsPlayer1 = new ArrayList<Sprite>();
    public static ArrayList<Sprite> explosionsPlayer2 = new ArrayList<Sprite>();
	
	
	//Miss.
    public static final int maximumMiss = (Map.gridRows * Map.gridColumns) - maximumExplosions;
    public static final Image missHitImage = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.miss_hit);
    public static ArrayList<Sprite> missHitsPlayer1 = new ArrayList<Sprite>();
    public static ArrayList<Sprite> missHitsPlayer2 = new ArrayList<Sprite>();

    

    /**
     * Constructor
     */
    public BattleshipUserInterface(){
    	map = new Map();
    	Controller.setBattleshipPositions();    	
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
    
    /**
     * Updating the sprites.
     * @param dt
     */
    public void updateSprites(float dt){
        // Update battleships.
        for(int i=0; i < numberOfBattleships; i++) {
            if(Controller.currentPlayer.equals("Player1")){
                player1Battleships.get(i).update(dt);
            }else{
                player2Battleships.get(i).update(dt);
            }
        }
        
        // Update explosions.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < explosionsPlayer1.size(); i++) {
                if(explosionsPlayer1.get(i) != null){
                    explosionsPlayer1.get(i).update(dt);
                }
            }
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < explosionsPlayer2.size(); i++) {
                if(explosionsPlayer2.get(i) != null){
                    explosionsPlayer2.get(i).update(dt);
                }
            }
        }
                
    }
    
    /** Draw the sprite's on screen */
    public void drawSprites(Canvas canvas){
        
        // Draw battleships.
        for(int i=0; i < numberOfBattleships; i++) {
            if(Controller.currentPlayer.equals("Player1")){
                player1Battleships.get(i).draw(canvas);
            }else{
                player2Battleships.get(i).draw(canvas);
            }
        }
        
        // Draw explosions.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < explosionsPlayer1.size(); i++) {
                if(explosionsPlayer1.get(i) != null){
                    explosionsPlayer1.get(i).draw(canvas);
                }
            }
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < explosionsPlayer2.size(); i++) {
                if(explosionsPlayer2.get(i) != null){
                    explosionsPlayer2.get(i).draw(canvas);
                }
            }
        }        

    }
    
    /** 
     * Touch screen events 
     */
    @Override
    public boolean onTouchUp(MotionEvent event) {
        // Touch is inside the grid.
        if(event.getX() >= Map.gridXStart && event.getX() <= Map.gridXStop && event.getY() >= Map.gridYStart && event.getY() <= Map.gridYStop){
            Log.d("TouchUp", "onTouchUp!");
            Log.d("X:", String.valueOf(event.getX()) );
            Log.d("Y:", String.valueOf(event.getY()) );
            
            // Transform the touch-coordinates to middle-point-coordinates of a column in the grid.
            float x = Controller.findMiddleXCoordinate(event.getX());
            float y = Controller.findMiddleYCoordinate(event.getY());
            
            /* TODO */            
            // Check if it is a hit or miss.
            if(Controller.currentPlayer.equals("Player1")){
                for(int i=0; i < player2Battleships.size(); i++){
                    if(player2Battleships.get(i).getBoundingBox().contains(x, y) ){
                        Sprite explosion = new Sprite(explosionImage);
                        explosion.setPosition(x, y);
                       
                        if(Controller.currentPlayer.equals("Player1")){
                            explosionsPlayer1.add(explosion);
                        }else if(Controller.currentPlayer.equals("Player2")){
                            explosionsPlayer2.add(explosion);
                        } 
                        
                        break;
                    }
                }
            }
            
            
                   
            
            return true;    
        }
        return true;    
    } 

}
