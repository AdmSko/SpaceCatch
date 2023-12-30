package com.mygame.spacecatch.tools;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class CollisionRect {
    float x, y;

    ArrayList<Vector2> location;
    ArrayList<GlyphLayout> glyphLayout;
    int width, height;
    //Constructor for arraylists collisions -> QuestionContent
    public CollisionRect(ArrayList<Vector2>location, ArrayList<GlyphLayout>glyphLayout){
        this.location = location;
        this.glyphLayout = glyphLayout;
    }
    //Constructor for object collision with player
    public CollisionRect(float x, float y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;
    }
    public void move(float x,float y){
        this.x = x;
        this.y = y;
    }
    public boolean collidesWith (CollisionRect rect){
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }
    public int collidesWithAnswer (CollisionRect rect){                          //tutaj jeszcze sprawdzic czy na pewno dobrze
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
