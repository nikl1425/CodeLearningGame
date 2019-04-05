package com.screens;

import com.TiledMap.GameMap;
import com.TiledMap.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gameObjects.PlayerClass;
import com.gameObjects.TextInputField;
import com.gameObjects.WorldGenerator;
import com.gameObjects.goalClass;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {

    GameMap gameMap;
    String tiledGameMap;
    TextInputField textInputField;
    WorldGenerator worldGenerator;


    private List<goalClass> goalClassList = new ArrayList<goalClass>();
    private OrthographicCamera camera;
    private Stage stage;
    private PlayerClass playerObject;
    private goalClass goalObject;

    private static int w = 32;

    int playerX;
    int playerY;
    //int goalX;
    //int goalY;
    int level;
    int amountGoals;



    public GameScreen(int playerX, int playerY, int amountGoals, int level, String tiledGameMap){
        this.playerX = playerX*w;
        this.playerY = playerY*w;
        //this.goalX = goalX*w;
        //this.goalY = goalY*w;
        this.tiledGameMap = tiledGameMap;
        this.level = level;
        this.amountGoals = amountGoals;

    }


    public static SequenceAction parseCommands(String input, PlayerClass playerObject) {
        SequenceAction cmdsToExecute = Actions.sequence();
        String[] cmdSplit = input.split(" ");
        switch (cmdSplit[0]) {
            case "step":
                float currentRotation = playerObject.getRotation();
                int cycles = (int) (currentRotation / 360);
                currentRotation = currentRotation - Math.signum(currentRotation) * 360 * cycles;
                currentRotation = (currentRotation + 360) % 360;
                currentRotation  = Math.round(currentRotation);
                int value = Integer.parseInt(cmdSplit[1]);
                if (currentRotation == 0)
                    cmdsToExecute.addAction(Actions.moveBy(value * w, 0, (float) value / 5));
                if (currentRotation == 90)
                    cmdsToExecute.addAction(Actions.moveBy(0, value * w, (float) value / 5));
                if (currentRotation == 180)
                    cmdsToExecute.addAction(Actions.moveBy(-value * w, 0, (float) value / 5));
                if (currentRotation == 270)
                    cmdsToExecute.addAction(Actions.moveBy(0, -value * w, (float) value / 5));
                break;
            case "turn":
                switch(cmdSplit[1]) {
                    case "left":
                        cmdsToExecute.addAction(Actions.rotateBy(90,0.5f));
                        break;
                    case "right":
                        cmdsToExecute.addAction(Actions.rotateBy(-90,0.5f));
                        break; }
                break;
            //cmdsToExecute.addAction(Actions.delay(1));
            case "repeat":
                int value2 = Integer.parseInt(cmdSplit[1]);
                playerObject.repeatCommands(value2);
                PlayerClass.sequenceAction.getActions();
                cmdsToExecute.addAction(Actions.repeat(value2, PlayerClass.sequenceAction));
                break;
        }
        //cmdsToExecute.addAction(Actions.delay(1));
        return cmdsToExecute;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        playerObject = new PlayerClass("images/player.png",w,w);
        playerObject.sprite.setOrigin(playerObject.getWidth()/2, playerObject.getHeight()/2);
        playerObject.setX(playerX);
        playerObject.setY(playerY);
        stage.addActor(playerObject);

        for (int i = 0; i < amountGoals; i++) {
            //goalObject = new goalClass("images/diamond.png",w,w);
            goalClassList.add(new goalClass("images/diamond.png", w,w));
            goalClassList.get(i).sprite.setOrigin(goalClassList.get(i).getWidth()/2,goalClassList.get(i).getHeight()/2);
            //goalObject.setX(goalY);
            //goalObject.setY(goalY);
            stage.addActor(goalClassList.get(i));
        }


        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        gameMap = new TiledGameMap(tiledGameMap);
        //gameMap = new TiledGameMap(gameMap);

        textInputField = new TextInputField();
        textInputField.textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                String[] cmds = textInputField.textArea.getText().split("\\n");
                playerObject.setCommands(cmds);
                textInputField.textArea.setText("");
            }
        });

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameMap.render(camera);
        stage.act(delta);
        stage.draw();
        textInputField.render(delta);

        playerObject.playerRect.set(playerObject.getX(),playerObject.getY(),playerObject.sprite.getWidth(),playerObject.getHeight());

        for (int i = 0; i < amountGoals; i++) {
            goalClassList.get(i).goalRect.set(goalClassList.get(i).getX(),goalClassList.get(i).getY(),goalClassList.get(i).sprite.getWidth(),goalClassList.get(i).sprite.getHeight());

            if (playerObject.playerRect.overlaps(goalClassList.get(i).goalRect)){
                if(goalClassList.size()>0){
                    //goalClassList.get(i).remove();
                    goalClassList.remove(i);
                    System.out.println(goalClassList.size());
                    if(goalClassList.size()<=0){
                        int newLvl = level + 1;
                        goalClassList.clear();
                        worldGenerator = new WorldGenerator(newLvl);

                    }
                } /*else {
                    try {
                        int newLvl = level + 1;
                        worldGenerator = new WorldGenerator(newLvl);
                    } catch (NullPointerException e) {
                        System.err.print("dang");
                    }
                }*/

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
        stage.dispose();
    }
}