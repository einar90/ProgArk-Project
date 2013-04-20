package com.example.arcade.tankwars;

import android.content.res.Resources;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import sheep.graphics.Image;

/**
 * User: Dzenan
 * Date: 20.04.13
 * Time: 20:48
 */
public class ThermiteExplosion extends Explosion {

    private static final Resources resources = Game.getInstance().getResources();
    private static final Image spriteImage = GraphicsHelper.getScaledImage(resources, R.drawable.thermite);

    private static final int duration = 3; // In Seconds


    ThermiteExplosion(){
        super(duration ,spriteImage);
    }
}
