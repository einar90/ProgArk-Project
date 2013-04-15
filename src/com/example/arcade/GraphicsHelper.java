package com.example.arcade;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.example.arcade.utilities.Constants;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 15.03.13
 * Time: 11:19
 */
public class GraphicsHelper {

    private static float[] scalingFactor = new float[]{
            Constants.WINDOW_WIDTH / 1280.0f,
            Constants.WINDOW_HEIGHT / 800.0f
    };

    public static Point getDisplaySize() {
        int height = Game.getInstance().getResources().getDisplayMetrics().heightPixels;
        int width = Game.getInstance().getResources().getDisplayMetrics().widthPixels;
        return new Point(width, height);
    }


    /**
     * Calculating the pixel height of devices with a software navigation bar.
     * Physical height minus navbar height.
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static float getRealHeight() {
        float realHeight = Game.getInstance().getResources().getDisplayMetrics().heightPixels;

        //If the API level is below 14 it should not have software navigation bar
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            boolean hasSoftNavBar = !ViewConfiguration.get(Game.getInstance().getContext()).hasPermanentMenuKey();
            if (hasSoftNavBar) {
                float density = Game.getInstance().getResources().getDisplayMetrics().density;
                float navBarHeight = 48 * density;
                realHeight = realHeight - navBarHeight;
            }
        }
        return realHeight;
    }


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
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, (int) (unscaledBitmap.getWidth() * scalingFactor[0]), (int) (unscaledBitmap.getHeight() * scalingFactor[1]), true);
            BitmapDrawable scaledDrawable = new BitmapDrawable(res, scaledBitmap);
            scaledDrawable.setTargetDensity(res.getDisplayMetrics().densityDpi);
            scaledImage = new Image(scaledDrawable);
        } catch (Exception e) {
            Log.e("MyClass", "Could not open image " + id, e);
        }

        return scaledImage;
    }


    /**
     * Method that returns an FLIPPED Image scaled for the display size, relative to a 1280x800 standard resolution
     * (which ALL images should initially be created for)
     *
     * @param res The Resources for the app
     * @param id  The id of the image to scale. id = R.drawable.imagename
     * @return Returns the flipped and scaled image if successful, otherwise null.
     */
    public static Image getFlippedScaledImage(Resources res, int id) {
        Image scaledImage = null;
        try {
            // Getting the bitmap
            Bitmap unscaledBitmap = BitmapFactory.decodeResource(res, id);

            // Scaling the bitmap
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(unscaledBitmap, (int) (unscaledBitmap.getWidth() * scalingFactor[0]), (int) (unscaledBitmap.getHeight() * scalingFactor[1]), true);

            //Rotating the bitmap
            Matrix m = new Matrix();
            m.preScale(-1, 1);
            Bitmap scaledFlippedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), m, false);

            // Creating the end-image via BitmapDrawable
            BitmapDrawable scaledFlippedDrawable = new BitmapDrawable(res, scaledFlippedBitmap);
            scaledFlippedDrawable.setTargetDensity(res.getDisplayMetrics().densityDpi);
            scaledImage = new Image(scaledFlippedDrawable);
        } catch (Exception e) {
            Log.e("MyClass", "Could not open image " + id, e);
        }

        return scaledImage;
    }

    public static boolean isSpriteTouched(Sprite s, float spriteWidth, float spriteHeight, MotionEvent event) {
        float minX = s.getX() - spriteWidth / 2;
        float maxX = s.getX() + spriteWidth / 2;
        float minY = s.getY() - spriteHeight / 2;
        float maxY = s.getY() + spriteHeight / 2;
        if (event.getX() > minX && event.getX() < maxX) {
            if (event.getY() > minY && event.getY() < maxY) {
                return true;
            }
        }
        return false;
    }

}
