package com.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextInputField {

    private TextureAtlas atlas;
    private Skin skin;
    public TextArea textArea;
    public TextButton textButton;
    public Stage stage;


    public TextInputField(int posX, int posY, int buttonWidth, int buttonHeight, int textWidth, int textHeight){
        atlas = new TextureAtlas("ui/atlas.pack"); //Bruges til textbutton
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas); //Bruges til textbutton
        textButton = new TextButton("Run!", skin);
        textButton.setPosition(posX,posY);
        textButton.setSize(buttonWidth,buttonHeight);
        textArea = new TextArea("", skin);
        textArea.setPosition(posX,posY);
        textArea.setSize(textWidth,textHeight);
    }
}

