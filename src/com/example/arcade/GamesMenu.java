package com.example.arcade;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import com.example.arcade.battleship.BattleshipUserInterface;
import com.example.arcade.coldWarII.ColdWarGame;
import com.example.arcade.tankwars.TankWarsUserInterface;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 11.03.13
 * Time: 13:41
 */
public class GamesMenu extends State {

	private static Resources resources;
	private static Point displaySize;
	private Sprite tankWarsButton;
	private Sprite wormsButton;
	private Sprite coldWarIIButton;
	private Sprite battleShipButton;
	private float buttonHeight;
	private float buttonWidth;

	public GamesMenu(Point displaySize, Resources resources) {
		this.resources = resources;
		this.displaySize = displaySize;

		initGameButtons();


	}

	private void initGameButtons() {
		Image tankWarsImage = GraphicsHelper.getScaledImage(resources, R.drawable.tankwars_button);
		Image wormsImage = GraphicsHelper.getScaledImage(resources, R.drawable.worms_button);
		Image battleshipImage = GraphicsHelper.getScaledImage(resources, R.drawable.battleship_button);
		Image coldWarIiImage = GraphicsHelper.getScaledImage(resources, R.drawable.coldwarii_button);

		buttonHeight = tankWarsImage.getHeight();
		buttonWidth = tankWarsImage.getWidth();
		
		tankWarsButton = new Sprite(tankWarsImage);
		wormsButton = new Sprite(wormsImage);
		battleShipButton = new Sprite(battleshipImage);
		coldWarIIButton = new Sprite(coldWarIiImage);

		tankWarsButton.setPosition(displaySize.x / 2, displaySize.y / 5);
		wormsButton.setPosition(displaySize.x / 2, displaySize.y / 5 * 2);
		battleShipButton.setPosition(displaySize.x / 2, displaySize.y / 5 * 3);
		coldWarIIButton.setPosition(displaySize.x / 2, displaySize.y / 5 * 4);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		tankWarsButton.update(dt);
		wormsButton.update(dt);
		battleShipButton.update(dt);
		coldWarIIButton.update(dt);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(canvas != null){
			canvas.drawPaint(new Paint(Color.BLACK));
			tankWarsButton.draw(canvas);
			wormsButton.draw(canvas);
			coldWarIIButton.draw(canvas);
			battleShipButton.draw(canvas);
		}

	}

	@Override
	public boolean onTouchDown(MotionEvent event) {
		if (GraphicsHelper.isSpriteTouched(tankWarsButton, buttonWidth, buttonHeight, event)) {
			getGame().pushState(new TankWarsUserInterface(displaySize, resources));
			Log.d("Tapped", "Tank Wars button tapped.");
		} else if (GraphicsHelper.isSpriteTouched(wormsButton, buttonWidth, buttonHeight, event)) {
			Log.d("Tapped", "Worms button tapped.");
		} else if (GraphicsHelper.isSpriteTouched(coldWarIIButton, buttonWidth, buttonHeight, event)) {
			getGame().pushState(new ColdWarGame());
			Log.d("Tapped", "Cold War II button tapped.");
		} else if (GraphicsHelper.isSpriteTouched(battleShipButton, buttonWidth, buttonHeight, event)) {
			getGame().pushState(new BattleshipUserInterface(displaySize));
			Log.d("Tapped", "Battleship button tapped.");
		}
		return true;
	}
}
