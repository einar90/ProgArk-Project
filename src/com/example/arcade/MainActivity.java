package com.example.arcade;

import java.util.EmptyStackException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Game game;
	private static Context context;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        game = new Game(this, null);

        game.pushState(new MainMenu(game.getResources()));

        setContentView(game);

        context = this;
    }
    
    @Override
    public void onBackPressed() {
    	try{
    		game.popState();
    	}catch(EmptyStackException e){
    		super.onBackPressed();
    	}
    }
    
    public static void toast(String text){
    	Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
