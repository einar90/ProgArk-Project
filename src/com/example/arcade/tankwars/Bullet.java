package com.example.arcade.tankwars;

import sheep.graphics.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Dzenan
 * Date: 11.03.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public class Bullet extends Projectile {
    private static Image bulletImage;

    public Bullet() {
        super(bulletImage);
    }
}