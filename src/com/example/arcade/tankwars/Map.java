package com.example.arcade.tankwars;

import android.graphics.Point;
import com.example.arcade.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 14:43
 */
public class Map extends Sprite {

    private Point displaySize;
    private static final Image mapGroundImage = new Image(R.drawable.map_bottombox);

    //Get scaling array


    public Map(Point displaySize) {
        super(mapGroundImage);
        this.displaySize = displaySize;
        //Set scaling
        //mapGroundSize is the scaled image size!, Not yet implemented, waiting for supportclass
        //for scaling
        this.setPosition(mapGroundImage.getWidth() / 2, displaySize.y);


    }

}
