package com.example.arcade.tankwars;

import android.graphics.Canvas;
import sheep.game.Sprite;
import sheep.graphics.Image;

import java.util.Calendar;

/**
 * User: Dzenan
 * Date: 20.04.13
 * Time: 20:31
 */
public abstract class Explosion extends Sprite{
    private Calendar creationTime;
    private int duration;

    Explosion(){

    }

    Explosion(int duration, Image spriteImage){
        super(spriteImage);
        creationTime = Calendar.getInstance();
        this.duration = duration;

    }


    public void Update(long dt){
       this.update(dt);

       if((Calendar.getInstance().getTimeInMillis() - creationTime.getTimeInMillis())/1000 > duration){
          this.die();
       }
    }


    public void Draw(Canvas canvas){
        super.draw(canvas);
    }

}
