package com.example.arcade.battleship;

import java.util.ArrayList;
import sheep.game.Sprite;
import sheep.graphics.Image;
import android.graphics.Point;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;

public class Battleship extends Sprite { 
   
    /**
     * Fields.
     */
    
    // Battleship images.
    private static final Image battleship1ImageHorisontal = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_x1);
    private static final Image battleship2ImageHorisontal = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_x2);
    private static final Image battleship3ImageHorisontal = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_x3);    
    private static final Image battleship4ImageVertical = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_y4);
    private static final Image battleship5ImageVertical = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_y5);
     
    // Battleships player1.
    private static ArrayList<Battleship> battleshipsPlayer1 = new ArrayList<Battleship>();
    private static Battleship battleship1Player1 = new Battleship(battleship1ImageHorisontal, 1, 1);
    private static Battleship battleship2Player1 = new Battleship(battleship2ImageHorisontal, 1, 2);
    private static Battleship battleship3Player1 = new Battleship(battleship3ImageHorisontal, 1, 3);
    private static Battleship battleship4Player1 = new Battleship(battleship4ImageVertical, 1, 4);
    private static Battleship battleship5Player1 = new Battleship(battleship5ImageVertical, 1, 5);
    
    // Battleships player2.
    private static ArrayList<Battleship> battleshipsPlayer2 = new ArrayList<Battleship>();
    private static Battleship battleship1Player2 = new Battleship(battleship1ImageHorisontal, 2, 1);
    private static Battleship battleship2Player2 = new Battleship(battleship2ImageHorisontal, 2, 2);
    private static Battleship battleship3Player2 = new Battleship(battleship3ImageHorisontal, 2, 3);
    private static Battleship battleship4Player2 = new Battleship(battleship4ImageVertical, 2, 4);
    private static Battleship battleship5Player2 = new Battleship(battleship5ImageVertical, 2, 5);
    
    // Battleship Hp left. Decrease this for every hit.
    private int hpLeft;
 
    
    /** Constructor 
     * @param battleshipimage 
     */
    public Battleship(Image battleshipimage, int player, int hpLeft) {
        super(battleshipimage);
        this.hpLeft = hpLeft;
       
        // Adding this battleship to the correct players battleship arraylist.
        if(player == 1){
            battleshipsPlayer1.add(this);
        }else if(player == 2){
            battleshipsPlayer2.add(this);
        }        
    }

    /** 
     * Returns the arraylist containing Player1's battleships. 
     */
    public static ArrayList<Battleship> getBattleshipsPlayer1() {
        return battleshipsPlayer1;
    }
    
    /** 
     * Returns the arraylist containing Player2's battleships. 
     */
    public static ArrayList<Battleship> getBattleshipsPlayer2() {
        return battleshipsPlayer2;
    }
    
    /** 
     * Sets position for this battleship. 
     */
    public void setBattleshipPosition(Point size) {
        setPosition(size.x, size.y);
    }
    
    /**
     * Returns the amount of hp this battleship has left.
     */
    public int getBattleshipHpLeft(){
        return this.hpLeft;
    }
    
    /**
     * Decreases the amount of hp this battleship has left by one.
     */
    public void decreaseBattleshipHpLeft(){
        if(this.hpLeft-1 >= 0){
            this.hpLeft--;
        }
    }  
    
}// end class.
