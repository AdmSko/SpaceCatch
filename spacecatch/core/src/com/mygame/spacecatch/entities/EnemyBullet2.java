package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygame.spacecatch.tools.CollisionRect;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Class contains information about bullet like speed, sprite and its behaviour
 */
public class EnemyBullet2 {
    /** bullet texture  */
    private static Texture texture;
    /** bullet sprite */
    private Sprite sprite;
    /** X, Y bullet position */
    private float x, y;
    /** bullet speed */
    private float speed = 150f;
    /** Amplitude of vertical bullet movement */
    private float amplitude = 300f;
    /** angular bullet speed */
    private float angularSpeed = 30f;
    /** angle of bullet movement, based on angular speed*/
    private float angle = 0f;
    /** Object of CollisionRect class to check collision of the object with a player */
    private CollisionRect rect;
    /** list of segments added behind main bullet*/
    private List<Bullet2Segments> segments;
    /** changes state if bullet leaves a screen, ready to be removed*/
    public boolean remove = false;
    /** game time*/
     int gameTime;
    /** maximum bullet speed */
     int speedBound=250;
    /** minimum bullet speed */
     int originSpeed=70;
    /** Up or Down, initial bullet spawn border Y value */
    boolean side;
    /**
     * Constructor that sets up objects and initial location
     * @param gameTime Variable that contains time spent in game
     */
    public EnemyBullet2(int gameTime) {
        this.gameTime = gameTime;
        texture = new Texture("bullet2.png");
        sprite = new Sprite(texture);
        Random rand = new Random();
        if(gameTime % 50 == 0){
            speedBound +=50;
            originSpeed +=25;
        }
        if(originSpeed >=300) originSpeed = 300;
        if(speedBound >= 600) speedBound = 600;
        speed = rand.nextFloat(originSpeed, speedBound);
        angularSpeed = rand.nextFloat(20, 40);
        //checking up/down
        if(rand.nextBoolean()) {
            y = 0 ;
            side = true;
        }
        else {
            y = 600;
            side = false;
        }
        x = rand.nextFloat(Gdx.graphics.getWidth()-sprite.getWidth());
        segments = createBulletSegments(x,y);
    }
    /**
     * Method updates location and speed of the object
     * @param deltaTime Time between the start of the previous and the start of the current call
     */
    public void Update(float deltaTime) {
        //check up/down movement
        if(!side){
            angle += angularSpeed * deltaTime;
            x += MathUtils.sin(angle) * amplitude * deltaTime;
            y -= speed * deltaTime;
            //head position/movement
            float headX = segments.get(0).getX() + MathUtils.sin(angle) * amplitude * deltaTime;
            float headY = segments.get(0).getY() - speed * deltaTime;
            segments.get(0).setPosition(headX, headY);
            if (headY < -sprite.getHeight()) {
                remove = true;
            }
        }
        else{
            angle += angularSpeed * deltaTime;
            x -= MathUtils.sin(angle) * amplitude * deltaTime;
            y += speed * deltaTime;
            //head position/movement
            float headX = segments.get(0).getX() - MathUtils.sin(angle) * amplitude * deltaTime;
            float headY = segments.get(0).getY() + speed * deltaTime;
            segments.get(0).setPosition(headX, headY);

            if (headY > Gdx.graphics.getHeight()-sprite.getHeight()) {
                remove = true;
            }
        }
        //tail position/movement
        for (int i = 1; i < segments.size(); i++) {
            Bullet2Segments currentSegment = segments.get(i);
            Bullet2Segments prevSegment = segments.get(i - 1);
            float newX = prevSegment.getPrevX();
            float newY = prevSegment.getPrevY();
            currentSegment.setPosition(newX, newY);
        }
        rect = new CollisionRect(x, y, (int) sprite.getWidth(), (int) sprite.getHeight());
    }
    /**
     * Method renders object via setting position and scale of the object
     * @param batch Object of a SpriteBatch class, contains a batch of sprites to draw
     */
    public void render(SpriteBatch batch) {
        //drawing each segment
        for (Bullet2Segments segment : segments) {
            segment.render(batch);
        }
        sprite.setPosition(x, y);
        sprite.setScale(2);
        sprite.draw(batch);
    }
    /**
     * Returns collision with object
     * @return object of CollisionRect, checking collision with the object
     */
    public CollisionRect getCollisionRect() {
        return rect;
    }

    /**
     * Method creating additional segments of EnemyBullet2
     * @param startX X-axis position of EnemyBullet2 object
     * @param startY Y-axis position of EnemyBullet2 object
     * @return List of bulletSegments that will be drawn
     */
    private List<Bullet2Segments> createBulletSegments(float startX, float startY) {
        List<Bullet2Segments> bulletSegments = new ArrayList<>();
        int numSegments = 10;
        float segmentSpacing = 20f;
        for (int i = 0; i < numSegments; i++) {
            Bullet2Segments segment = new Bullet2Segments(startX, startY - i * segmentSpacing);
            bulletSegments.add(segment);
        }
        return bulletSegments;
    }

}