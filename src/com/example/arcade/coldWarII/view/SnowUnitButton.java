package com.example.arcade.coldWarII.view;


import com.example.arcade.R;
import com.example.arcade.coldWarII.model.SnowUnitType;

import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.*;
import android.util.Log;
import sheep.game.Game;
import sheep.game.Sprite;
import sheep.graphics.Image;

public class SnowUnitButton extends Sprite{
	private Canvas canvas;
	private String label;
	private Bitmap bm;
	private int imageH,imageW,canvasX,canvasY;
	private float x,y;
	private Paint paint;
	private static Resources res = Game.getInstance().getResources();
	private SnowUnitType type;
	
	public SnowUnitButton( String text,Bitmap bm,Canvas c,int cX,int cY,Paint p,SnowUnitType t){
		super(new Image(new BitmapDrawable(res,bm)));
		this.bm = bm;
		imageH = bm.getHeight();
		imageW = bm.getWidth();
		canvas = c;
		label = text;
		paint = p;
		canvasX = cX;
		canvasY = cY;
		type = t;
	}
	public void changePaint(int color){
		paint.setColor(color);
		canvas.drawText(label, canvasX, canvasY, paint);
	}
	public String getLabel(){
		return label;
	}
	public int getImageHeight(){
		return imageH;
	}
	public int getImageWidth(){
		return imageW;
	}
	public Paint getPaint(){
		return paint;
	}
	public SnowUnitType getType(){
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(this == o)
			return false;
		if(o instanceof SnowUnitButton){
			if(this.type == ((SnowUnitButton)o).getType())
				return true;
			
		}
		
		return false;
	}
}
