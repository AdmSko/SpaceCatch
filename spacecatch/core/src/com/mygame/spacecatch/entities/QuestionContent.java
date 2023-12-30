package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygame.spacecatch.screens.MainGameScreen;
import com.mygame.spacecatch.tools.CollisionRect;


import java.util.ArrayList;
import java.util.Random;

public class QuestionContent {

    public ArrayList<Vector2>location;
    BitmapFont questionFont,qstnFnt;
    int a, b, goodAnswer, badAnswer1, badAnswer2, whichImg, playerWidth, playerHeight;
    float playerX, playerY;
    private static final int QSTN_WIDTH =200, QSTN_HEIGHT = 50, MENUINGAMEWITDH = 100, MENUINGAMEHEIGHT = 40;
    float menuInGameX,menuInGameY;
    public int questionType;
    CollisionRect rect;
    public GlyphLayout question,goodAnswerGl,badAnswer1Gl,badAnswer2Gl,toSkip;
    ArrayList <GlyphLayout> glyphLayoutList;
    Texture resistor, coil, diode, cell, capacitor;
    ArrayList<Texture> texturesList;
    ArrayList<String> possibleAnswers = new ArrayList<>();
    private static final int MAXY =Gdx.graphics.getHeight()-80;
    public QuestionContent(float playerX,float playerY, int playerWidth, int playerHeight, float menuInGameX, float menuInGameY){
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerWidth = playerWidth;
        this.playerHeight = playerHeight;
        this.menuInGameX = menuInGameX;
        this.menuInGameY = menuInGameY;
        location = new ArrayList<>();
        Random rand = new Random();
        questionFont = new BitmapFont(Gdx.files.internal("questionFont.fnt"));
        questionFont.getData().setScale(1f);
        glyphLayoutList = new ArrayList<>();
        questionType =rand.nextInt(2);
        //random question type
        switch (questionType) {
            // type 0 - mathematical
            case (0):
                qstnFnt = new BitmapFont(Gdx.files.internal("questionFont.fnt"));
                qstnFnt.getData().setScale(1.5f, 1f);
                //rand numbers
                a = rand.nextInt(21);       //rand number a
                b = rand.nextInt(21);       //rand number b
                while (a == 0) a = rand.nextInt(21);
                while (b == 0) b = rand.nextInt(21);
                //random math sign
                switch (rand.nextInt(4)) {
                    case (0):
                        goodAnswer = a + b;
                        question = new GlyphLayout(qstnFnt, a + "+" + b + "=?");
                        break;
                    case (1):
                        goodAnswer = a - b;
                        question = new GlyphLayout(qstnFnt, a + "-" + b + "=?");
                        break;
                    case (2):
                        goodAnswer = a * b;
                        question = new GlyphLayout(qstnFnt, a + "x" + b + "=?");
                        break;
                    case (3):
                        goodAnswer = a % b;
                        badAnswer1 = goodAnswer + rand.nextInt(4);
                        badAnswer2 = goodAnswer - rand.nextInt(4);
                        question = new GlyphLayout(qstnFnt, a + "%" + b + "=?");
                        break;
                    default:
                        break;
                }
                //creating wrong answers
                badAnswer1 = goodAnswer - rand.nextInt(10);
                badAnswer2 = goodAnswer + rand.nextInt(10);
                while(badAnswer1 == goodAnswer || badAnswer2 == goodAnswer){
                    badAnswer1 = goodAnswer - rand.nextInt(10);
                    badAnswer2 = goodAnswer + rand.nextInt(10);
                }
                //preparing text to GlyphLayout
                goodAnswerGl = new GlyphLayout(questionFont, Integer.toString(goodAnswer));
                badAnswer1Gl = new GlyphLayout(questionFont, Integer.toString(badAnswer1));
                badAnswer2Gl = new GlyphLayout(questionFont, Integer.toString(badAnswer2));
                glyphLayoutList.add(question);
                glyphLayoutList.add(goodAnswerGl);
                glyphLayoutList.add(badAnswer1Gl);
                glyphLayoutList.add(badAnswer2Gl);
                //question location
                location.add(new Vector2((int) (Gdx.graphics.getWidth() / 2) - question.width / 2, Gdx.graphics.getHeight() - question.height * 1.5f));
                break;
            //type 1 - electronic
            case(1):
                questionFont.getData().setScale(0.8f);
                //preparing Textures
                possibleAnswers = new ArrayList<>();
                possibleAnswers.add("RESISTOR");
                possibleAnswers.add("CAPACITOR");
                possibleAnswers.add("DIODE");
                possibleAnswers.add("CELL");
                possibleAnswers.add("COIL");
                resistor = new Texture("resistor.png");
                capacitor = new Texture("capacitor.png");
                diode = new Texture("diode.png");
                cell = new Texture("cell.png");
                coil = new Texture("coil.png");
                texturesList = new ArrayList<>();
                texturesList.add(resistor);
                texturesList.add(capacitor);
                texturesList.add(diode);
                texturesList.add(cell);
                texturesList.add(coil);
                toSkip = new GlyphLayout(questionFont, "ToSkipIndex0");       //to moze cos spierdolic
                glyphLayoutList.add(goodAnswerGl);
                //picking random questionImg and answers
                whichImg = rand.nextInt(5);
                int rndanswer1 = rand.nextInt(5), rndanswer2 = rand.nextInt(5);
                while(rndanswer1 == whichImg) rndanswer1 = rand.nextInt(5);
                while(rndanswer2 == whichImg || rndanswer2 == rndanswer1) rndanswer2 = rand.nextInt(5);
                //setting up possible answers
                goodAnswerGl = new GlyphLayout(questionFont, possibleAnswers.get(whichImg));
                badAnswer1Gl = new GlyphLayout(questionFont, possibleAnswers.get(rndanswer1));
                badAnswer2Gl = new GlyphLayout(questionFont, possibleAnswers.get(rndanswer2));
                //adding answers to layout
                glyphLayoutList.add(goodAnswerGl);
                glyphLayoutList.add(badAnswer1Gl);
                glyphLayoutList.add(badAnswer2Gl);
                //question location
                location.add(new Vector2((int)(Gdx.graphics.getWidth()/2 - QSTN_WIDTH/2),
                        (int)(Gdx.graphics.getHeight() - QSTN_HEIGHT*1.2f)));

            break;
            default: break;
        }
        //random locations for answers
        for(int i = 1; i<4; i++)
            location.add(new Vector2(rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight())));
        //check if location is proper / make sure that answers don't collide with each other
        for (int i = 1; i < 4; i++) {
            // Checking X and Y location
            while (location.get(i).x <= glyphLayoutList.get(i).width
                    || location.get(i).x >= Gdx.graphics.getWidth() - glyphLayoutList.get(i).width
                    || checkObjectCollisions(i)
                    || checkPlayerCollision(i)
                    || checkMenuButtonCollision(i)
                    || location.get(i).y <= glyphLayoutList.get(i).height
                    || location.get(i).y >= MAXY - glyphLayoutList.get(i).height) {
                location.get(i).x = rand.nextFloat(Gdx.graphics.getWidth());
                location.get(i).y = rand.nextFloat(MAXY);
            }
        }
        /*for(int i = 1; i<4; i++) {          //-----------------przez te warunki prawdopodobnie czasem sie crashuje ?!?!?!?!?
            //checking X location
            while (location.get(i).x <= glyphLayoutList.get(i).width
            || location.get(i).x >= Gdx.graphics.getWidth() - glyphLayoutList.get(i).width
            || (location.get(1).x < location.get(2).x + glyphLayoutList.get(2).width && location.get(1).x + glyphLayoutList.get(1).width > location.get(2).x)
            || (location.get(1).x < location.get(3).x + glyphLayoutList.get(3).width && location.get(1).x + glyphLayoutList.get(1).width > location.get(3).x)
            || (location.get(2).x < location.get(3).x + glyphLayoutList.get(3).width && location.get(2).x + glyphLayoutList.get(2).width > location.get(3).x)
            || (location.get(i).x < playerX + playerWidth && location.get(i).x + glyphLayoutList.get(i).width > playerX)) {
                location.get(i).x = rand.nextFloat(Gdx.graphics.getWidth());
                if (location.get(3).x < location.get(2).x + glyphLayoutList.get(2).width && location.get(3).x + glyphLayoutList.get(3).width > location.get(2).x)
                    location.get(3).x = rand.nextFloat(Gdx.graphics.getWidth());
            }
            //checking Y location
            while (location.get(i).y <= glyphLayoutList.get(i).height
            || location.get(i).y >= MAXY - glyphLayoutList.get(i).height
            || (location.get(1).y < location.get(2).y + glyphLayoutList.get(2).height && location.get(1).y + glyphLayoutList.get(1).height > location.get(2).y)
            || (location.get(1).y < location.get(3).y + glyphLayoutList.get(3).height && location.get(1).y + glyphLayoutList.get(1).height > location.get(3).y)
            || (location.get(2).y < location.get(3).y + glyphLayoutList.get(3).height && location.get(2).y + glyphLayoutList.get(2).height > location.get(3).y)
            || (location.get(i).y < playerY + playerHeight && location.get(i).y + glyphLayoutList.get(i).height > playerY)) {
                location.get(i).y = rand.nextFloat(MAXY);
                if(location.get(3).y < location.get(2).y + glyphLayoutList.get(2).height && location.get(3).y + glyphLayoutList.get(3).height > location.get(2).y)
                    location.get(3).y = rand.nextFloat(MAXY);
            }
        }*/
    }
    public void Update(float deltaTime){
            //checking collision with player of every possible answer
                this.rect = new CollisionRect(location, glyphLayoutList);
    }
    public void render(SpriteBatch batch){
        //draw question & answers
        switch (questionType){
            case(0):
                questionFont.draw(batch, question, location.get(0).x,location.get(0).y);
                questionFont.draw(batch, goodAnswerGl, location.get(1).x, location.get(1).y);
                questionFont.draw(batch, badAnswer1Gl, location.get(2).x, location.get(2).y);
                questionFont.draw(batch, badAnswer2Gl, location.get(3).x, location.get(3).y);
                break;
            case(1):
                batch.draw(texturesList.get(whichImg), location.get(0).x, location.get(0).y, QSTN_WIDTH, QSTN_HEIGHT);
                questionFont.draw(batch, goodAnswerGl, location.get(1).x, location.get(1).y);
                questionFont.draw(batch, badAnswer1Gl, location.get(2).x, location.get(2).y);
                questionFont.draw(batch, badAnswer2Gl, location.get(3).x, location.get(3).y);
                break;
            default:break;
        }
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
    //additional method to support collisions of answers
    private boolean checkObjectCollisions(int currentIndex) {
        for (int j = 1; j < 4; j++) {
            if (j != currentIndex) {
                if (location.get(currentIndex).x < location.get(j).x + glyphLayoutList.get(j).width
                        && location.get(currentIndex).x + glyphLayoutList.get(currentIndex).width > location.get(j).x
                        && location.get(currentIndex).y < location.get(j).y + glyphLayoutList.get(j).height
                        && location.get(currentIndex).y + glyphLayoutList.get(currentIndex).height > location.get(j).y) {
                    return true; //collision with other answer
                }
            }
        }
        return false; //no collision
    }
    //additional method to support collisions of answers
    private boolean checkPlayerCollision(int currentIndex) {
        if (location.get(currentIndex).x < playerX + playerWidth
                && location.get(currentIndex).x + glyphLayoutList.get(currentIndex).width > playerX
                && location.get(currentIndex).y < playerY + playerHeight
                && location.get(currentIndex).y + glyphLayoutList.get(currentIndex).height > playerY) {
            return true; // collision with a player
        }
        return false; //no collision
    }
    //additional method to support collisions of answers
    private boolean checkMenuButtonCollision(int currentIndex){
        if (location.get(currentIndex).x < menuInGameX + MENUINGAMEWITDH
                && location.get(currentIndex).x + glyphLayoutList.get(currentIndex).width > menuInGameX
                && location.get(currentIndex).y < menuInGameY + MENUINGAMEHEIGHT
                && location.get(currentIndex).y + glyphLayoutList.get(currentIndex).height > menuInGameY) {
            return true; // collision with a player
        }
        return false; //no collision
    }
}
