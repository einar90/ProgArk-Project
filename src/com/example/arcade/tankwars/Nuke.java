package com.example.arcade.tankwars;

import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class Nuke extends Projectile {
    private static Image nukeImage;
    private int explosionRadius;

    public Nuke() {
        super(nukeImage);
    }
}
