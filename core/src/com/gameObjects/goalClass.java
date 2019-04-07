/*package com.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;


public class goalClass extends Actor {
    public Sprite sprite;
    public Rectangle goalRect;
    int tileSize = 32;
    int x   ,   y;


    public goalClass(String imagePath, int sizeX, int sizeY) {
        Random r = new Random();
        x = (r.nextInt(5) * tileSize);
        y = (r.nextInt(5) * tileSize);

        setSize(sizeX, sizeY);
        setBounds(x, y, getWidth(), getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        sprite = new Sprite(new Texture(imagePath));
        sprite.setSize(sizeX, sizeY);
        goalRect = sprite.getBoundingRectangle();


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(x , y);
        sprite.setRotation(getRotation());
        setDebug(true);
        sprite.draw(batch, parentAlpha);
        sprite.rotate(90);
        sprite.setScale(getScaleX(), getScaleY());

    }
}

*/


      /*  for (int i = 0; i < amountGoals; i++) {
            goalClassList.get(i).goalRect.set(goalClassList.get(i).getX(),goalClassList.get(i).getY(),goalClassList.get(i).sprite.getWidth(),goalClassList.get(i).sprite.getHeight());
            if (playerObject.rectangle.overlaps(goalClassList.get(i).goalRect)){
                if(goalClassList.size()>0){
                    //goalClassList.get(i).remove();
                    goalClassList.remove(i);
                    System.out.println(goalClassList.size());
                    if(goalClassList.size()<=0){
                        int newLvl = level + 1;
                        goalClassList.clear();
                        worldGenerator = new WorldGenerator(newLvl);
                        System.out.println("New Level!");

                    }
                }


            }*/