package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.Random;

/**
 * Class contains information about bullet like speed, sprite and its behaviour
 */
public class EnemyBullet1 {
    /** bullet speed */
    float speed =200;
    /** bullet texture*/
    private static Texture texture;
    /** bullet sprite */
    Sprite sprite;
    /** X, Y bullet position */
    float x,y;
    /** X, Y player position */
    float plX, plY;
    /** timers for how long projectile should be homing towards player */
    float time=0f, period;
    /** X-axis last direction of a bullet*/
    private float lastDirectionX;
    /** Y-axis last direction of a bullet*/
    private float lastDirectionY;
    /** maximum bullet speed */
    int upperSpeed = 200;
    /** works as an initial side of the screen for a new bullet  */
    int side;
    /** in-game time*/
    int gameTime;
    /** minimum bullet speed */
    int originSpeed=50;
    /** changes state if bullet leaves a screen, ready to be removed*/
    public boolean remove = false;
    /** Object of CollisionRect class to check collision of the object with a player */
    CollisionRect rect;
    /**
     * Constructor that sets up objects and initial location
     * @param plX Player X-axis position
     * @param plY Player Y-axis position
     * @param gameTime Variable that contains time spent in game
     */
    public EnemyBullet1(float plX,float plY,int gameTime){
        this.gameTime = gameTime;
        this.plX = plX;
        this.plY = plY;
        texture = new Texture("bullet1.png");
        sprite = new Sprite(texture);
        Random rand = new Random();
        if(gameTime % 30 == 0){
            originSpeed += 20;
            upperSpeed += 20;
        }
        if(upperSpeed >=550) upperSpeed = 480;
        if(originSpeed >=150)originSpeed = 150;
        speed = rand.nextFloat(originSpeed, upperSpeed);
        period = rand.nextFloat(5f,10f);
        side = rand.nextInt(4);
        //spawn position
        switch (side) {
            case 0:
                x = 0;
                y = Gdx.graphics.getHeight() * rand.nextFloat();
                break;
            case 1:
                x = Gdx.graphics.getWidth();
                y = Gdx.graphics.getHeight() * rand.nextFloat();
                break;
            case 2:
                x = Gdx.graphics.getWidth() * rand.nextFloat();
                y = 0;
                break;
            case 3:
                x = Gdx.graphics.getWidth() * rand.nextFloat();
                y = Gdx.graphics.getHeight();
                break;
            default:
                break;
        }
    }
    /**
     * Method updates location and speed of the object
     * @param deltaTime Time between the start of the previous and the start of the current call
     * @param plX X-axis position of the player
     * @param plY Y-axis position of the player
     */
    public void Update(float deltaTime,float plX, float plY){
        this.plX=plX;
        this.plY=plY;
        //movement
        if(time < period){
            float angle = MathUtils.atan2(plY - y, plX - x);
            lastDirectionX = MathUtils.cos(angle);
            lastDirectionY = MathUtils.sin(angle);
            x += lastDirectionX * deltaTime * speed;
            y += lastDirectionY * deltaTime * speed;
            time += deltaTime;
        }
        else{
            x += lastDirectionX * deltaTime * speed;
            y += lastDirectionY * deltaTime * speed;
        }
        //screen borders if out of screen, delete
        if(x > Gdx.graphics.getWidth()
                || x < 0
                || y > Gdx.graphics.getHeight()
                || y < 0) remove = true;
        this.rect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
    }
    /**
     * Method renders object via setting position and scale of the object
     * @param batch Object of a SpriteBatch class, contains a batch of sprites to draw
     */
    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
    /**
     * Returns collision with object
     * @return object of CollisionRect, checking collision with the object
     */
    public CollisionRect getCollisionRect(){
        return rect;
    }
}

