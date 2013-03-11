package com.example.arcade;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created with IntelliJ IDEA.
 * User: Einar
 * Date: 11.03.13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class Game extends sheep.game.Game {
    /**
     * Creates a new Game. You should only create one Game for each
     * project.
     *
     * @param context The Context for this applications.
     * @param attrs   The AttributeSet (may be null).
     */
    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
