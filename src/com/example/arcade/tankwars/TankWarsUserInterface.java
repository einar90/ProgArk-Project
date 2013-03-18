package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class TankWarsUserInterface extends State implements MiniGame, CollisionListener {

    Point displaySize;

    Map map;
    Tank playerOneTank, playerTwoTank;

    public TankWarsUserInterface(Point displaySize, Resources resources) {

        map = new Map();
        getSprites(displaySize);
        addSpritesToCollisionLayer();

    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.drawMap(canvas);

        drawSprites(canvas);

    }

    public void update(float dt) {
        super.update(dt);

        map.updateMap(dt);

        updateSprites(dt);

    }

    private void getSprites(Point displaySize) {
        playerOneTank = Tank.getTank1();
        playerTwoTank = Tank.getTank2();
        Tank.setInitialTankPositions(new Point(displaySize.x, 100));

    }

    private void updateSprites(float dt) {
        playerOneTank.update(dt);
        playerTwoTank.update(dt);


    }

    private void addSpritesToCollisionLayer() {
        playerOneTank.addCollisionListener(this);
        playerTwoTank.addCollisionListener(this);


    }

    private void drawSprites(Canvas canvas) {
        playerOneTank.draw(canvas);
        playerTwoTank.draw(canvas);

    }

    @Override
    public void launchGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void exitGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HighscoreList getHighscoreList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void collided(Sprite a, Sprite b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
