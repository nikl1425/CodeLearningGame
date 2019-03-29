package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
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
private SpriteBatch spriteBatch;
private ShapeRenderer shape;
GameMap gameMap;





GameMap gameMap;





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
TiledMapTileLayer.Cell tiledMapCell;
TiledMapTileLayer layer;





    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.update();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameMap = new TiledGameMap();



        /*
        player = new playerClass(50,50);

        shape = new ShapeRenderer();
        */
    }




        /*
        player = new playerClass(50,50);

        shape = new ShapeRenderer();
        */


@Override
    public void render(float delta) {
    Gdx.gl.glClearColor(0,0,0,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


   /* if (Gdx.input.isTouched()){
        camera.translate(-Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
        camera.update();
    }*/

    if (Gdx.input.justTouched()){
        Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        TileType type = gameMap.getTileTypeByLocation(1,pos.x/32,pos.y/32);
        System.out.println(pos.x + "," + pos.y);

        if (type != null){
            System.out.println("You clicked on tile with id "+ type.getId() + " " + type.getName() + " " + type.isCollidable());
        }

    }



    gameMap.render(camera);







        /*renderer.setView(camera);
        renderer.render();*/
/*
        shape.setColor(Color.RED);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(x,y,width,height);
        shape.end();
         player.update(delta);
         spriteBatch.begin();
         spriteBatch.draw(player.getPlayer(), player.getPosition().x, player.getPosition().y);
         spriteBatch.end();
*/






    }

    @Override
    public void resize(int width, int height) {
    }




    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.update();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameMap = new TiledGameMap();
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
        //dispose();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        //shape.dispose();

    }

    private void test()
    {
        System.out.println("test!");
    }


/*
        MapLayers mapLayers = map.getLayers();
        renderer = new OrthogonalTiledMapRenderer(map);
 map = new TmxMapLoader().load("maps/tiledmap.tmx");
 terrainLayer = (TiledMapTileLayer) mapLayers.get("grass");
  private TiledMap map;
    TiledMapTileLayer terrainLayer;


    int tileWidth = map.getProperties().get("tilewidth", Integer.class), tileHeight = map.getProperties().get("tileheight", Integer.class);
    int mapWidth = map.getProperties().get("width", Integer.class) * tileWidth, mapHeight = map.getProperties().get("height", Integer.class) * tileHeight;

    shape.setProjectionMatrix(camera.combined);
    shape.begin(ShapeRenderer.ShapeType.Line);
    for(int x = 0; x < mapWidth; x += tileWidth)
        shape.line(x, 0, x, mapHeight);

    for(int y = 0; y < mapHeight; y += tileHeight)
        shape.line(0, y, mapWidth, y);
    shape.end();





    if(Gdx.input.isTouched()){
        Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(click);
        TiledMapTileLayer.Cell clicked = terrainLayer.getCell((int)click.x/32, (int)click.y/32);
        //clicked.getTile();
        System.out.println(clicked.getTile());
    }





 */