package com.example.arcade;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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

    private static double[] scalingFactor = new double[]{
            displaySize.x / 1280.0,
            displaySize.y / 800.0
    };


    /**
     * Method that returns an Image scaled for the display size, relative to a 1280x800 standard resolution
     * (which ALL images should initially be created for)
     *
     * @param filename The name of the file (placed in the root of the 'assets' directory to scale.
     * @return Returns the scaled image if successful, otherwise null.
     */
    public static Image getScaledImage(String filename) {
        AssetManager assetManager = Game.getInstance().getResources().getAssets();
        InputStream imageIn;
        Bitmap bitmapImage;
        try {
            imageIn = assetManager.open(filename, AssetManager.ACCESS_BUFFER);
            bitmapImage = BitmapFactory.decodeStream(imageIn);
            Point scaledImageSize = new Point((int) ((double) (bitmapImage.getWidth() * scalingFactor[0])), (int) ((double) bitmapImage.getHeight() * scalingFactor[1]));
            bitmapImage = Bitmap.createScaledBitmap(bitmapImage, scaledImageSize.x, scaledImageSize.y, true);
            Drawable drawableImage = new BitmapDrawable(bitmapImage);

            Log.d("Value", "Scaled image size in Scaling: " + drawableImage.getIntrinsicWidth() + ", " + drawableImage.getIntrinsicHeight() + "\n\n");

            return new Image(drawableImage);
        } catch (IOException e) {
            Log.e("Error", "Could not open image " + filename);
        }
        return null;
    }
}
