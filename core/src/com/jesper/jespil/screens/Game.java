package com.jesper.jespil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gameObjects.playerClass;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game implements Screen {

    private OrthographicCamera camera;
    GameMap gameMap;
    TextInputField textInputField;
    Texture texture;
    Sprite sprite;


    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Vector2 movement = new Vector2();
    Vector2 touch = new Vector2();
    Vector2 dir = new Vector2();
    private LinkedList<CommandsToExec> stackOfCommands = new LinkedList<CommandsToExec>();


    Vector3 temp = new Vector3();

    float speed = 100;

    private playerClass player;
    SpriteBatch spriteBatch;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("images/player.png"));
        sprite = new Sprite(texture);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        //player = new playerClass(1, 1);
        gameMap = new TiledGameMap();
        textInputField = new TextInputField();
        sprite.setSize(32, 32);
        sprite.setX(2 * 32);
        sprite.setY(2 * 32);
        touch.set(sprite.getX(), sprite.getY());


        textInputField.textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {

                float startPosX;
                float startPosY;

                float endPosX = sprite.getX();
                float endPosY = sprite.getY();

                for (String line : textInputField.textArea.getText().split("\\n")) {
                    System.out.println(line);

                    textInputField.textButton.setText("Compiling!");

                    if (line.startsWith("walk(") && line.endsWith(");")) {

                        line = line.replace("walk(", "");
                        line = line.replace(");", "");
                        System.out.println(line);

                        int i = Integer.parseInt(line);
                        startPosX = endPosX;
                        startPosY = endPosY;
                        endPosX = startPosX + i * 32;
                        endPosY = startPosY + i * 32;


                        stackOfCommands.add(new CommandsToExec(startPosX, startPosY, endPosX, endPosY));
                    }

                }



            }
        });

    }

    /*StringBuilder sb = new StringBuilder();
                String textStr = textInputField.textArea.getText();
                boolean found = false;

                for (char c : textStr.toCharArray()){
                    if(Character.isDigit(c)){
                        sb.append(c);
                        found=true;
                        if(textStr.equals("walk "+c)){
                            touch.set(0,0);
                            System.out.println(c);
                        } else if (found){
                            break;
                        }
                    }

                }*/

       /* textInputField.textArea.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (textInputField.textArea.getText().equals("gå")){
                    touch.set(10*32,10*32);
                }
            }
        });*/

/*
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                camera.unproject(temp.set(screenX, screenY, 0));
                touch.set(temp.x, temp.y);
                return true;
            }
        });

        */


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameMap.render(camera);
        spriteBatch.begin();
        //spriteBatch.draw(player.getPlayer(), player.getPosition().x*32, player.getPosition().y*32, w, w);
        sprite.draw(spriteBatch);
        spriteBatch.end();

        //player.update(delta);
        textInputField.render(delta);

        if (!stackOfCommands.isEmpty()) {
            CommandsToExec cte = stackOfCommands.get(0);
            touch.set(cte.getDestPosX(), cte.getDestPosY());
            cte.setExecuted(true);

            if (sprite.getX() == cte.getDestPosX() && sprite.getY() == cte.getDestPosY()) {
                stackOfCommands.removeFirst();
            }
        }
        position.set(sprite.getX(), sprite.getY());
        dir.set(touch).sub(position).nor();
        velocity.set(dir).scl(speed);
        movement.set(velocity).scl(delta);
        if (position.dst2(touch) > movement.len2()) {
            position.add(movement);
        } else {
            position.set(touch);
        }
        sprite.setX(position.x);
        sprite.setY(position.y);


        if (Gdx.input.justTouched()) {
            Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            {
                TileType type = gameMap.getTileTypeByLocation(1, pos.x, pos.y);


                if (type != null) {
                    System.out.println(position.x + "," + position.y);
                    System.out.println("you clicked the tile with id " + type.getId() + " " + type.getName() + " " + type.isCollidable() + " " + type.getDamage());
                }
            }
        }
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
        dispose();
    }

    @Override
    public void dispose() {

    }

}

/*


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

*/