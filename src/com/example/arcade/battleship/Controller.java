package com.example.arcade.battleship;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.arcade.Game;

public class Controller {
    
    public static final Resources resources = Game.getInstance().getResources();
    public static String currentPlayer = "Player1";
    public static ArrayList<Float> player1BattleshipsCoordinates = new ArrayList<Float>();
    public static ArrayList<Float> player2BattleshipsCoordinates = new ArrayList<Float>();    
    
    /** 
     * Sets positions for both players battleships 
     */
    public static void setBattleshipPositions(){
        // Player1.
        BattleshipUserInterface.player1Battleships.get(0).setBattleshipPosition(new Point((int)Map.gridXStart+28, (int)Map.gridYStart+27));
        BattleshipUserInterface.player1Battleships.get(1).setBattleshipPosition(new Point((int)Map.gridXStart+218, (int)Map.gridYStart+81));
        BattleshipUserInterface. player1Battleships.get(2).setBattleshipPosition(new Point((int)Map.gridXStart+140, (int)Map.gridYStop-28));
        BattleshipUserInterface.player1Battleships.get(3).setBattleshipPosition(new Point((int)Map.gridXStop-300, (int)Map.gridYStop-220));
        BattleshipUserInterface.player1Battleships.get(4).setBattleshipPosition(new Point((int)Map.gridXStop-30, (int)Map.gridYStop-135));
        
        // Player2 - Inverted positions of Player 1.
        BattleshipUserInterface.player2Battleships.get(0).setBattleshipPosition(new Point((int)Map.gridXStart+28, (int)Map.gridYStop-27));
        BattleshipUserInterface.player2Battleships.get(1).setBattleshipPosition(new Point((int)Map.gridXStop-218, (int)Map.gridYStop-81));
        BattleshipUserInterface. player2Battleships.get(2).setBattleshipPosition(new Point((int)Map.gridXStop-140, (int)Map.gridYStart+28));
        BattleshipUserInterface.player2Battleships.get(3).setBattleshipPosition(new Point((int)Map.gridXStart+300, (int)Map.gridYStart+220));
        BattleshipUserInterface.player2Battleships.get(4).setBattleshipPosition(new Point((int)Map.gridXStart+30, (int)Map.gridYStart+135));
    }
    
    /**
     * Calculates the coordinates 
     */
    
    
    
    /** Find the middle-X-Coordinate of a column inside the grid */
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
    
    /** Find the middle-Y-Coordinate of a column inside the grid */
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
     * Sets the current player
     */
    public static void setCurrentPlayer(String player){
        currentPlayer = player;
    }
}
