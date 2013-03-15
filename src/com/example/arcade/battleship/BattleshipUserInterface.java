package com.example.arcade.battleship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import com.example.arcade.Scaling;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;


public class BattleshipUserInterface extends State implements MiniGame {
    Point displaySize;
    Image battleshipXImage, battleshipYImage;
    Sprite battleshipX, battleshipY;

    /**
     * Constructor
     */
    public BattleshipUserInterface(Point displaySize) {
        this.displaySize = displaySize;
        battleshipXImage = Scaling.getScaledImage("battleshipX.png");
        battleshipX = new Sprite(battleshipXImage);
    }

    /**
     * Draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.BLUE);
        battleshipX.draw(canvas);
    }

    /**
     * Update
     */
    public void update(float dt) {
        battleshipX.update(dt);
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
