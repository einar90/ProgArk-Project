package com.example.arcade.battleship;

import com.example.arcade.utilities.Constants;

import android.graphics.Canvas;
import android.graphics.Color;

public class Map {    
	
    public String currentPlayer = "PLAYER 1";
	public static final int gridRows = 7;
	public static final int gridColumns = 7;
	
	public static final float gridHeight = (Constants.WINDOW_HEIGHT-100), gridWidth = gridHeight;
	public static final float gridYStart = 50, gridXStart = (Constants.WINDOW_WIDTH-gridWidth)/2;
	public static final float gridYStop = 50 + gridHeight, gridXStop = (Constants.WINDOW_WIDTH-gridWidth)/2 + gridWidth;
	public static final float columnWidth = gridWidth / gridColumns, columnHeight = columnWidth;
    
	/** Constructor */
	public Map() {
		
	}
	
	/** Draws the map */
	public void drawMap(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        canvas.drawText(currentPlayer, canvas.getWidth() / 2, 10, sheep.graphics.Color.WHITE);
        drawGrid(canvas);
	}
	
	/** Draws the grid */
	public void drawGrid(Canvas canvas){
	    for(int i=0; i < gridRows+1; i++) {
            // Horizontal line
	        canvas.drawLine(gridXStart, gridYStart + (i*gridHeight/gridRows), gridXStop, gridYStart + (i*gridHeight/gridRows), sheep.graphics.Color.WHITE);
	        // Vertical line
	        canvas.drawLine(gridXStart + (i*gridHeight/gridRows), gridYStart, gridXStart + (i*gridHeight/gridRows), gridYStop, sheep.graphics.Color.WHITE);
        }
	}
	
	/** Find the middle-X-Coordinate of a column inside the grid */
    public static float findMiddleXCoordinate(float inputX){
        float x = -1;
        
        for(int i=1; i <= gridColumns; i++){
            float startInterval = gridXStart + (columnWidth * (i-1) );
            float stopInterval = gridXStart + (columnWidth * i);
            
            // Find the right interval.
            if(inputX >= startInterval && inputX < stopInterval ){
                x = startInterval + (columnWidth/2);               
                break;
            }
        }
        
        return x;
    }
    
    /** Find the middle-Y-Coordinate of a column inside the grid */
    public static float findMiddleYCoordinate(float inputY){
        float y = -1;
        
        for(int i=1; i <= gridRows; i++){
            float startInterval = gridYStart + (columnHeight * (i-1) );
            float stopInterval = gridYStart + (columnHeight * i);
            
            // Find the right interval. 
            if(inputY >= startInterval && inputY < stopInterval ){
                y = startInterval + (columnHeight/2);               
                break;
            }
        }        
        
        return y;
    }
}
