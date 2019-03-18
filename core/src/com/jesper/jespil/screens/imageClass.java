package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class imageClass {

    public Texture backgroundTexture;
    public Sprite backgroundSprite;


    public void loadTextures() {
        backgroundTexture = new Texture("download.jpeg");
        backgroundSprite =new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
}
