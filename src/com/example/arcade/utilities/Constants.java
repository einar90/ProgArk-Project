package com.example.arcade.utilities;

import com.example.arcade.Game;
import com.example.arcade.GraphicsHelper;

public class Constants {
    public static float WINDOW_HEIGHT = Game.getInstance().getResources().getDisplayMetrics().heightPixels;
    public static float WINDOW_WIDTH = Game.getInstance().getResources().getDisplayMetrics().widthPixels;
    
    public static void initConstants(){
    	WINDOW_HEIGHT = GraphicsHelper.getRealHeight();
    	WINDOW_WIDTH = Game.getInstance().getResources().getDisplayMetrics().widthPixels;
    }
}
