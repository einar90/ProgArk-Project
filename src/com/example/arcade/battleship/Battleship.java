package com.example.arcade.battleship;

import java.util.ArrayList;

import sheep.game.Sprite;
import sheep.graphics.Image;
import android.content.res.Resources;
import android.graphics.Point;

import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;

public class Battleship extends Sprite {
 
    
    // Battleship images.
    private static final Image battleshipImageHorisontal = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_x);
    private static final Image battleshipImageVertical = GraphicsHelper.getScaledImage(Controller.resources, R.drawable.battleship_y);
    
    // Battleships player1.
    private static ArrayList<Battleship> battleshipsPlayer1 = new ArrayList<Battleship>();
    private static Battleship battleship1Player1 = new Battleship(battleshipImageHorisontal, 1);
    private static Battleship battleship2Player1 = new Battleship(battleshipImageHorisontal, 1);
    private static Battleship battleship3Player1 = new Battleship(battleshipImageHorisontal, 1);
    private static Battleship battleship4Player1 = new Battleship(battleshipImageVertical, 1);
    private static Battleship battleship5Player1 = new Battleship(battleshipImageVertical, 1);
    
    // Battleships player2.
    private static ArrayList<Battleship> battleshipsPlayer2 = new ArrayList<Battleship>();
    private static Battleship battleship1Player2 = new Battleship(battleshipImageHorisontal, 2);
    private static Battleship battleship2Player2 = new Battleship(battleshipImageHorisontal, 2);
    private static Battleship battleship3Player2 = new Battleship(battleshipImageHorisontal, 2);
    private static Battleship battleship4Player2 = new Battleship(battleshipImageVertical, 2);
    private static Battleship battleship5Player2 = new Battleship(battleshipImageVertical, 2);
 
    /** Constructor 
     * @param battleshipimage */
    public Battleship(Image battleshipimage, int player) {
        super(battleshipimage);
        
        if(player == 1){
            battleshipsPlayer1.add(this);
        }else if(player == 2){
            battleshipsPlayer2.add(this);
        }
        
    }

    /** Get player1's battleships */
    public static ArrayList<Battleship> getBattleshipsPlayer1() {
        return battleshipsPlayer1;
    }
    
    /** Get player2's battleships */
    public static ArrayList<Battleship> getBattleshipsPlayer2() {
        return battleshipsPlayer2;
    }
    
    /** Set position for battleship */
    public void setBattleshipPosition(Point size) {
        setPosition(size.x, size.y);
    }
}
