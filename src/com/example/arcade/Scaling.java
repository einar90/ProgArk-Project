package com.example.arcade;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
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

    private static Point scalingFactor = new Point(
            displaySize.x / 1280,
            displaySize.y / 800
    );

    public Image getScaledImage(String filename) {
        AssetManager assetManager = Game.getInstance().getResources().getAssets();
        InputStream imageIn;
        Bitmap bitmapImage;
        try {
            imageIn = assetManager.open(filename, AssetManager.ACCESS_BUFFER);
            bitmapImage = BitmapFactory.decodeStream(imageIn);
        } catch (IOException e) {
            Log.e("Error", "Could not open image " + filename);
        }

        Point originalSize = new Point(bitmapImage.getWidth(), bitmapImage.getHeight());

        bitmapImage = Bitmap.createScaledBitmap(bitmapImage, originalSize.x * scalingFactor.x, originalSize.y * scalingFactor.y);

    }
}
