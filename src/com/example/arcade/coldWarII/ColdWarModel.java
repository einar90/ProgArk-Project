package com.example.arcade.coldWarII;

import android.util.Log;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;

public class ColdWarModel implements CollisionListener {
    private boolean menu, placing,attack;
    private ColdWarPlayer active, plOne, plTwo;
    private SnowUnitSpriteContainer plOneCon, plTwoCon;


    public ColdWarModel(SnowUnitSpriteContainer one, SnowUnitSpriteContainer two) {
        menu = true;
        placing = false;
        plOne = new ColdWarPlayer("Christian");
        plTwo = new ColdWarPlayer("J�rn");
        active = plOne;
        plOneCon = one;
        plTwoCon = two;
    }

    public void reverseMenu() {
        if (menu)
            menu = false;
        else
            menu = true;
    }
    public void reverseAttack() {
        if (attack)
        	attack = false;
        else
        	attack = true;
    }

    public void reversePlacing() {
        if (placing)
            placing = false;
        else
            placing = true;
    }

    public boolean isPlacing() {
        return placing;
    }
    
    public boolean isAttack() {
        return attack;
    }

    public boolean isMenu() {
        return menu;
    }

    /**
     * @return
     */
    public boolean isPlayerOne() {
        if (active != null && active.equals(plOne))
            return true;
        return false;
    }

    public void changePlayer() {
        if (active != null) {
            if (active.equals(plOne))
                active = plTwo;
            else
                active = plOne;
        }
    }

    public void destroySnowUnit(SnowUnitSprite s) {
        if (plOneCon.contains(s))
            plOneCon.removeSprite(s);
        else if (plTwoCon.contains(s))
            plTwoCon.removeSprite(s);
        s.die();
    }

    @Override
    public void collided(Sprite a, Sprite b) {
        SnowUnitSprite sa = (SnowUnitSprite) a;
        SnowUnitSprite sb = (SnowUnitSprite) b;
        sa.die();
        sb.die();
        Log.d("Collision!", "Model, Collision" + a.getClass().getName() + " collided with " + b.getClass().getName());
    }
}
