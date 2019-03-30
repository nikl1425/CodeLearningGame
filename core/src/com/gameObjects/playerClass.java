package com.gameObjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.com.jesper.jespil.tween.Animation;

public class playerClass {
    private Vector2 position;
    private Animation playerAnimation;
    private Texture player;

    public playerClass( int x, int y){
        position = new Vector2(x,y);
        player = new Texture ( "images/player.png");
        Texture texture = new Texture("Animation/playeranimation.png");
        playerAnimation = new Animation(new TextureRegion(texture), 6,1f);
    }

    public void update(float delta){
        playerAnimation.update(delta);

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
