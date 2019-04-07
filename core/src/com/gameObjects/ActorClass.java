package com.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.screens.GameScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActorClass extends Actor {
    public Sprite sprite;
    public Rectangle rectangle;
    public ArrayList<String[]> cmdList;
    public List<String> cmdListRepeat;


    public ActorClass(String imagePath, int sizeX, int sizeY){
        setSize(sizeX,sizeY);
        setBounds(getX(), getY() ,getWidth(),getHeight());
        setOrigin(getWidth()/2,getHeight()/2);
        sprite = new Sprite(new Texture(imagePath));
        sprite.setSize(sizeX,sizeY);
        cmdList = new ArrayList<>();
        cmdListRepeat = new LinkedList<>();
        rectangle = this.sprite.getBoundingRectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(),getY());
        sprite.setRotation(getRotation());
        setDebug(true);
        sprite.draw(batch, parentAlpha);
        sprite.rotate(90);
        sprite.setScale(getScaleX(), getScaleY());

    }


    public void act(float deltaT) {
        super.act(deltaT);
        if (getActions().size == 0 && !cmdList.isEmpty()) {
            addAction(GameScreen.parseCommands(cmdList.remove(0), ActorClass.this));
        }



    }

    public void setCommands(ArrayList<String[]> cmds){
        for (String[] cmd : cmds){
            cmdList.add(cmd);
        }
    }
}
