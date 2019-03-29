package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gameObjects.playerClass;
import javafx.scene.shape.Box;

public class Game implements Screen {


private TiledMap map;
private OrthogonalTiledMapRenderer renderer;
private OrthographicCamera camera;
private ShapeRenderer shape;
int x = 640;
int y = 0;
int width = 240;
int height = 880;
private playerClass player;
SpriteBatch spriteBatch;
TiledMapTileLayer.Cell tiledMapCell;
TiledMapTileLayer layer;





@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        shape.setColor(Color.RED);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(x,y,width,height);
        shape.end();


    int tileWidth = map.getProperties().get("tilewidth", Integer.class), tileHeight = map.getProperties().get("tileheight", Integer.class);
    int mapWidth = map.getProperties().get("width", Integer.class) * tileWidth, mapHeight = map.getProperties().get("height", Integer.class) * tileHeight;
    shape.setProjectionMatrix(camera.combined);
    shape.begin(ShapeRenderer.ShapeType.Line);
    for(int x = 0; x < mapWidth; x += tileWidth)
        shape.line(x, 0, x, mapHeight);
    for(int y = 0; y < mapHeight; y += tileHeight)
        shape.line(0, y, mapWidth, y);
    shape.end();





        player.update(delta);
        spriteBatch.begin();
        spriteBatch.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
    tiledMapCell = new TiledMapTileLayer.Cell();
    spriteBatch = new SpriteBatch();
        player = new playerClass(20,20);
        map = new TmxMapLoader().load("maps/tiledmap.tmx");
        layer = (TiledMapTileLayer) map.getLayers().get(0);
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape = new ShapeRenderer();
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
}
