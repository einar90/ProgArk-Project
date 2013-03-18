package com.example.arcade;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import sheep.graphics.Image;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by:
 * User: Einar
 * Date: 15.03.13
 * Time: 11:19
 */
public class Scaling {
    private static DisplayMetrics displayMetrics = Game.getInstance().getResources().getDisplayMetrics();
    private static Point displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);

    private static float[] scalingFactor = new float[]{
            displaySize.x / 1280.0f,
            displaySize.y / 800.0f
    };


    /**
     * Method that returns an Image scaled for the display size, relative to a 1280x800 standard resolution
     * (which ALL images should initially be created for)
     *
     * @param res The Resources for the app
     * @param id  The id of the image to scale. id = R.drawable.imagename
     * @return Returns the scaled image if successful, otherwise null.
     */
    public static Image getScaledImage(Resources res, int id) {
        Image scaledImage = null;
        try {
            Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, id);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, (int) (displaySize.x * scalingFactor[0]), (int) (displaySize.y * scalingFactor[1]), true);
            BitmapDrawable scaledDrawable = new BitmapDrawable(scaledBitmap);
            scaledImage = new Image(scaledDrawable);
        } catch (Exception e) {
            Log.e("MyClass", "Could not open image " + id, e);
        }

//TODO: calculate width and height to fill or fit screen

        return scaledImage;
    }


}
