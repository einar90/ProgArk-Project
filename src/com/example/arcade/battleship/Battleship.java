package com.example.arcade.battleship;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.content.res.Resources;
import android.graphics.Point;

import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;

public class Battleship extends Sprite {
 
	private static final Resources resources = Game.getInstance().getResources();
    
    // Battleship1.
    private static final Image battleshipImage1 = GraphicsHelper.getScaledImage(resources, R.drawable.battleship_x);
    private static Battleship battleship1 = new Battleship(battleshipImage1);
    
    // Battleship2.
    private static final Image battleshipImage2 = GraphicsHelper.getScaledImage(resources, R.drawable.battleship_y);
    private static Battleship battleship2 = new Battleship(battleshipImage2);
    
    private int hp;
    
    
    /** Constructor */
    public Battleship(Image battleshipimage) {
    	super(battleshipimage);
    }
    
    // Get battleships.
    public static Battleship getBattleship1() {
    	return battleship1;
    }
    
    public static Battleship getBattleship2() {
    	return battleship2;
    }
    
    // Set initial positions for battleships.
    public static void setInitialBattleshipPositions(Point size) {
    	battleship1.setPosition(size.x / 2, size.y / 2);
    	battleship2.setPosition(size.x / 4, size.y / 4);
    }
}
