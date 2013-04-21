package com.example.arcade.tankwars;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.MiniGame;
import com.example.arcade.tankwars.explosions.Explosion;
import com.example.arcade.tankwars.projectiles.Projectile;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.gui.TextButton;

import java.util.Dictionary;

/**
 * Created by:
 * User: Dzenan
 * Date: 11.03.13
 * Time: 13:39
 */
public class TankWarsUserInterface extends State implements MiniGame, CollisionListener {

    private static Point displaySize = GraphicsHelper.getDisplaySize();
    private static final Font font = new Font(64, 64, 64, 50, Typeface.SANS_SERIF, Typeface.NORMAL);

    private CollisionLayer collisionLayer = new CollisionLayer();
    private Map map;

    private Tank playerOneTank;
    private Tank playerTwoTank;
    private static Projectile currentProjectile = null;
    private static Explosion currentExplosion = null;

    private TextButton bulletButton;
    private TextButton shellButton;
    private TextButton thermiteButton;
    private TextButton nukeButton;

    /**
     * Constructor for the TankWarsUserinterface, does some
     * important Initializing.
     */
    public TankWarsUserInterface() {

        map = new Map();
        getSprites();

        addSpritesToCollisionLayer();
        addSpritesToCollisionListener();

    }

    public static void createExplosion(Explosion explosion) {
        currentExplosion = explosion;
    }


    public static void removeExplosion() {
        currentExplosion = null;
    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

        map.drawMap(canvas);

        drawSprites(canvas);

        canvas.drawText(Map.getWindString(), 20, 50, font);
        drawAmmoText(canvas);
        drawHpText(canvas);
        drawActivePlayerIndicator(canvas);

        if (currentProjectile != null) {
            currentProjectile.draw(canvas);
        }

        if (currentExplosion != null) {
            currentExplosion.draw(canvas);
        }
    }

    private void drawAmmoText(Canvas canvas) {
        Dictionary ammo = Controller.getActiveTank().getProjectileAmmo();
        String bulletText = "Bullet: " + ammo.get("Bullet");
        String tankShellText = "Shells: " + ammo.get("TankShell");
        String thermiteText = "Thermite: " + ammo.get("ThermiteShells");
        String nukeText = "Nukes: " + ammo.get("Nukes");
        String pickedAmmoText = "Picked: " + Controller.getChosenProjectile();

        int y = displaySize.y - displaySize.y / 8;
        int yIncr = displaySize.y / 10;
        int xWidth = displaySize.x / 4;
        int xIncr = displaySize.x / 10;

        bulletButton = new TextButton(xIncr, y, bulletText);
        shellButton = new TextButton(xWidth + xIncr, y, tankShellText);
        thermiteButton = new TextButton(xWidth * 2 + xIncr, y, thermiteText);
        nukeButton = new TextButton(xWidth * 3 + xIncr, y, nukeText);

        bulletButton.draw(canvas);
        shellButton.draw(canvas);
        thermiteButton.draw(canvas);
        nukeButton.draw(canvas);
        canvas.drawText(pickedAmmoText, xWidth * 2 - xIncr, y + yIncr, font);
    }


    private void drawHpText(Canvas canvas) {
        int yPos = displaySize.y / 5;
        int xPos1 = displaySize.x / 10;
        int xPos2 = displaySize.x / 8 * 7;
        canvas.drawText(Tank.getTank1().getHpString(), xPos1, yPos, font);
        canvas.drawText(Tank.getTank2().getHpString(), xPos2, yPos, font);
    }


    private void drawActivePlayerIndicator(Canvas canvas) {
        int yPos = displaySize.y / 7;
        int xPos;

        if (Controller.getActiveTank() == Tank.getTank1()) {
            xPos = displaySize.x / 10;
        } else xPos = displaySize.x / 8 * 7;

        canvas.drawText("__", xPos, yPos, font);
    }

    private void changeAmmo(MotionEvent event) {
        if (bulletButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Controller.setChosenProjectile("Bullet");
        } else if (shellButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Controller.setChosenProjectile("TankShell");
        } else if (thermiteButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Controller.setChosenProjectile("ThermiteShells");
        } else if (nukeButton.getBoundingBox().contains(event.getX(), event.getY())) {
            Controller.setChosenProjectile("Nukes");
        }
    }

    public void update(float dt) {
        super.update(dt);

        map.updateMap(dt);

        updateSprites(dt);

        if (currentProjectile != null) {
            currentProjectile.update(dt);
        }

        if (currentExplosion != null) {
            currentExplosion.update(dt);
        }
    }

    /**
     * Returns the singletons to the correct var's in
     * TankWarsUserInterface
     */
    private void getSprites() {
        playerOneTank = Tank.getTank1();
        playerTwoTank = Tank.getTank2();
        Controller.setInitialTankPositions();
        Controller.setStartSpeed();
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

        // Add map sprites
        for (Sprite sprite : Map.getMapSprites()) {
            collisionLayer.addSprite(sprite);
        }

    }

    /**
     * Adds the tanks to the collision listener.
     */
    private void addSpritesToCollisionListener() {
        playerOneTank.addCollisionListener(this);
        playerTwoTank.addCollisionListener(this);

        // Add map sprites
        for (Sprite sprite : Map.getMapSprites()) {
            sprite.addCollisionListener(this);
        }
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

    public static void removeCurrentProjectile() {
        currentProjectile = null;
    }


    @Override
    public void collided(Sprite a, Sprite b) {

        // To avoid friendly fire
        if ((a == Controller.getActiveTank() || b == Controller.getActiveTank())
                && (a instanceof Projectile || b instanceof Projectile)) {
            return;
        }

        // Projectile collision. The projectile != null check is to avoid a strange bug
        if ((a instanceof Projectile || b instanceof Projectile) && currentProjectile != null) {
            currentProjectile.explode();
            collisionLayer.removeSprite(currentProjectile);
            currentProjectile = null;
            Controller.changeActiveTank();
            return;
        }

        // Initial tank fall collision
        if (a.getClass() == Tank.class) {
            if (b.getClass() == Sprite.class) {    //Denne er rævva
                Controller.stopStartSpeed();
            }
        }
    }

    @Override
    public boolean onTouchDown(MotionEvent event) {

        // Attempting to change ammo and returning if touch is below ground level
        if (event.getY() > displaySize.y - Map.getGroundHeight()) {
            changeAmmo(event);
            return true;
        }

        // Not reacting if there's a projectile in the air
        if (currentProjectile != null) {
            return true;
        }

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

        // Attempting to change ammo and returning if touch is below ground level
        if (event.getY() > displaySize.y - Map.getGroundHeight()) {
            changeAmmo(event);
            return true;
        }

        // Not reacting if there's a projectile in the air
        if (currentProjectile != null) {
            return true;
        }

        Controller.calculatePower();
        currentProjectile = Controller.getProjectile();
        Controller.getActiveTank().reduceAmmo();
        collisionLayer.addSprite(currentProjectile);
        Map.changeWindVector();
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
