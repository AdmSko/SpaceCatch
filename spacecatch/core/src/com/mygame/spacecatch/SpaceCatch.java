package com.mygame.spacecatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.screens.MainMenuScreen;

/**
 * Class that implements Game and is used as a first class after starting the game, sets creen to main menu screen
 * Uses default constructor
 */
public class SpaceCatch extends Game {
	/**	Width of the screen	*/
	public static final int WIDTH = 600;
	/**	Height of the screen */
	public static final int HEIGHT = 600;
	/**	batch of screens that is used to draw everything at the same time */
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();


	}
	
	@Override
	public void dispose () {

	}
}
