package com.mygame.spacecatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.spacecatch.SpaceCatch;

/**
 * This class contains visualization of the main menu screen, buttons and their behaviour, implements Screen.
 *
 */
public class MainMenuScreen implements Screen {
    /** Exit button width */
    public static final int EXIT_BUTTON_WIDTH = 250;
    /** Exit button height */
    public static final int EXIT_BUTTON_HEIGHT = 90;
    /** Play button width */
    public static final int PLAY_BUTTON_WIDTH = 250;
    /** Play button height */
    public static final int PLAY_BUTTON_HEIGHT = 90;
    /** Exit button Y-axis position */
    public static final int EXIT_BUTTON_Y = 80;
    /** Play button Y-axis position */
    public static final int PLAY_BUTTON_Y = 230;
    /** Logo width and height */
    public static final int LOGO_WIDTH = 420, LOGO_HEIGHT = 90;
    /** background img */
    private Sprite background;
    /** menu music */
    Music menuMusic;
    /** play button img when cursor hovered over */
    Texture playButtonActive;
    /** play button img */
    Texture playButtonInactive;
    /** exit button img when cursor hovered over */
    Texture exitButtonActive;
    /** exit button img */
    Texture exitButtonInactive;
    /** logo texture */
    Texture logo;
    /** Instance of the game */
    SpaceCatch game;
    /**
     * This method is a constructor of the main menu screen which takes care of creating new objects and variables.
     *
     * @param game Instance of the game.
     */
    public MainMenuScreen(SpaceCatch game){
        this.game = game;
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("musicmenu.mp3"));
        menuMusic.isLooping();
        menuMusic.play();
        background = new Sprite( new Texture("background.png"));
        logo = new Texture("logo.png");
        playButtonActive = new Texture("playon.png");
        playButtonInactive = new Texture("playoff.png");
        exitButtonActive = new Texture("exiton.png");
        exitButtonInactive = new Texture("exitoff.png");

    }
    @Override
    public void show() {

    }
    /**
     * Method for rendering/drawing main menu screen, textures, buttons.
     *
     * @param delta Time between the start of the previous and the start of the current call
     */
    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0, 1);
        ScreenUtils.clear(Color.DARK_GRAY);
        //drawing
        game.batch.begin();
        background.draw(game.batch);
        game.batch.draw(logo,SpaceCatch.WIDTH/2 - LOGO_WIDTH/2, SpaceCatch.HEIGHT - LOGO_HEIGHT*2.5f, LOGO_WIDTH,LOGO_HEIGHT );
        //rendering exit button
        int exitx = SpaceCatch.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        int playx = SpaceCatch.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() < exitx + EXIT_BUTTON_WIDTH
                && Gdx.input.getX() > exitx
                && SpaceCatch.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && SpaceCatch.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, exitx, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched())Gdx.app.exit();
        }
        else {
            game.batch.draw(exitButtonInactive, exitx, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        //rendering play button
        if(Gdx.input.getX() < playx + PLAY_BUTTON_WIDTH &&
                Gdx.input.getX() > playx &&
                SpaceCatch.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                SpaceCatch.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {
            game.batch.draw(playButtonActive, playx, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        }
        else {
            game.batch.draw(playButtonInactive, playx, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
            game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
    /**
     * Method for disposing used objects, screen.
     */
    @Override
    public void dispose() {
        menuMusic.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
