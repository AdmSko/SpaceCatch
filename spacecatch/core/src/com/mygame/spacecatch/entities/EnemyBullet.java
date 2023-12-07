package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygame.spacecatch.tools.CollisionRect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyBullet {
    float speed;
    private static Texture texture;
    Sprite sprite;
    float x,y;
    private static final int UPPERBOUND = 520;
    boolean side;
    public boolean remove = false;
    CollisionRect rect;

    public EnemyBullet(){                           //!!!! ADD REMOVING BULLETS AFTER LEAVING SCREEN
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
        while(speed == 0) speed = rand.nextFloat(UPPERBOUND);
    }
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
    public void render(SpriteBatch batch){
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
}
