package com.mygame.spacecatch.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;

public class Bullet2Segments {
    Sprite sprite;
    private float x, y;
    private float prevX, prevY;
    private CollisionRect rect;
    Bullet2Segments(float x, float y){
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        sprite = new Sprite(new Texture("bullet2.png"));
        this.rect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
    }
    public void setPosition(float x, float y) {
        prevX = this.x;
        prevY = this.y;
        this.x = x;
        this.y = y;
        rect.move((int) x, (int) y);
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getPrevX() {
        return prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
}
