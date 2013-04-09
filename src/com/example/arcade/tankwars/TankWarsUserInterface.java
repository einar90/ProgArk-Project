package com.example.arcade.tankwars;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.HighscoreList;
import com.example.arcade.MiniGame;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class TankWarsUserInterface extends State implements MiniGame, CollisionListener {


    CollisionLayer collisionLayer = new CollisionLayer();

    private static final Font font = new Font(64, 64, 64, 50, Typeface.SANS_SERIF, Typeface.NORMAL);

    Map map;
    Tank playerOneTank, playerTwoTank;
    Sprite playerOneBarrel, playerTwoBarrel;

    public TankWarsUserInterface(Point displaySize, Resources resources) {

        map = new Map();
        getSprites(displaySize);

        addSpritesToCollisionLayer();
        addSpritesToCollisionListener();

    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.drawMap(canvas);

        drawSprites(canvas);

        canvas.drawText(Map.getWindString(), 20, 50, font);

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
        Tank.setStartSpeed();

    }

    private void updateSprites(float dt) {
        playerOneTank.update(dt);
        playerTwoTank.update(dt);
        Tank.getTankBarrel1().update(dt);
        Tank.getTankBarrel2().update(dt);
        collisionLayer.update(dt);


    }

    private void addSpritesToCollisionLayer() {
        collisionLayer.addSprite(playerOneTank);
        collisionLayer.addSprite(playerTwoTank);
        Map.addToCollisionLayer(collisionLayer);


    }

    private void addSpritesToCollisionListener() {
        playerOneTank.addCollisionListener(this);
        playerTwoTank.addCollisionListener(this);

    }

    private void drawSprites(Canvas canvas) {
        Tank.getTankBarrel2().draw(canvas);
        Tank.getTankBarrel1().draw(canvas);
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
        Log.d("Collision", "Something collided: " + a.getClass().toString() + " and " + b.getClass().toString());
        if (a.getClass() == Tank.class) {
            if (b.getClass() == Sprite.class) {    //Denne er rævva
                Tank.stopStartSpeed();

            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onTouchDown(MotionEvent event) {
        Controller.aimBarrel(Tank.getTank1(), new Point((int) event.getX(), (int) event.getY()));
        return true;
    }
}
