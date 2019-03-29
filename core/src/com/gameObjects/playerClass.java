package com.gameObjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.com.jesper.jespil.tween.Animation;

public class playerClass {
    private Vector2 position;
    private Animation playerAnimation;

    private Texture player;

    public playerClass(int x, int y){
        position = new Vector2(x,y);
        player = new Texture ( "images/player.png");
        Texture texture = new Texture("Animation/playeranimation.png");
        playerAnimation = new Animation(new TextureRegion(texture), 6,1f);

    }

    public void update(float delta){
        playerAnimation.update(delta);

        //WALK
        boolean left = Gdx.input.isKeyPressed(21);
        boolean right = Gdx.input.isKeyPressed(22);
        boolean up = Gdx.input.isKeyPressed(19);
        boolean down = Gdx.input.isKeyPressed(20);
        if(left){
            position.add(-1,0);

        }
        if(right){
            position.add(+1,0);
        }
        if(up){
            position.add(0,+1);
        }
        if(down){
            position.add(0,-1);
        }

    }

    public Vector2 getPosition(){
        return position;
    }

    public TextureRegion getPlayer() {
        return playerAnimation.getFrame();
    }


    public void dispose(){
        player.dispose();
    }
}
