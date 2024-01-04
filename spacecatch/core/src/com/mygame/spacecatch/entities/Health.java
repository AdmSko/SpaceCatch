package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Class contains health mechanism, loading sprites and drawing position
 */
public class Health {
    final static private float WIDTH = 80;
    Sprite[] healthSprite = {
            new Sprite(new Texture("Health0.png")),
            new Sprite(new Texture("Health0_5.png")),
            new Sprite(new Texture("Health1.png")),
            new Sprite(new Texture("Health1_5.png")),
            new Sprite(new Texture("Health2.png")),
            new Sprite(new Texture("Health2_5.png")),
            new Sprite(new Texture("fullHealth.png"))
    };
    Sprite healthOut;

    /**
     * Empty constructor
     */
    public Health(){
    }

    /**
     *Method that checks health changes
     *
     * @param deltaTime Time between the start of the previous and the start of the current call
     * @param health Integer that indicates current health number, health = 6 full health, health =0 game over
     */
    public void Update(float deltaTime, int health){
        if(health >= 0) healthOut = healthSprite[health];
    }

    /**
     * Method that renders health, sets proper position and scale
     * @param batch Object of a SpriteBatch class, contains a batch of sprites to draw
     */
    public void render(SpriteBatch batch){
        healthOut.setPosition(WIDTH,Gdx.graphics.getHeight() - healthOut.getHeight()*6);
        healthOut.setScale(6);
        healthOut.draw(batch);
    }

}
