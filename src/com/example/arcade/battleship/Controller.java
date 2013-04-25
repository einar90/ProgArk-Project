package com.example.arcade.battleship;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.Game;

public class Controller {
    /**
     * Fields.
     */
    
    public static final Resources resources = Game.getInstance().getResources();
    // The turn variable of the game.
    public static String currentPlayer = "Player1";
   
    // Position intervals of each players battleships.                                                BATTLESHIP 1                                                  BATTLESHIP 2                                                                               BATTLESHIP 3                                                         BATTLESHIP 4                                        BATTLESHIP 5
    public static final float[][] player1BattleshipXIntervals = new float[][] { {Map.gridXStart, Map.gridXStart+(Map.columnWidth*1)}, {Map.gridXStart+(Map.columnWidth*3), Map.gridXStart+(Map.columnWidth*5)}, {Map.gridXStart+(Map.columnWidth*1), Map.gridXStart+(Map.columnWidth*4)}, {Map.gridXStart+(Map.columnWidth*1), Map.gridXStart+(Map.columnWidth*2)}, {Map.gridXStop-(Map.columnWidth*1), Map.gridXStop} };
    public static final float[][] player1BattleshipYIntervals = new float[][] { {Map.gridYStart, Map.gridYStart+(Map.columnHeight*1)}, {Map.gridYStart+(Map.columnHeight*1), Map.gridYStart+(Map.columnHeight*2)}, {Map.gridYStop-(Map.columnHeight*1), Map.gridYStop}, {Map.gridYStart+(Map.columnHeight*1), Map.gridYStop-(Map.columnHeight*2)}, {Map.gridYStart+(Map.columnHeight*2), Map.gridYStop} };
    public static final float[][] player2BattleshipXIntervals = new float[][] { {Map.gridXStop-(Map.columnWidth), Map.gridXStop}, {Map.gridXStart+(Map.columnWidth*2) ,Map.gridXStart+(Map.columnWidth*4)}, {Map.gridXStart+(Map.columnWidth*3) ,Map.gridXStart+(Map.columnWidth*6)}, {Map.gridXStop-(Map.columnWidth*2), Map.gridXStop-(Map.columnWidth*1)},  {Map.gridXStart, Map.gridXStart+(Map.columnWidth*1)} };
    public static final float[][] player2BattleshipYIntervals = new float[][] { {Map.gridYStop-(Map.columnHeight), Map.gridYStop}, {Map.gridYStop-(Map.columnHeight*2) ,Map.gridYStop-(Map.columnHeight*1)}, {Map.gridYStart, Map.gridYStart+(Map.columnHeight*1)}, {Map.gridYStart+(Map.columnHeight*2), Map.gridYStop-(Map.columnHeight*1)}, {Map.gridYStart, Map.gridYStop-(Map.columnHeight*2)} };
    
    // Player's score.
    public static int player1Score=0, player2Score=0; 
    
    /** 
     * Sets positions for both players battleships. 
     */
    public static void setBattleshipPositions(){
        // Player1 battleship positions.
        BattleshipUserInterface.player1Battleships.get(0).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*0.5)), (int)(Map.gridYStart+(Map.columnHeight*0.5))) );
        BattleshipUserInterface.player1Battleships.get(1).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*4)), (int)(Map.gridYStart+(Map.columnHeight*1.5))) );
        BattleshipUserInterface. player1Battleships.get(2).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*2.5)), (int)(Map.gridYStop-(Map.columnHeight*0.5))) );
        BattleshipUserInterface.player1Battleships.get(3).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*1.5)), (int)(Map.gridYStart+(Map.columnHeight*3))) );
        BattleshipUserInterface.player1Battleships.get(4).setBattleshipPosition(new Point((int)(Map.gridXStop-(Map.columnWidth*0.5)), (int)(Map.gridYStop-(Map.columnHeight*2.5))) );
        
        // Player2 battleship positions - Inverted positions of Player 1.
        BattleshipUserInterface.player2Battleships.get(0).setBattleshipPosition(new Point((int)(Map.gridXStop-(Map.columnWidth/2)), (int)(Map.gridYStop-(Map.columnHeight/2))) );
        BattleshipUserInterface.player2Battleships.get(1).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*3)), (int)(Map.gridYStop-(Map.columnHeight*1.5))) ) ;
        BattleshipUserInterface. player2Battleships.get(2).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*4.5)), (int)(Map.gridYStart+(Map.columnHeight*0.5))) );
        BattleshipUserInterface.player2Battleships.get(3).setBattleshipPosition(new Point((int)(Map.gridXStop-(Map.columnWidth*1.5)), (int)(Map.gridYStop-(Map.columnHeight*3))) );
        BattleshipUserInterface.player2Battleships.get(4).setBattleshipPosition(new Point((int)(Map.gridXStart+(Map.columnWidth*0.5)), (int)(Map.gridYStart+(Map.columnHeight*2.5))) );
    }    
    
    /**
     * Check battleship1 intervals
     */
    public static boolean isBetweenPlayer1BattleshipIntervals(float x, float y){
        boolean result = false;
        
        for(int i = 0; i < player1BattleshipXIntervals.length; i++) {
            if(x >= player1BattleshipXIntervals[i][0] &&  x <= player1BattleshipXIntervals[i][1] && y >= player1BattleshipYIntervals[i][0]  && y <= player1BattleshipYIntervals[i][1]){
//                Log.d("HIT:", "battleship"+i+" to player1");
                result = true;
                // Decrease the hp of the battleship that has been touched.
                BattleshipUserInterface.player1Battleships.get(i).decreaseBattleshipHpLeft();
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Check battleship2 intervals
     */
    public static boolean isBetweenPlayer2BattleshipIntervals(float x, float y){
        boolean result = false;
        
        for(int i = 0; i < player2BattleshipXIntervals.length; i++) {
            if(x >= player2BattleshipXIntervals[i][0] &&  x <= player2BattleshipXIntervals[i][1] && y >= player2BattleshipYIntervals[i][0]  && y <= player2BattleshipYIntervals[i][1]){
//                Log.d("HIT:", "battleship"+i+" to player2");
                result = true;
                // Decrease the hp of the battleship that has been touched.
                BattleshipUserInterface.player2Battleships.get(i).decreaseBattleshipHpLeft();
                break;
            }
        }
        
        return result;
    }    
    
    /** 
     * Find the middle-X-Coordinate of a column inside the grid. 
     */
    public static float findMiddleXCoordinate(float inputX){
        float x = -1;
        
        for(int i=1; i <= Map.gridColumns; i++){
            float startInterval = Map.gridXStart + (Map.columnWidth * (i-1) );
            float stopInterval = Map.gridXStart + (Map.columnWidth * i);
            
            // Find the right interval.
            if(inputX >= startInterval && inputX < stopInterval ){
                x = startInterval + (Map.columnWidth/2);               
                break;
            }
        }
        
        return x;
    }
    
    /** 
     * Find the middle-Y-Coordinate of a column inside the grid 
     */
    public static float findMiddleYCoordinate(float inputY){
        float y = -1;
        
        for(int i=1; i <= Map.gridRows; i++){
            float startInterval = Map.gridYStart + (Map.columnHeight * (i-1) );
            float stopInterval = Map.gridYStart + (Map.columnHeight * i);
            
            // Find the right interval. 
            if(inputY >= startInterval && inputY < stopInterval ){
                y = startInterval + (Map.columnHeight/2);               
                break;
            }
        }        
        
        return y;
    }
    
    /**
     * Method that changes between Player1 and Player2.
     */
    public static void changePlayer(){
        if(currentPlayer.equals("Player1")){
            currentPlayer = "Player2";
        }else{
            currentPlayer = "Player1";
        }
    }
    
    /**
     * Returns true if the touch on screen is inside the grid, false otherwise.
     */
    public static boolean isTouchInsideGrid(MotionEvent event){
        if(event.getX() >= Map.gridXStart && event.getX() <= Map.gridXStop && event.getY() >= Map.gridYStart && event.getY() <= Map.gridYStop){
            return true;
        }
        
        return false;
    }
    
    /**
     * Returns true if the player given as an argument has battleships left, false otherwise.
     */
    public static boolean hasBattleshipsLeft(int player){
        
        //If player==1 then check if player1's battleships has hp left.
        if(player == 1){
            for(int i = 0; i < BattleshipUserInterface.player1Battleships.size(); i++) {
                if( BattleshipUserInterface.player1Battleships.get(i).getBattleshipHpLeft() > 0 ){
                    return true;
                }
            }
        
        //If player==2 then check if player2's battleships has hp left.
        }else if(player == 2){
            for(int i = 0; i < BattleshipUserInterface.player2Battleships.size(); i++) {
                if( BattleshipUserInterface.player2Battleships.get(i).getBattleshipHpLeft() > 0 ){
                    return true;
                }
            }
        }
        
        return false;       
    }
    
    /**
     * Increases the score to the player given as an argument by one.
     */
    public static void increasePlayerScore(int player){
        if(player == 1){
            player1Score++;
        }else if(player == 2){
            player2Score++;
        }        
    }
    
    /**
     * Initiate ending game user interface.
     */
    public static void setEndGameGUI(){        
        Game.getInstance().pushState(new BattleshipEndGameGUI(player1Score, player2Score));
    }
    
}// end class.
