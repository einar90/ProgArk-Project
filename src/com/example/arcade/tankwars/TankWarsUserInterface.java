package com.example.arcade.tankwars;

import android.graphics.Canvas;
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
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 */
public class TankWarsUserInterface extends State implements MiniGame, CollisionListener {


    private CollisionLayer collisionLayer = new CollisionLayer();

    private static final Font font = new Font(64, 64, 64, 50, Typeface.SANS_SERIF, Typeface.NORMAL);

    private Map map;
    private Tank playerOneTank;
    private Tank playerTwoTank;
    private Projectile currentProjectile = null;

    /**
     * Constructor for the TankWarsUserinterface, does some
     * important Initializing.
     *
     * @param displaySize
     */
    public TankWarsUserInterface(Point displaySize) {

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

        if (currentProjectile != null) {
            currentProjectile.draw(canvas);
        }
    }

    public void update(float dt) {
        super.update(dt);

        map.updateMap(dt);

        updateSprites(dt);

        if (currentProjectile != null) {
            currentProjectile.update(dt);
        }

    }

    /**
     * Returns the singletons to the correct var's in
     * TankWarsUserInterface
     *
     * @param displaySize
     */
    private void getSprites(Point displaySize) {
        playerOneTank = Tank.getTank1();
        playerTwoTank = Tank.getTank2();
        Tank.setInitialTankPositions(new Point(displaySize.x, 100));
        Tank.setStartSpeed();

    }

    /**
     * Handles the updating of all the sprites
     *
     * @param dt
     */
    private void updateSprites(float dt) {
        playerOneTank.update(dt);
        playerTwoTank.update(dt);
        Tank.getTankBarrel1().update(dt);
        Tank.getTankBarrel2().update(dt);
        collisionLayer.update(dt);


    }

    /**
     * Adds the sprites to the collision layer.
     */
    private void addSpritesToCollisionLayer() {
        collisionLayer.addSprite(playerOneTank);
        collisionLayer.addSprite(playerTwoTank);
        Map.addToCollisionLayer(collisionLayer);


    }

    /**
     * Adds the tanks to the collision listener.
     */
    private void addSpritesToCollisionListener() {
        playerOneTank.addCollisionListener(this);
        playerTwoTank.addCollisionListener(this);

    }

    /**
     * Handles drawing all the sprites
     *
     * @param canvas
     */
    private void drawSprites(Canvas canvas) {
        Tank.getTankBarrel2().draw(canvas);
        Tank.getTankBarrel1().draw(canvas);
        playerOneTank.draw(canvas);
        playerTwoTank.draw(canvas);


    }

    @Override
    public void launchGame() {
        // Load something?
    }

    @Override
    public void exitGame() {
        // Save the highscore list
    }

    @Override
    public HighscoreList getHighscoreList() {
        return null;  // Return the highscore list
    }

    @Override
    public void collided(Sprite a, Sprite b) {
        Log.d("Collision", "Something collided: " + a.getClass().toString() + " and " + b.getClass().toString());


        // To avoid friendly fire
        if ((a == Controller.getFiringTank() || b == Controller.getFiringTank())
                && (a instanceof Projectile || b instanceof Projectile)) {
            return;
        }

        // Projectile collision
        if (a instanceof Projectile || b instanceof Projectile) {
            Log.d("Collision", "Projectile collided");
            // Explode
            // Check tank in radius
            // If so, reduce hp
            collisionLayer.removeSprite(currentProjectile);
            currentProjectile = null;
            return;
        }

        // Initial tank fall collision
        if (a.getClass() == Tank.class) {
            if (b.getClass() == Sprite.class) {    //Denne er rævva
                Tank.stopStartSpeed();
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onTouchDown(MotionEvent event) {
        Controller.aimBarrel(new Point((int) event.getX(), (int) event.getY()));

        // Clearing current projectile if it already exists.
        //   This may happen if a player attempts to fire a shot while one is already in the air.
        if (currentProjectile != null) {
            collisionLayer.removeSprite(currentProjectile);
            currentProjectile = null;
        }

        Controller.recordPower();


        return true;
    }

    @Override
    public boolean onTouchUp(MotionEvent event) {
        Controller.calculatePower();
        currentProjectile = Controller.getProjectile();
        collisionLayer.addSprite(currentProjectile);
        Controller.changeActiveTank();
        Map.changeWindVector();
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
