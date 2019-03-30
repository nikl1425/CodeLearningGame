package com.jesper.jespil.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextArea implements Screen {

    private Game game;
    private TextureAtlas atlas;
    private Skin skin;
    private Stage stage;
    private TextButton btnInput;
    private TextField textInput;


    public TextArea(){

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack"); //Bruges til textbutton
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas); //Bruges til textbutton

        final TextButton btnInput = new TextButton("Run!", skin);

        btnInput.setPosition(640,0);
        btnInput.setSize(240,60);
        btnInput.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                btnInput.setText("Compiling!");
                System.out.println(textInput.getText());

            }
        });

        com.badlogic.gdx.scenes.scene2d.ui.TextArea textInput = new com.badlogic.gdx.scenes.scene2d.ui.TextArea("", skin);
        textInput.setPosition(640,60);
        textInput.setPrefRows(5);
        textInput.setSize(240,240);
        textInput.setText("1.");


        stage.addActor(textInput);
        stage.addActor(btnInput);
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
