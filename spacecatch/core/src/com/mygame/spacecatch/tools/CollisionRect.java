package com.mygame.spacecatch.tools;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 * Contains rectangular collision mechanic
 */
public class CollisionRect {
    /**	x, Y position of the object */
    float x, y;
    /**	Arraylist of vector2 objects, contains location of objects to check collision with player */
    ArrayList<Vector2> location;
    /**	Contains width and height of objects that will be checked for collision with player*/
    ArrayList<GlyphLayout> glyphLayout;
    /**	width and height of object sprite */
    int width, height;

    /**
     * Constructor of the class, collects positions - for multiple objects collision check
     * @param location Location of objects - x, y
     * @param glyphLayout Contains glyph layouts, needed for width and length
     */
    public CollisionRect(ArrayList<Vector2>location, ArrayList<GlyphLayout>glyphLayout){
        this.location = location;
        this.glyphLayout = glyphLayout;
    }
    /**
     * Constructor of the class, collects positions - for single object collision check with player
     * @param x X-axis location of the object
     * @param y Y-axis location of the object
     * @param width Width of the object
     * @param height Height of the object
     */
    public CollisionRect(float x, float y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;
    }

    /**
     * Method for updating player position on screen
     * @param x X-axis location of the player
     * @param y Y-axis location of the player
     */
    public void move(float x,float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Method containing collision check for a single object
     * @param rect CollisionRect object with object data
     * @return rectangular collision - boolean value
     */
    public boolean collidesWith (CollisionRect rect){
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }

    /**
     * Method containing collision check for multiple objects
     * @param rect CollisionRect object with objects data
     * @return returns value 1 if answer is good, 2 if answer is wrong, and 0 if there's no collision
     */
    public int collidesWithAnswer (CollisionRect rect){
        //if good answer
        if(location.get(1).x < rect.x + rect.width
                && location.get(1).y < rect.y +rect.height
                && location.get(1).x + glyphLayout.get(1).width > rect.x
                && location.get(1).y + glyphLayout.get(1).height > rect.y){
            location.clear();
            glyphLayout.clear();
            return 1;
        }
        //if bad answer
        else if((location.get(2).x < rect.x + rect.width
                && location.get(2).y < rect.y +rect.height
                && location.get(2).x + glyphLayout.get(2).width > rect.x
                && location.get(2).y + glyphLayout.get(2).height > rect.y)
                ||(location.get(3).x < rect.x + rect.width
                && location.get(3).y < rect.y +rect.height
                && location.get(3).x + glyphLayout.get(3).width > rect.x
                && location.get(3).y + glyphLayout.get(3).height > rect.y)){
            location.clear();
            glyphLayout.clear();
            return 2;
        }
        return 0;
    }



}
