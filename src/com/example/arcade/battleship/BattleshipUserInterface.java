package com.example.arcade.battleship;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.MiniGame;
import com.example.arcade.R;
import com.example.arcade.utilities.Constants;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;

public class BattleshipUserInterface extends State implements MiniGame {
	/** 
	 * Fields
	 */
    public Map map;	
    private static final Font font = new Font(0, 0, 0, Map.columnHeight/2, Typeface.SANS_SERIF, Typeface.NORMAL);
	
	// Each player has 5 battleships with the length: 1,2,3,4,5.
	public static ArrayList<Battleship>player1Battleships = Battleship.getBattleshipsPlayer1();
	public static ArrayList<Battleship>player2Battleships = Battleship.getBattleshipsPlayer2();	
	public static final int numberOfBattleships = player1Battleships.size();
	
	// Explosion.
	public static final Image explosionImage = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.thermite);
	public static ArrayList<Sprite> explosionsPlayer1 = new ArrayList<Sprite>();
    public static ArrayList<Sprite> explosionsPlayer2 = new ArrayList<Sprite>();
	
	
	//Miss.
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
     * Draw all of the elements on the screen.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        map.drawMap(canvas);
        canvas.drawText(Controller.currentPlayer, (float)(Constants.WINDOW_WIDTH*0.45), Map.columnHeight/2, font);
        drawSprites(canvas);
    }

    /**
     * Update the screen.
     */
    public void update(float dt) {
    	super.update(dt);
    	updateSprites(dt);
    }
    
    /**
     * Updating the sprites on the screen: Battleships, Explosions, Miss hits.
     * @param dt
     */
    public void updateSprites(float dt){
        // Update battleships.
        for(int i=0; i < numberOfBattleships; i++) {
            // PLAYER 1.
            if(Controller.currentPlayer.equals("Player1")){
                player2Battleships.get(i).update(dt);
            
            // PLAYER 2.
            }else{
                player1Battleships.get(i).update(dt);
            }
        }
        
        // Update explosions for PLAYER 1.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < explosionsPlayer2.size(); i++) {
                if(explosionsPlayer2.get(i) != null){
                    explosionsPlayer2.get(i).update(dt);
                }
            }
        
        // Update explosions for PLAYER 2. 
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < explosionsPlayer1.size(); i++) {
                if(explosionsPlayer1.get(i) != null){
                    explosionsPlayer1.get(i).update(dt);
                }
            }
        }
        
        // Update miss hits for PLAYER 1.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < missHitsPlayer2.size(); i++) {
                if(missHitsPlayer2.get(i) != null){
                    missHitsPlayer2.get(i).update(dt);
                }
            }
        
        // Update miss hits for PLAYER 2.
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < missHitsPlayer1.size(); i++) {
                if(missHitsPlayer1.get(i) != null){
                    missHitsPlayer1.get(i).update(dt);
                }
            }
        }
                
    }
    
    /** 
     * Draws the sprites on the screen: Battleships, Explosions, Miss hits.
     */
    public void drawSprites(Canvas canvas){
        
        // Draw battleships.
        for(int i=0; i < numberOfBattleships; i++) {
            //PLAYER 1
            if(Controller.currentPlayer.equals("Player1")){
                // Only show battleships that is sunk.
                if(player2Battleships.get(i).getBattleshipHpLeft() == 0){
                    player2Battleships.get(i).draw(canvas);
                }
             
            // PLAYER 2    
            }else if(Controller.currentPlayer.equals("Player2")){
                // Only show battleships that is sunk.
                if(player1Battleships.get(i).getBattleshipHpLeft() == 0){
                    player1Battleships.get(i).draw(canvas);
                }
            }
        }
        
        // Draw explosions for PLAYER 1.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < explosionsPlayer2.size(); i++) {
                if(explosionsPlayer2.get(i) != null){
                    explosionsPlayer2.get(i).draw(canvas);
                }
            }
       
        // Draw explosions for PLAYER 2.
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < explosionsPlayer1.size(); i++) {
                if(explosionsPlayer1.get(i) != null){
                    explosionsPlayer1.get(i).draw(canvas);
                }
            }
        } 
        
        // Draw miss hits for PLAYER 1.
        if(Controller.currentPlayer.equals("Player1")){
            for(int i=0; i < missHitsPlayer2.size(); i++) {
                if(missHitsPlayer2.get(i) != null){
                    missHitsPlayer2.get(i).draw(canvas);
                }
            }
        
        // Draw miss hits for PLAYER 2.
        }else if(Controller.currentPlayer.equals("Player2")){
            for(int i=0; i < missHitsPlayer1.size(); i++) {
                if(missHitsPlayer1.get(i) != null){
                    missHitsPlayer1.get(i).draw(canvas);
                }
            }
        } 
    }// end drawSprites.
    
    /** 
     * Touch screen events 
     */
    @Override
    public boolean onTouchUp(MotionEvent event) {
        // Checks that the touch is inside the grid.
        if(Controller.isTouchInsideGrid(event)){
//            Log.d("X:", String.valueOf(event.getX()) );
//            Log.d("Y:", String.valueOf(event.getY()) );
            
            // Transform the touch-coordinates into middle-point-coordinates of the touched column in the grid.
            float x = Controller.findMiddleXCoordinate(event.getX());
            float y = Controller.findMiddleYCoordinate(event.getY());
            
            //PLAYER 1
            if(Controller.currentPlayer.equals("Player1") ){
                
                //Check if it is a hit.
                if( Controller.isBetweenPlayer2BattleshipIntervals(x, y) ){
                    Sprite explosion = new Sprite(explosionImage);
                    explosion.setPosition(x, y);
                    explosionsPlayer2.add(explosion);
                    
                    // Increase score to the player given as argument.
                    Controller.increasePlayerScore(1);
                    
                    // If player2 don't have any battleships left then end game.
                    if(!Controller.hasBattleshipsLeft(2) ){
//                        Log.d("Player1:", "HAS WON!" );                        
                        Controller.setEndGameGUI();
                    }
                
                // It is a miss hit.
                }else{
                    Sprite missHit = new Sprite(missHitImage);
                    missHit.setPosition(x, y);
                    missHitsPlayer2.add(missHit);
                    
                    // Change player.
                    Controller.changePlayer();
                }               
                
            // PLAYER 2
            }else if(Controller.currentPlayer.equals("Player2")){
              
                //Check if it is a hit.
                if( Controller.isBetweenPlayer1BattleshipIntervals(x, y) ){
                    Sprite explosion = new Sprite(explosionImage);
                    explosion.setPosition(x, y);
                    explosionsPlayer1.add(explosion);
                    
                    // Increase score to the player given as argument.
                    Controller.increasePlayerScore(2);
                    
                    // If player1 don't have any battleships left then end game.
                    if(!Controller.hasBattleshipsLeft(1) ){
//                        Log.d("Player2:", "HAS WON!" );                        
                        Controller.setEndGameGUI();
                    }
                 
                // It is a miss hit.
                }else{
                    Sprite missHit = new Sprite(missHitImage);
                    missHit.setPosition(x, y);
                    missHitsPlayer1.add(missHit);
                    
                    // Change player.
                    Controller.changePlayer();
                }                
                
            }                    
            
            return true;    
        }
        
        return true;    
    }    

}// end class.
