package com.mygame.spacecatch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.spacecatch.screens.GameOverScreen;
import com.mygame.spacecatch.screens.MainGameScreen;
import com.mygame.spacecatch.screens.MainMenuScreen;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SpaceCatch extends Game {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
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
