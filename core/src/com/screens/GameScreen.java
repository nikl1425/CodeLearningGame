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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gameObjects.ActorClass;
import com.gameObjects.TextInputField;
import com.gameObjects.WorldGenerator;

import java.util.*;

//import com.gameObjects.goalClass;

public class GameScreen implements Screen {

    GameMap gameMap;
    String tiledGameMap;
    TextInputField textInputField;
    WorldGenerator worldGenerator;
    private OrthographicCamera camera;
    private Stage stage;
    private ActorClass playerActor;
    private List<ActorClass> goalActorList = new ArrayList<>();
    private static int w = 32;
    int playerX;
    int playerY;
    int level;
    int amountGoals;
    int x, y;
    int tileSize = 32;


    public ArrayList<String[]> validateInput(String input) {
        ArrayList<String[]> cmds = new ArrayList<>();
        String[] cmdArray = input.split("\\n");
        String[] cmd0, cmd1;
        int repeatEndIndex = 0;
        for (int index = 0; index < cmdArray.length; index++) {
            cmd0 = cmdArray[index].split(" ");
            if (validateCommand(cmd0)) {
                if (cmd0[0].equals("repeat")) {
                    System.out.println("REPEAT");
                    boolean cmdIsValid = true;
                    for (int subIndex = index + 1; subIndex < cmdArray.length; subIndex++) {
                        cmd1 = cmdArray[subIndex].split(" ");
                        cmdIsValid &= validateCommand(cmd1);
                        if (cmdIsValid) {
                            if (cmd1[0].equals("break")) {
                                System.out.println("BREAK");
                                repeatEndIndex = subIndex;
                                break;
                            }
                        } else {
                            cmds.clear();
                            return cmds;
                        }
                    }
                    int amountOfRepeats = Integer.parseInt(cmd0[1]);
                    for (int i = 0; i < amountOfRepeats; i++) {
                        for (int subIndex = index + 1; subIndex < repeatEndIndex; subIndex++) {
                            cmds.add(cmdArray[subIndex].split(" "));
                        }
                    }
                    index += repeatEndIndex;
                } else cmds.add(cmd0);
            } else {
                cmds.clear();
                return cmds;
            }
        }
        return cmds;
    }

    public boolean validateCommand(String[] cmd) {
        if (cmd == null || cmd.length == 0) return false;
        switch (cmd[0]) {
            case "step":
                return cmd.length == 2 && cmd[1].matches("\\d");
            case "turn":
                if (cmd.length != 2) return false;
                return cmd[1].equals("left") || cmd[1].equals("right");
            case "repeat":
                return cmd.length == 2 && cmd[1].matches("\\d");
            case "break":
                return true;
        }
        return false;
    }


    public static SequenceAction parseCommands(String[] input, ActorClass playerObject) {
        SequenceAction cmdsToExecute = Actions.sequence();
        String[] cmdSplit = input;
        switch (cmdSplit[0]) {
            case "step":
                float currentRotation = playerObject.getRotation();
                int cycles = (int) (currentRotation / 360);
                currentRotation = currentRotation - Math.signum(currentRotation) * 360 * cycles;
                currentRotation = (currentRotation + 360) % 360;
                currentRotation = Math.round(currentRotation);
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
                switch (cmdSplit[1]) {
                    case "left":
                        cmdsToExecute.addAction(Actions.rotateBy(90, 0.5f));
                        break;
                    case "right":
                        cmdsToExecute.addAction(Actions.rotateBy(-90, 0.5f));
                        break;
                }
                break;
            //cmdsToExecute.addAction(Actions.delay(1));
        }
        return cmdsToExecute;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.graphics.getDisplayMode();
        Gdx.input.setInputProcessor(stage);
        playerActor = new ActorClass("images/player.png", w, w);
        playerActor.sprite.setOrigin(playerActor.getWidth() / 2, playerActor.getHeight() / 2);
        playerActor.setX(playerX);
        playerActor.setY(playerY);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        gameMap = new TiledGameMap(tiledGameMap);
        textInputField = new TextInputField(640, 0, 240, 60, 240, 640);
        stage.addActor(playerActor);
        stage.addActor(textInputField.textArea);
        stage.addActor(textInputField.textButton);


        textInputField.textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                playerActor.setCommands(validateInput(textInputField.textArea.getText()));
                textInputField.textArea.setText("");
            }
        });


        for (int i = 0; i < amountGoals; i++) {
            goalActorList.add(new ActorClass("images/diamond.png", w, w));
            stage.addActor(goalActorList.get(i));
        }

        for (ActorClass actorClass : goalActorList) {
            Random r = new Random();
            x = (r.nextInt(5) * tileSize);
            y = (r.nextInt(5) * tileSize);
            actorClass.setBounds(x, y, actorClass.getWidth(), actorClass.getHeight());
            actorClass.sprite.setPosition(x, y);
        }


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameMap.render(camera);
        stage.act(delta);
        stage.draw();

        playerActor.rectangle.set(playerActor.getX(), playerActor.getY(), playerActor.sprite.getWidth() / 2, playerActor.getHeight() / 2);

        if (playerActor.getActions().size > 0) {
            textInputField.textButton.setText("Compiling!");
        } else {
            textInputField.textButton.setText("Run!");
        }


        for (int i = 0; i < amountGoals; i++) {
            goalActorList.get(i).sprite.setOrigin(goalActorList.get(i).getWidth() / 2, goalActorList.get(i).getHeight() / 2);
            System.out.println(amountGoals);

        }
        for (Iterator<ActorClass> iterator = goalActorList.iterator(); iterator.hasNext(); ) {
            ActorClass actorGoal = iterator.next();
            float goalX = actorGoal.getX();
            float goalY = actorGoal.getY();
            float goalWidth = actorGoal.sprite.getWidth();
            float goalHeight = actorGoal.sprite.getHeight();
            actorGoal.rectangle.set(goalX, goalY, goalWidth, goalHeight);
            if (actorGoal.rectangle.overlaps(playerActor.rectangle)) {
                actorGoal.remove();
                iterator.remove();
                amountGoals = amountGoals - 1;


                if (amountGoals < 1) {
                    int newLvl = level + 1;
                    goalActorList.clear();

                    worldGenerator = new WorldGenerator(newLvl);
                }
            }
        }

    }

    public GameScreen(int playerX, int playerY, int amountGoals, int level, String tiledGameMap) {
        this.playerX = playerX * w;
        this.playerY = playerY * w;
        this.tiledGameMap = tiledGameMap;
        this.level = level;
        this.amountGoals = amountGoals;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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