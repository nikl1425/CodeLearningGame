package com.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gameObjects.ImageFileClass;
import com.gameObjects.WorldGenerator;


public class LevelScreen implements Screen {

    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private TextButton play, back;
    ImageFileClass ImageFileClass = new ImageFileClass();
    WorldGenerator worldGenerator;
    private SpriteBatch batch;
    String[] List;
    public int level;

    private void setupTable(){
        //Samler tingene i table
        table.clear();
        table.setBounds(0,0, stage.getWidth(), stage.getHeight());
        table.add("SELECT LEVEL").center().top().spaceBottom(100);
        table.add().row();
        table.add(scrollPane).height(200).center().spaceBottom(20).width(100);
        table.add().row();
        table.add(play).center().spaceBottom(10).width(120).height(60);
        table.add().row();
        table.add(back).center().spaceBottom(10).width(120).height(60);


    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        ImageFileClass.loadTextures();
        ImageFileClass.backgroundSprite.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        

        list = new List<String>(skin);
        list.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        list.setAlignment(Align.center);
        list.addListener(new InputListener());

        scrollPane = new ScrollPane(list, skin);

        play = new TextButton("PLAY", skin);
        play.pad(15);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(list.getSelected() == "1"){
                    level = 1;
                    worldGenerator = new WorldGenerator(level);
                }
                else if(list.getSelected() == "2"){
                    level = 2;
                    worldGenerator = new WorldGenerator(level);
                }
                else if(list.getSelected() == "3"){
                    level = 3;
                    worldGenerator = new WorldGenerator(level);
                }
                //((Game) Gdx.app.getApplicationListener()).setScreen(new com.screens.GameScreen());

            }
        });


        back = new TextButton("BACK", skin);
        back.pad(10);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });



        setupTable();
        stage.addActor(table);
    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setFillParent(true);
        table.setClip(true);
        setupTable();
    }

    @Override
    public void pause() {
       /* Gdx.app.getPreferences(MyGdxGame.TITLE).putBoolean("fullscreen", true);
        Gdx.app.getPreferences(MyGdxGame.TITLE).getBoolean("fullscreen");
        Gdx.app.getPreferences(MyGdxGame.TITLE).flush();*/
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }

}
