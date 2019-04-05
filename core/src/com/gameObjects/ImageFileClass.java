package com.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class ImageFileClass {

    public Texture backgroundTexture;
    public Sprite backgroundSprite;


    public void loadTextures() {
        backgroundTexture = new Texture("download.jpeg");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }
}
