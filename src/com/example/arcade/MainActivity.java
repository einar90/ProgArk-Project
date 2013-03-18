package com.example.arcade;

import java.util.EmptyStackException;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private Game game;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new Game(this, null);

        game.pushState(new MainMenu(game.getResources()));

        setContentView(game);

    }
    
    @Override
    public void onBackPressed() {
    	try{
    		game.popState();
    	}catch(EmptyStackException e){
    		super.onBackPressed();
    	}
    }
}
