package com.mygame.spacecatch.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.spacecatch.tools.CollisionRect;

/**
 * Class used to creating additional segments for EnemyBullet2 object
 */
public class Bullet2Segments {
    /**	Sprite of a bullet */
    Sprite sprite;
    /** position of a bullet */
    private float x, y;
    /** previous position of a bullet */
    private float prevX, prevY;
    /** Object of CollisionRect class to check collision of the object with a player */
    private CollisionRect rect;

    /**
     * Constructor that collects position and set new objects
     * @param x X-axis position of EnemyBullet2 object
     * @param y Y-axis position of EnemyBullet2 object
     */
    Bullet2Segments(float x, float y){
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        sprite = new Sprite(new Texture("bullet2.png"));
        this.rect = new CollisionRect(x, y, (int)sprite.getWidth(), (int)sprite.getHeight());
    }

    /**
     * Method sets new position of the object, updates collision check
     * @param x X-axis position of EnemyBullet2 object
     * @param y Y-axis position of EnemyBullet2 object
     */
    public void setPosition(float x, float y) {
        prevX = this.x;
        prevY = this.y;
        this.x = x;
        this.y = y;
        rect.move((int) x, (int) y);
    }

    /**
     * current X-axis position getter
     * @return current X-axis position of the bullet
     */
    public float getX() {
        return x;
    }
    /**
     * current Y-axis position getter
     * @return current Y-axis position of the bullet
     */
    public float getY() {
        return y;
    }
    /**
     * previous X-axis position getter
     * @return previous X-axis position of the bullet
     */
    public float getPrevX() {
        return prevX;
    }
    /**
     * previous Y-axis position getter
     * @return previous Y-axis position of the bullet
     */
    public float getPrevY() {
        return prevY;
    }
    /**
     * Method renders object via setting position and scale of the object
     * @param batch Object of a SpriteBatch class, contains a batch of sprites to draw
     */
    public void render(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
}
