package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygame.spacecatch.tools.CollisionRect;

import java.util.Random;

public class EnemyBullet1 {
    float speed =200;
    private static Texture texture;
    Sprite sprite;
    float x,y;
    float plX, plY;
    float time=0f, period;
    private float lastDirectionX;
    private float lastDirectionY;
    int upperSpeed = 200;
    int side,gameTime,originSpeed=50;
    public boolean remove = false;
    CollisionRect rect;

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
    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
}

