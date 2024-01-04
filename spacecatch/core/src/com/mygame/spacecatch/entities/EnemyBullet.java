package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.Random;

/**
 * Class contains information about bullet like speed, sprite and its behaviour
 */
public class EnemyBullet {
    /** bullet speed */
    float speed;
    /** bullet texture  */
    private static Texture texture;
    /** bullet sprite */
    Sprite sprite;
    /** X, Y bullet position */
    float x,y;
    /** game time*/
    int gameTime;
    /** maximum bullet speed */
    int speedBound=100;
    /** minimum bullet speed */
    int originSpeed=10;
    /** maximum Y-axis value of position */
    private static final int UPPERBOUND = 520;

    /** left of right, initial bullet spawn border X value */
    boolean side;
    /** changes state if bullet leaves a screen, ready to be removed*/
    public boolean remove = false;
    /** Object of CollisionRect class to check collision of the object with a player */
    CollisionRect rect;

    /**
     * Constructor that sets up objects and initial location
     * @param gameTime Variable that contains time spent in game
     */

    public EnemyBullet(int gameTime){
        this.gameTime = gameTime;
        texture = new Texture("bullet.png");
        sprite = new Sprite(texture);
        Random rand = new Random();

        //generating random bullet position on Y axis
        if(!rand.nextBoolean()) {
            x = 0 ;
            side = true;
        }
        else {
            x = 600;
            side = false;
        }
        y = rand.nextFloat(UPPERBOUND);
        if(gameTime % 50 == 0){
            speedBound +=50;
            originSpeed +=10;
        }
        if(originSpeed >=200) originSpeed = 200;
        if(speedBound >= 450) speedBound = 450;
        speed = rand.nextFloat(originSpeed,speedBound);
    }

    /**
     * Method updates location and speed of the object
     * @param deltaTime Time between the start of the previous and the start of the current call
     */
    public void Update(float deltaTime){
        //side of the screen, movement direction
        if(side) x += deltaTime * speed;
        if(!side) x-= deltaTime * speed;
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
        sprite.setScale(2.5f);
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