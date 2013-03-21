package com.example.arcade;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewConfiguration;
import sheep.graphics.Image;

import com.example.arcade.utilities.Constants;

/**
 * Created by:
 * User: Einar
 * Date: 15.03.13
 * Time: 11:19
 */
public class GraphicsHelper {
    private static DisplayMetrics displayMetrics = Game.getInstance().getResources().getDisplayMetrics();
    private static Point displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);

    private static float[] scalingFactor = new float[]{
    	displaySize.x / 1280.0f,
        getRealHeight() / 800.0f
    };

    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
    public static float getRealHeight(){
    	float realHeight = displaySize.y;
    	//If the API level is below 14 it should not have software navigation bar
    	if(android.os.Build.VERSION.SDK_INT >= 14){
	    	boolean hasSoftNavBar = !ViewConfiguration.get(Game.getInstance().getContext()).hasPermanentMenuKey();
	    	if(hasSoftNavBar){
	    		float density = displayMetrics.density;
	    		float navBarHeight = 48*density;
	    		realHeight = realHeight - navBarHeight;
	    		Constants.WINDOW_HEIGHT = (int) realHeight;
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


}
