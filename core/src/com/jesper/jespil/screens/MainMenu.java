package com.jesper.jespil.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.com.jesper.jespil.tween.ActorAccessor;
import com.mygdx.game.MyGdxGame;

import static com.badlogic.gdx.Gdx.gl20;


public class MainMenu implements Screen {

    private Stage stage; //done
    private Skin skin; //The appearance of buttons etc //done
    private TextureAtlas atlas; //Text button has a background ,and the background is a picture. //Done
    private Table table; //bruges til at aligne knapper og headings (er en slags container) //done
    private TextButton buttonExit, buttonPlay, buttonSettings;
    private Label heading;
    private TweenManager tweenManager;
    public static final String Menu = "MAIN MENU";
    imageClass imageClass = new imageClass();
    private SpriteBatch batch;
    Settings settings = new Settings();

    @Override
    public void render(float delta) { //update
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(gl20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        imageClass.loadTextures();
        imageClass.backgroundSprite.draw(batch);
        batch.end();
        tweenManager.update(delta);



        stage.act(delta); //opdater alt i stage (stage har table - derfor opdateres det også ,og alt deri)
        stage.draw(); //Her tegner vi hele lortet
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setFillParent(true);
        table.setClip(true);

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        settings = new Settings();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack"); //Bruges til textbutton
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas); //Bruges til textbutton

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Creating heading
        heading = new Label(Menu, skin);
        heading.setFontScale(2);






        //Creating button PLAY
        buttonPlay = new TextButton("PLAY", skin);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new levelMenu());
            }
        });
        buttonPlay.pad(25);

        buttonSettings = new TextButton("SETTINGS", skin);
        buttonSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
            }
        });



        //Creating buttons (EXIT
        buttonExit = new TextButton("EXIT", skin);
        buttonExit.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y){
                //Gdx.app.exit();
                Splash splash = new Splash();
                ((Game) Gdx.app.getApplicationListener()).setScreen(splash);

            }
        });
        buttonExit.pad(25);







        //Putting stuff together
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15).height(80).width(150);
        table.row();
        table.add(buttonSettings).height(80).width(150).spaceBottom(15).row();
        table.add(buttonExit).height(80).width(150);
        //table.debug(); //Tilføjer debug linjer
        stage.addActor(table);

        //Her laver vi animationer.
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        // heading color animation
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,0,1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,1,0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,0,0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,1,0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0,1,1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,0,1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1,1,1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        //Heading and buttons fade in
        Timeline.createSequence().beginSequence() //Starten af animations sekvensen
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .8f).target(0))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .8f).target(1))
                .push(Tween.to(buttonSettings, ActorAccessor.ALPHA, .8f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .8f).target(1))
                .end().start(tweenManager);

        // table fade-in
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(5).start(tweenManager);
        Tween.from(table, ActorAccessor.Y, 2.5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);
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
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        batch.dispose();

    }
}
