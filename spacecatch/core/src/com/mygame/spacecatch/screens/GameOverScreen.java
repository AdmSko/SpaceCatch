package com.mygame.spacecatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.spacecatch.SpaceCatch;

/**
 * This class contains visualization of the game over screen, buttons and their behaviour, score and high score display, implements Screen.
 *
 */
public class GameOverScreen implements Screen {
    /** Game Over img width */
    private static final int GAMEOVER_WIDTH = 400;
    /** Game Over img height */
    private static final int GAMEOVER_HEIGHT = 60;
    /** button width */
    private static final int BUTTON_WIDTH = 200;
    /** button height */
    private static final int BUTTON_HEIGHT = 50;
    /** instance of the game */
    SpaceCatch game;
    /** game over button texture  */
    Texture gameOverImg;
    /** background sprite  */
    Sprite backgroundSprite;
    /** menu button texture  */
    Texture menuButton;
    /** exit button texture  */
    Texture exitButton;
    /** exit button texture when cursor hovered over  */
    Texture exitButtonActive;
    /** menu button texture when cursor hovered over  */
    Texture menuButtonActive;
    /** font of score   */
    BitmapFont scoreFont;
    /** score value */
    int score;
    /** high score value */
    int highScore;
    /** menu music */
    Music menuMusic;

    /**
     * This method is a constructor of game over screen which takes care of creating new objects, variables and loading file.
     * @param game Instance of the game.
     * @param score Score of the player
     */
    public GameOverScreen(SpaceCatch game, int score){
        this.game = game;
        this.score = score;
        //Fonts & Textures
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("musicmenu.mp3"));
        menuMusic.isLooping();
        menuMusic.play();
        backgroundSprite = new Sprite(new Texture("background.png"));
        gameOverImg = new Texture("gameover.png");
        scoreFont = new BitmapFont(Gdx.files.internal("scorefont.fnt"));
        menuButton = new Texture("menu.png");
        exitButton = new Texture("exitoff.png");
        exitButtonActive = new Texture("exiton.png");
        menuButtonActive = new Texture("menuon.png");

        //setting file highscore
        Preferences pref = Gdx.app.getPreferences("spacecatch");
        this.highScore = pref.getInteger("highscore", 0);
        //check and save new
        if (score > highScore){
            pref.putInteger("highscore", score).flush();
        }
    }

    @Override
    public void show() {

    }

    /**
     * Method for rendering/drawing game over screen, textures, buttons and updating high score or score.
     *
     * @param delta Time between the start of the previous and the start of the current call
     */
    @Override
    public void render(float delta) {
        int menux = Gdx.graphics.getWidth()/4 -BUTTON_WIDTH/2;
        int exitx = Gdx.graphics.getWidth()/4*3 - BUTTON_WIDTH/2;
        int buttonY = BUTTON_HEIGHT *2;
        ScreenUtils.clear(Color.DARK_GRAY);
        scoreFont.getData().setScale(2);
        if(score >= highScore) highScore = score;
        GlyphLayout scoreL = new GlyphLayout(scoreFont,"Score:\n" +score,Color.WHITE,0 , Align.center,false);
        GlyphLayout hscoreL = new GlyphLayout(scoreFont,"highscore:\n" +highScore,Color.WHITE,0 , Align.center,false);

        //drawing
        game.batch.begin();
        backgroundSprite.draw(game.batch);
        //Game Over text
        game.batch.draw(gameOverImg,
                (int)(Gdx.graphics.getWidth() / 2) - (int)(GAMEOVER_WIDTH / 2) ,
                (int)(Gdx.graphics.getHeight() - GAMEOVER_HEIGHT*2),
                GAMEOVER_WIDTH,
                GAMEOVER_HEIGHT);

        //Drawing Score
        scoreFont.draw(game.batch, scoreL, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 180);
        scoreFont.draw(game.batch, hscoreL, Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() - scoreL.height - 200 );

        //Menu button check
        if(Gdx.input.getX() < menux + BUTTON_WIDTH &&
                Gdx.input.getX() > menux &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < buttonY + BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > buttonY){
                game.batch.draw(
                    menuButtonActive,
                    menux,
                    buttonY,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
                if(Gdx.input.isTouched()) {
                    this.dispose();
                    game.batch.end();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
        }
        else
            game.batch.draw(menuButton, menux, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);

        //Exit button check
        if(Gdx.input.getX() < exitx + BUTTON_WIDTH &&
                Gdx.input.getX() > exitx &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < buttonY + BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > buttonY){
            game.batch.draw(
                    exitButtonActive,
                    exitx,
                    buttonY,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
            if(Gdx.input.isTouched())Gdx.app.exit();
        }
        else
            game.batch.draw(exitButton, exitx, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);

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
