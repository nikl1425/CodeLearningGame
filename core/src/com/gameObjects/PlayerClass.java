package com.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.screens.GameScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerClass extends Actor {
    public Sprite sprite;
    public Rectangle playerRect;
    public ArrayList<String> cmdList;
    public List<String> cmdListRepeat;
    public int counter = 0;
    public boolean maxEntriesExceeded = false;
    public static SequenceAction sequenceAction = new SequenceAction();


    public PlayerClass(String imagePath, int sizeX, int sizeY){
        setSize(sizeX,sizeY);
        setBounds(getX(),getY(),getWidth(),getHeight());
        setOrigin(getWidth()/2,getHeight()/2);
        sprite = new Sprite(new Texture(imagePath));
        sprite.setSize(sizeX,sizeY);
        cmdList = new ArrayList<>();
        cmdListRepeat = new LinkedList<>();
        playerRect = sprite.getBoundingRectangle();
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

/*
    public void repeatCommands(int amountofRepeats){
        for (int i = 0; i < amountofRepeats; i++) {
            if (!cmdListRepeat.isEmpty()){
                sequenceAction.addAction(GameScreen.parseCommands(cmdListRepeat.get(i), PlayerClass.this));
                //cmdListRepeat.remove(i);
                System.out.println(amountofRepeats);
            }else{
                sequenceAction.reset();
            }
        }

        System.out.println(cmdListRepeat.size());
    }
*/


    public void act(float deltaT) {
        super.act(deltaT);
        if (getActions().size == 0 && !cmdList.isEmpty()) {
            addAction(GameScreen.parseCommands(cmdList.remove(0),PlayerClass.this));
        }


    }

    public void setCommands(String[] cmds){
        for (String cmd : cmds){
            cmdList.add(cmd);
            //cmdListRepeat.add(cmd);
        }
    }
}
