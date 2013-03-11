package com.example.arcade.tankwars;

import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class TankShell extends Projectile {
    private static Image tankshellImage;
    private int explosionRadius;

    public TankShell() {
        super(tankshellImage);
    }
}
