package com.example.arcade.tankwars.explosions;

import android.content.res.Resources;
import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;
import com.example.arcade.R;
import sheep.graphics.Image;

/**
 * User: Dzenan
 * Date: 20.04.13
 * Time: 20:44
 */
public class NukeExplosion extends Explosion {

    private static final Resources resources = Game.getInstance().getResources();
    private static final Image spriteImage = GraphicsHelper.getScaledImage(resources, R.drawable.nuke);

    private static final int duration = 1; // In Seconds


    public NukeExplosion() {
        super(duration, spriteImage);
    }
}
