package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gameObjects.playerClass;

import javax.swing.JTextField;

public class Game implements Screen {

private Stage stage;
private TiledMap map;
private OrthogonalTiledMapRenderer renderer;
private OrthographicCamera camera;
private ShapeRenderer shape;
private TextField textField;
private JTextField text;
int x = 640;
int y = 0;
int width = 240;
int height = 880;
int amountSteps;

boolean isTestCalled = false;

int w = 32;

private TextArea txtf;

private playerClass player;
private Skin skin;
private TextureAtlas atlas;
SpriteBatch spriteBatch;
TiledMapTileLayer.Cell tiledMapCell;
TiledMapTileLayer layer;





@Override
    public void render(float delta) {
        delta = 0.05f;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        shape.setColor(Color.RED);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(x,y,width,height);
        shape.end();

        /////////
        //DEBUG//
        /////////
        int tileWidth = map.getProperties().get("tilewidth", Integer.class), tileHeight = map.getProperties().get("tileheight", Integer.class);
        int mapWidth = map.getProperties().get("width", Integer.class) * tileWidth, mapHeight = map.getProperties().get("height", Integer.class) * tileHeight;
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        for(int x = 0; x < mapWidth; x += tileWidth)
            shape.line(x, 0, x, mapHeight);
        for(int y = 0; y < mapHeight; y += tileHeight)
            shape.line(0, y, mapWidth, y);
        shape.end();
        /////////
        //DEBUG//
        /////////

        if(Gdx.input.isTouched()){
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(click);
            TiledMapTileLayer.Cell clicked = layer.getCell((int)click.x/w, (int)click.y/w);
            //System.out.println(clicked);
            //System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
        }
        /////////
        //DEBUG//
        /////////
        amountSteps = 5;

        String inputTextLTC ="gå frem: "+amountSteps;

        if(Gdx.input.isTouched()){
            //System.out.println(txtf.getText());
            if(txtf.getText().equals(inputTextLTC) && !isTestCalled){
                System.out.println("Det virker");
                player.getPosition().add((player.getPosition().x+amountSteps),0);
                isTestCalled = true;
                test();

            }
        }



        player.update(delta);
        spriteBatch.begin();
        spriteBatch.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y, w, w);
        spriteBatch.end();

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
        spriteBatch = new SpriteBatch();

        player = new playerClass(5*w,5*w);
        atlas = new TextureAtlas("ui/atlas.pack"); //Bruges til textbutton
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas); //Bruges til textbutton
        this.stage = new Stage();


        txtf = new TextArea("gå frem:", skin);
        txtf.setSize(240,640);
        txtf.setPosition(640,0);

        stage.addActor(txtf);
        Gdx.input.setInputProcessor(this.stage);


        map = new TmxMapLoader().load("maps/tiledmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        layer = (TiledMapTileLayer) map.getLayers().get(0);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape = new ShapeRenderer();

        System.out.println(layer+"LAYERLOL");

        //System.out.println(layer.getCell(1,1));




    }

    @Override
    public void pause() {

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
    shape.dispose();

    }

    private void test()
    {
        System.out.println("test!");
    }

}
