package com.mygame.spacecatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.spacecatch.SpaceCatch;
import com.mygame.spacecatch.entities.EnemyBullet;
import com.mygame.spacecatch.entities.Health;
import com.mygame.spacecatch.entities.Question;
import com.mygame.spacecatch.entities.QuestionContent;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {
    private float SPEED = 500;
    private float timeSeconds = 0f;
    private float questiontimer = 0f;
    private float period = 1f;
    private float periodQuestion = 10f;
    private boolean isQuestionPresent = true,isPicked;
    Texture img;
    float x;
    float y;
    Sprite sprite;
    SpaceCatch game;
    ArrayList<EnemyBullet> enemyBullets;
    QuestionContent questionContent;
    Question question;
    Health hpCheck;
    CollisionRect playerRect;
    int health; //0 = dead, 6 = full health
    int score = 0;
    int goodOrBadAnswer;
    BitmapFont scoreFont;
    public MainGameScreen(SpaceCatch game){
        this.game = game;
        img = new Texture("ship.png");
        sprite = new Sprite(img);
        x = (float)Gdx.graphics.getWidth()/2;
        y = sprite.getHeight()*sprite.getScaleY()*2;
        enemyBullets = new ArrayList<>();
        question = new Question();
        playerRect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
        hpCheck = new Health();
        scoreFont = new BitmapFont(Gdx.files.internal("scorefont.fnt"));
        health =1;
        questionContent = new QuestionContent();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
            Random rand = new Random();
            //creating enemy bullets
            timeSeconds += Gdx.graphics.getDeltaTime();     //!!!!randomize spawn of bullets!!
            if (timeSeconds > period) {
                score += 1;                                 //updating score for time in game
                timeSeconds -= period;                      //+1 score per second
                enemyBullets.add(new EnemyBullet());        //spawn bullet every period (rn second
            }

            //creating question
            if (!isQuestionPresent) {
                questiontimer += Gdx.graphics.getDeltaTime();
                //maybe randomize question time
                if (questiontimer > periodQuestion) {
                    questiontimer -= periodQuestion;
                    question = new Question();
                    isQuestionPresent = true;
                }
            }

            //rendering score
            scoreFont.getData().setScale(2.5f);
            GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);

            //update enemy bullets
            //check if out of stream -> yes -> delete
            ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<EnemyBullet>();
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.Update(delta);
                if (enemyBullet.remove)
                    enemyBulletsToRemove.add(enemyBullet);
            }
            enemyBullets.removeAll(enemyBulletsToRemove);

            //player movement
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {         //ogarnac zeby przy rotacji zmienilo sie width z height
                y += SPEED * Gdx.graphics.getDeltaTime();       //efekt driftowania, moze animacja wtedy zeby dzialalo
                sprite.setRotation(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                y -= SPEED * Gdx.graphics.getDeltaTime();
                sprite.setRotation(180);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                x += SPEED * Gdx.graphics.getDeltaTime();
                sprite.setRotation(-90);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                x -= SPEED * Gdx.graphics.getDeltaTime();
                sprite.setRotation(90);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)
                    && Gdx.input.isKeyPressed(Input.Keys.W)) { //do naprawy bo zapierdala
                sprite.setRotation(45);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)
                    && Gdx.input.isKeyPressed(Input.Keys.W)) { //do naprawy bo zapierdala
                sprite.setRotation(-45);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)
                    && Gdx.input.isKeyPressed(Input.Keys.S)) { //do naprawy bo zapierdala
                sprite.setRotation(-135);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)
                    && Gdx.input.isKeyPressed(Input.Keys.S)) { //do naprawy bo zapierdala
                sprite.setRotation(135);
            }

            //screen borders
            if (x > 580) x = 580;
            if (x < 15) x = 15;
            if (y > 575) y = 575;
            if (y < 10) y = 10;

            //check for collision after updates/moves
            playerRect.move(x, y);

            //update question collision
            question.Update(delta);
            if (question.getCollisionRect().collidesWith(playerRect)) {
                isPicked = true;
                //pojawienie sie pytania i odpowiedzi na ekranie
                questionContent = new QuestionContent();


                question.x = -100; // xD
            }
            //update questionContent collision
            //if question is picked
            if(isPicked) {
                questionContent.Update(delta);
                //if player collides with answer
                goodOrBadAnswer = questionContent.getCollisionRect().collidesWithAnswer(playerRect);
                if (goodOrBadAnswer != 0) {      // <- jebac to
                    isPicked = false;
                    isQuestionPresent = false;                  // start timer for another question event
                    System.out.println("chuj");
                    if(goodOrBadAnswer == 1) score += 200;
                    else {
                        if(score - 20 < 0) score = 0;
                        else score -= 20;
                    }
                    goodOrBadAnswer = 0;
                }
            }
            //remove bullet if player got hit - checking collision
            for (EnemyBullet bullet : enemyBullets) {
                if (bullet.getCollisionRect().collidesWith(playerRect)) {
                    enemyBulletsToRemove.add(bullet);
                    health -= 1;

                    //game over - screen change
                    if (health <= 0) {
                        System.out.println(health);
                        this.dispose();
                        game.setScreen(new GameOverScreen(game, score));
                        return;
                    }
                }
            }
            hpCheck.Update(delta, health); //update health bar

            enemyBullets.removeAll(enemyBulletsToRemove);

            //drawing
            ScreenUtils.clear(Color.DARK_GRAY);
            sprite.setScale(2);
            sprite.setPosition(x, y);

            game.batch.begin();
            scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 15);
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.render(game.batch);
            }
            question.render(game.batch);
            if (isPicked) {
                questionContent.render(game.batch);
            }
            hpCheck.render(game.batch);
            sprite.draw(game.batch);
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

    @Override
    public void dispose() {

    }
}
