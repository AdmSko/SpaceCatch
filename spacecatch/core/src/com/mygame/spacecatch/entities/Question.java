package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;

import java.util.Random;

public class Question {
    public Texture texture;
    public Sprite sprite;
    public float x,y;
     private static final int UPPERBOUND = 500;
    CollisionRect rect;
    public Question(){
        texture = new Texture("question.png");
        sprite = new Sprite(texture);
        Random rand = new Random();
        while(x<texture.getWidth())x = rand.nextInt((int)(Gdx.graphics.getWidth()-sprite.getWidth()));
        y = rand.nextFloat(UPPERBOUND);
    }
    public void Update (float deltaTime){

        this.rect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
    }
    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.setScale(1.3f);
        sprite.draw(batch);
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
}
