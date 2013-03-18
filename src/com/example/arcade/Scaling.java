package com.example.arcade;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
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
     * @param original A drawable object from res/drawable-nodpi to scale
     * @return Returns the scaled image if successful, otherwise null.
     */
    public static Image getScaledImage(Drawable original) {
        try {
            Drawable scaledImage = new ScaleDrawable(original, Gravity.CENTER, scalingFactor[0], scalingFactor[1]);
            Log.d("Value", "Scaled image size in Scaling: " + scaledImage.getIntrinsicWidth() + ", " + scaledImage.getIntrinsicHeight() + "\n\n");
            return new Image(scaledImage);
        } catch (Exception e) {
            Log.e("Error", "Could not open image " + original.toString());
        }
        return null;
    }
}
