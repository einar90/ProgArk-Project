package com.example.arcade.tankwars;

import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class ThermiteShell extends Projectile {
    private static Image thermiteShellImage;
    private int burnDuration;

    public ThermiteShell() {
        super(thermiteShellImage);
    }
}
