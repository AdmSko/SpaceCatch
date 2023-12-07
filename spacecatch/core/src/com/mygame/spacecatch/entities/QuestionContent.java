package com.mygame.spacecatch.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygame.spacecatch.tools.CollisionRect;

import java.util.ArrayList;
import java.util.Random;

public class QuestionContent {

    public ArrayList<Vector2>location;
    BitmapFont questionFont,qstnFnt;
    int a, b, goodAnswer, badAnswer1, badAnswer2, questionType;
    CollisionRect rect;
    public GlyphLayout question,goodAnswerGl,badAnswer1Gl,badAnswer2Gl;
    ArrayList <GlyphLayout> glyphLayoutList;
    private static final int MAXY =Gdx.graphics.getHeight()-80;
    public QuestionContent(){

        location = new ArrayList<>();
        Random rand = new Random();
        questionFont = new BitmapFont(Gdx.files.internal("questionFont.fnt"));
        questionFont.getData().setScale(1f);
        questionType = 0;//rand.nextInt(2);

        //random question type
        switch (questionType) {
            // type 0 - mathematical
            case (0):
                glyphLayoutList = new ArrayList<>();
                qstnFnt = new BitmapFont(Gdx.files.internal("questionFont.fnt"));
                qstnFnt.getData().setScale(1.5f, 1f);

                //rand numbers
                a = rand.nextInt(20);       //rand number a
                b = rand.nextInt(20);       //rand number b
                while (a == 0) a = rand.nextInt(20);
                while (b == 0) b = rand.nextInt(20);
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

                //prepare text to GlyphLayout
                goodAnswerGl = new GlyphLayout(questionFont, Integer.toString(goodAnswer));
                badAnswer1Gl = new GlyphLayout(questionFont, Integer.toString(badAnswer1));
                badAnswer2Gl = new GlyphLayout(questionFont, Integer.toString(badAnswer2));
                glyphLayoutList.add(question);
                glyphLayoutList.add(goodAnswerGl);
                glyphLayoutList.add(badAnswer1Gl);
                glyphLayoutList.add(badAnswer2Gl);

                //setting location on screen
                location.add(new Vector2((int) (Gdx.graphics.getWidth() / 2) - question.width / 2, Gdx.graphics.getHeight() - question.height * 1.5f)); //tu moze pogrzebac tak zeby ladnie bylo
                for (int i = 1; i < 4; i++)
                    location.add(new Vector2(rand.nextFloat(Gdx.graphics.getWidth()), rand.nextFloat(Gdx.graphics.getHeight())));

                //check if location is proper
                //!!!!dodac jeszcze sprawdzenie kolizji miedzy odpowiedziamy zeby dopieraly inne location.x/y
                for (int i = 1; i < 4; i++) {
                    //checking X location
                    while (location.get(i).x <= glyphLayoutList.get(i).width
                            || location.get(i).x >= Gdx.graphics.getWidth() - glyphLayoutList.get(i).width)
                        location.get(i).x = rand.nextFloat(Gdx.graphics.getWidth());
                    //checking Y location
                    while (location.get(i).y >= MAXY - glyphLayoutList.get(i).height)
                        location.get(i).y = rand.nextFloat(MAXY);
                }
                break;
            //type 1 - electronic
            case(1):

            break;

            default: break;
        }
    }
    public void Update(float deltaTime){
            //checking collision of every possible answer
            this.rect = new CollisionRect(location, glyphLayoutList );
    }
    public void render(SpriteBatch batch){  //ogarnac to zeby zadne dziadostwo nie wychodzilo ani troche poza ekran i zeby byly dosyc daleko od siebie

        //draw question & answers
        questionFont.draw(batch, question, location.get(0).x,location.get(0).y);
        questionFont.draw(batch, goodAnswerGl, location.get(1).x, location.get(1).y);
        questionFont.draw(batch, badAnswer1Gl, location.get(2).x, location.get(2).y);
        questionFont.draw(batch, badAnswer2Gl, location.get(3).x, location.get(3).y);
    }
    public CollisionRect getCollisionRect(){
        return rect;
    }

}
