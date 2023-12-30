package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.Random;
public class EnemyBullet {
    float speed;
    private static Texture texture;
    Sprite sprite;
    float x,y;
    int gameTime, speedBound=100, originSpeed=10;
    private static final int UPPERBOUND = 520;

    boolean side;
    public boolean remove = false;
    CollisionRect rect;

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
        sprite.setScale(2.5f);
        sprite.draw(batch);
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
}