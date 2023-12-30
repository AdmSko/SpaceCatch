package com.mygame.spacecatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.spacecatch.SpaceCatch;
import com.mygame.spacecatch.entities.*;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {
    private float SPEED = 500;
    Music mainMusic,playerHit,shotBullet,badAnsw,goodAnsw;
    private float timeSeconds = 0f;
    private float questiontimer = 0f;
    private static final float period = 1f;
    private static final float periodQuestion = 10f, ACCELERATION = 50f, MAXVELOCITY=50f;
    public Vector2 velocity, acceleration;
    private static final int MENUINGAMEWITDH = 100, MENUINGAMEHEIGHT = 40, MAX_HEIGHT = 525;
    private boolean isQuestionPresent = true, isPicked;
    Texture img, menuInGameImg, background, infoBackground, logo;
    float x,y,menuInGameX,menuInGameY,periodBullet=5f,timeBullet, periodBullet1=10f,timeBullet1, periodBullet2=6f, timeBullet2;
    Sprite sprite,backgroundSprite;
    SpaceCatch game;
    ArrayList<EnemyBullet> enemyBullets;
    ArrayList<EnemyBullet1> enemyBullets1;
    ArrayList<EnemyBullet2> enemyBullets2;
    QuestionContent questionContent;
    Question question;
    Health hpCheck;
    CollisionRect playerRect;
    int health; //0 = dead, 6 = full health
    int score = 0,gameTime=1;
    int goodOrBadAnswer;
    BitmapFont scoreFont;

    public MainGameScreen(SpaceCatch game){
        this.game = game;
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("musicgame.mp3"));
        mainMusic.isLooping();
        mainMusic.play();
        playerHit = Gdx.audio.newMusic(Gdx.files.internal("explosion.mp3"));
        shotBullet = Gdx.audio.newMusic(Gdx.files.internal("bulletblow.mp3"));
        badAnsw = Gdx.audio.newMusic(Gdx.files.internal("lossPoints.mp3"));
        goodAnsw = Gdx.audio.newMusic(Gdx.files.internal("pickpointgoodansw.mp3"));
        background = new Texture("background.png");
        infoBackground = new Texture("infobackground.png");
        logo = new Texture("logo.png");
        backgroundSprite = new Sprite(background);
        img = new Texture("ship.png");
        menuInGameImg = new Texture("menuInGame.png");
        menuInGameX = menuInGameImg.getWidth()-30;
        menuInGameY = menuInGameImg.getHeight()-5;
        sprite = new Sprite(img);
        x = (float)Gdx.graphics.getWidth()/2;
        y = sprite.getHeight()*sprite.getScaleY()*2;
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);
        enemyBullets = new ArrayList<>();
        enemyBullets1 = new ArrayList<>();
        enemyBullets2 = new ArrayList<>();
        question = new Question();
        playerRect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
        hpCheck = new Health();
        scoreFont = new BitmapFont(Gdx.files.internal("scorefont.fnt"));
        health =6;
        questionContent = new QuestionContent(x,y, (int)sprite.getWidth(), (int)sprite.getHeight(), menuInGameX, menuInGameY);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
            Random rand = new Random();
            //timer for score

            timeSeconds += Gdx.graphics.getDeltaTime();
            if (timeSeconds > period) {
                score += 1;                                 //updating score for time in game
                gameTime +=1;
                timeSeconds -= period;                      //+1 score per second
            }
            //creating enemy bullets
            timeBullet += Gdx.graphics.getDeltaTime();
            if(timeBullet > periodBullet){
                timeBullet -= periodBullet;
                shotBullet.play();
                //tu dodac rand period dla kazdego bulleta
                enemyBullets.add(new EnemyBullet(gameTime));
            }
            timeBullet1 += Gdx.graphics.getDeltaTime();
            if(timeBullet1 > periodBullet1){
                timeBullet1 -= periodBullet1;
                shotBullet.play();
                enemyBullets1.add(new EnemyBullet1(x,y,gameTime));
            }
            timeBullet2 += Gdx.graphics.getDeltaTime();
            if(timeBullet2 > periodBullet2){
                timeBullet2 -= periodBullet2;
                shotBullet.play();
                enemyBullets2.add(new EnemyBullet2(gameTime));
            }
            //Updating periods per time in game - making it harder, more spawning
            if(gameTime % 50 == 0){
                periodBullet -= 0.5f;
                periodBullet1 -= 0.3f;
                periodBullet2 -=0.8f;
                if(periodBullet < 1f || periodBullet1 < 1f || periodBullet2 < 0.8f){
                    periodBullet = 1f;
                    periodBullet1 = 1.5f;
                    periodBullet2 =0.8f;
                }
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
            //update menuInGameButton // ewentualnie zmienic zeby bylo tylko wejscie do ingamemenu, mozliwosc powrotu do rozgrywki
            if(Gdx.input.getX() < menuInGameX + MENUINGAMEWITDH &&
                Gdx.input.getX() > menuInGameX &&
                SpaceCatch.HEIGHT - Gdx.input.getY() < menuInGameY + MENUINGAMEHEIGHT &&
                SpaceCatch.HEIGHT - Gdx.input.getY() > menuInGameY){
                if(Gdx.input.isTouched()){
                    this.dispose();
                    game.setScreen(new MainMenuScreen(game)); //<--- change (bool?) ismenu, eveything i case switch
                }
            }
            //update enemy bullets
            //check if out of stream -> yes -> delete
            ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<EnemyBullet>();
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.Update(delta);
                if (enemyBullet.remove)
                    enemyBulletsToRemove.add(enemyBullet);
            }
            enemyBullets.removeAll(enemyBulletsToRemove);
            ArrayList<EnemyBullet1> enemyBullets1ToRemove = new ArrayList<>();
            for(EnemyBullet1 enemyBullet1 : enemyBullets1){
                enemyBullet1.Update(delta,x,y);
                if(enemyBullet1.remove)
                    enemyBullets1ToRemove.add(enemyBullet1);
            }
            enemyBullets1.removeAll(enemyBullets1ToRemove);
            ArrayList<EnemyBullet2> enemyBullets2ToRemove = new ArrayList<>();
            for(EnemyBullet2 enemyBullet2 : enemyBullets2){
                enemyBullet2.Update(delta);
                if(enemyBullet2.remove)
                    enemyBullets2ToRemove.add(enemyBullet2);
            }
            enemyBullets2.removeAll(enemyBullets2ToRemove);
            //player movement
            handleInput();
            move();

            //check for collision after updates/moves
            playerRect.move(x, y);

            //update question collision
            question.Update(delta);
            if (question.getCollisionRect().collidesWith(playerRect)) {
                isPicked = true;
                goodAnsw.play();
                //creating object of possible answers and question
                questionContent = new QuestionContent(x, y, (int)sprite.getWidth(), (int)sprite.getHeight(), menuInGameX, menuInGameY);
                question.x = -100;
            }
            //update questionContent collision
            //if question is picked
            if(isPicked) {
                //checking question type to pick proper collision check
                questionContent.Update(delta);
                goodOrBadAnswer = questionContent.getCollisionRect().collidesWithAnswer(playerRect);
                //if player collides with answer
                if (goodOrBadAnswer != 0) {
                    isPicked = false;
                    goodAnsw.play();
                    isQuestionPresent = false;                  // start timer for another question event
                    if(goodOrBadAnswer == 1) {
                        goodAnsw.play();
                        score += 35;
                    }
                    else {
                        badAnsw.play();
                        if((score - 50) < 0) score = 0;
                        else score -= 50;
                    }
                    goodOrBadAnswer = 0;
                }
            }
            //remove bullet if player got hit - checking collision
            for (EnemyBullet bullet : enemyBullets) {
                if (bullet.getCollisionRect().collidesWith(playerRect)) {
                    enemyBulletsToRemove.add(bullet);
                    playerHit.play();
                    health -= 1;
                }
            }
             for (EnemyBullet1 bullet1 : enemyBullets1) {
                  if (bullet1.getCollisionRect().collidesWith(playerRect)) {
                     enemyBullets1ToRemove.add(bullet1);
                      playerHit.play();
                        health -= 1;
                  }
            }
            for (EnemyBullet2 bullet2 : enemyBullets2) {
                if (bullet2.getCollisionRect().collidesWith(playerRect)) {
                    enemyBullets2ToRemove.add(bullet2);
                    playerHit.play();
                    health -= 1;
                }
            }
            //update health bar
            hpCheck.Update(delta, health);
            //game over - screen change
            if (health <= 0) {
                System.out.println(health);
                this.dispose();
                game.setScreen(new GameOverScreen(game, score));
                return;
            }
            enemyBullets.removeAll(enemyBulletsToRemove);
            enemyBullets1.removeAll(enemyBullets1ToRemove);
            enemyBullets2.removeAll(enemyBullets2ToRemove);
            //drawing
            ScreenUtils.clear(Color.DARK_GRAY);
            sprite.setScale(2);
            //start of batch - drawing
            game.batch.begin();
            backgroundSprite.draw(game.batch);
            game.batch.draw(menuInGameImg,menuInGameX,menuInGameY,MENUINGAMEWITDH,MENUINGAMEHEIGHT);
            game.batch.draw(infoBackground,0,MAX_HEIGHT,Gdx.graphics.getWidth(),Gdx.graphics.getHeight() - MAX_HEIGHT);
            scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 15);
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.render(game.batch);
            }
            for (EnemyBullet1 enemyBullet1 : enemyBullets1) {
                 enemyBullet1.render(game.batch);
            }
            for (EnemyBullet2 enemyBullet2 : enemyBullets2) {
                 enemyBullet2.render(game.batch);
            }
            question.render(game.batch);
            if (isPicked) {
                questionContent.render(game.batch);
            }else game.batch.draw(logo,(int)(Gdx.graphics.getWidth()/2) - 110, Gdx.graphics.getHeight() -50, 220, 25);
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
        mainMusic.dispose();
        Gdx.input.setInputProcessor(null);
    }
    private void handleInput(){
        acceleration.set(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) acceleration.x = -1;
        if(Gdx.input.isKeyPressed(Input.Keys.D)) acceleration.x = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) acceleration.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) acceleration.y = -1;
    }
    private void move(){
        // Adjust velocity based on acceleration
        velocity.add(acceleration.x, acceleration.y);
        // Damping to simulate drifting
        velocity.scl(0.9f);
        // Update sprite position based on velocity
        x += velocity.x;
        y += velocity.y;
        // Ensure the sprite stays within the screen bounds
        float minX = 15;
        float minY = sprite.getHeight()-6;
        float maxX = 580;
        float maxY = MAX_HEIGHT-sprite.getHeight()-10;
        x = Math.max(minX, Math.min(x, maxX));
        y = Math.max(minY, Math.min(y, maxY));
        if(x<=minX || x>= maxX) velocity.x = -velocity.x *2;
        if (y <= minY || y >= maxY) velocity.y = -velocity.y *2;
        //updating position
        sprite.setPosition(x, y);
        // Rotate sprite based on velocity
        float targetRotation = MathUtils.atan2(velocity.y, velocity.x) * MathUtils.radiansToDegrees;
        float rotationSpeed = 3.0f;
        float rotation = MathUtils.lerp(sprite.getRotation(), targetRotation, Gdx.graphics.getDeltaTime() * rotationSpeed);
        sprite.setRotation(rotation);

    }
}
