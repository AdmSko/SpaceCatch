package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;

import java.util.Random;

/**
 * Class contains information about question. Its texture, position and collision check with player
 */
public class Question {
    /** Question texture */
    public Texture texture;
    /** Question sprite */
    public Sprite sprite;
    /** Question X, Y position */
    public float x,y;
    /** maximum Y value */
     private static final int UPPERBOUND = 500;
    /** Object of CollisionRect class to check collision of the object with a player */
    CollisionRect rect;
    /**
     * Default constructor
     *
     */
    public Question(){
        texture = new Texture("question.png");
        sprite = new Sprite(texture);
        Random rand = new Random();
        while(x<texture.getWidth())x = rand.nextInt((int)(Gdx.graphics.getWidth()-sprite.getWidth()));
        y = rand.nextFloat(UPPERBOUND);
    }

    /**
     * Method updates location and speed of the object
     *
     * @param deltaTime Time between the start of the previous and the start of the current call
     */
    public void Update (float deltaTime){

        this.rect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
    }

    /**
     * Method that renders health, sets proper position and scale
     * @param batch Object of a SpriteBatch class, contains a batch of sprites to draw
     */
    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.setScale(1.3f);
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
