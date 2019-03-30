package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextInputField implements Screen {

    private TextureAtlas atlas;
    private Skin skin;
    private Stage stage;
    public TextArea textArea;
    public TextButton textButton;
    public int amountofMoves;


    public TextInputField(){

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack"); //Bruges til textbutton
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas); //Bruges til textbutton

        textButton = new TextButton("Run!", skin);

        textButton.setPosition(640,0);
        textButton.setSize(240,60);
        textButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                //textButton.setText("Compiling!");

            }
        });
        textButton.addListener(new ClickListener(){
          public void clicked(InputEvent event, float x, float y){
              //textButton.setText("Compiling!");
          }
        });

        textArea = new TextArea("", skin);
        textArea.setPosition(640,60);
        textArea.setPrefRows(5);
        textArea.setSize(240,240);


        stage.addActor(textArea);
        stage.addActor(textButton);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
