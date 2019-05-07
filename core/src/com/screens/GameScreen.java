package com.screens;
import com.TiledMap.GameMap;
import com.TiledMap.TiledGameMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gameObjects.ActorClass;
import com.gameObjects.TextInputField;
import com.gameObjects.WorldGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

//import com.gameObjects.goalClass;

public class GameScreen implements Screen {
    private GameMap gameMap;
    //private TiledGameMap gameMap;
    private TiledMap gm;
    private String tiledGameMap;
    private TextInputField textInputField;
    private TextInputField textInputField1;
    private WorldGenerator worldGenerator;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Stage stage;
    private ActorClass playerActor;
    private List<ActorClass> goalActorList = new ArrayList<>();
    private static int w = 32;
    private int playerX;
    private int playerY;
    private int level;
    private int amountGoals;
    private boolean lvlBegun;
    private int commandCounter;
    private boolean measure;
    private Dialog dialog;
    Skin uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));


    public GameScreen(int playerX, int playerY, int amountGoals, int level, String tiledGameMap) {
        this.playerX = playerX * w;
        this.playerY = playerY * w;
        this.tiledGameMap = tiledGameMap;
        this.level = level;
        this.amountGoals = amountGoals;
    }


    public ArrayList<String[]> validateInput(String input) {
        ArrayList<String[]> cmds = new ArrayList<>();
        String[] cmdArray = input.split("\\n");
        String[] cmd0, cmd1;

        int repeatEndIndex = 0;
        System.out.println("niels");
        for (int index = 0; index < cmdArray.length; index++) {
            cmd0 = cmdArray[index].split(" ");
            if (validateCommand(cmd0)) {
                if (cmd0[0].equals("repeat")) {
                    //System.out.println("REPEAT");
                    boolean cmdIsValid = true;
                    for (int subIndex = index + 1; subIndex < cmdArray.length; subIndex++) {
                        cmd1 = cmdArray[subIndex].split(" ");
                        cmdIsValid &= validateCommand(cmd1);
                        if (cmdIsValid) {
                            if (cmd1[0].equals("break")) {
                                //System.out.println("BREAK");
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
        commandCounter++;
        if (cmd == null || cmd.length == 0) return false;
        switch (cmd[0]) {
            case "-step":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "step":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "turn":
                if (cmd.length != 2) return false;
                return cmd[1].equals("left") || cmd[1].equals("right");
            case "repeat":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "break":
                return true;
        }
        return false;

    }


    public static SequenceAction parseCommands(String[] input, ActorClass playerObject) {
        String[] cmdSplit = input;
        SequenceAction cmdsToExecute = Actions.sequence();
        switch (cmdSplit[0]) {
            case "-step":
                float currentRotation1 = playerObject.getRotation();
                int cycles1 = (int) (currentRotation1 / 360);
                currentRotation1 = currentRotation1 - Math.signum(currentRotation1) * 360 * cycles1;
                currentRotation1 = (currentRotation1 + 360) % 360;
                currentRotation1 = Math.round(currentRotation1);
                int value1 = Integer.parseInt(cmdSplit[1]);
                if (currentRotation1 == 0)
                    cmdsToExecute.addAction(Actions.moveBy(-value1 * w, 0, (float) value1 / 5));
                if (currentRotation1 == 90)
                    cmdsToExecute.addAction(Actions.moveBy(0, -value1 * w, (float) value1 / 5));
                if (currentRotation1 == 180)
                    cmdsToExecute.addAction(Actions.moveBy(value1 * w, 0, (float) value1 / 5));
                if (currentRotation1 == 270)
                    cmdsToExecute.addAction(Actions.moveBy(0, value1 * w, (float) value1 / 5));
                break;
            case "step":
                float currentRotation = playerObject.getRotation();
                int cycles = (int) (currentRotation / 360);
                currentRotation = currentRotation - Math.signum(currentRotation) * 360 * cycles;
                currentRotation = (currentRotation + 360) % 360;
                currentRotation = Math.round(currentRotation);
                int value = Integer.parseInt(cmdSplit[1]);
                System.out.println(value);
                //int value = Integer.parseInt(cmdSplit[1]);
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
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        FitViewport viewp = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewp, spriteBatch);

        Gdx.graphics.getDisplayMode();
        Gdx.input.setInputProcessor(stage);
        playerActor = new ActorClass("images/player.png", w, w);
        playerActor.sprite.setOrigin(playerActor.getWidth() / 2, playerActor.getHeight() / 2);
        playerActor.setX(playerX);
        playerActor.setY(playerY);

        gameMap = new TiledGameMap(tiledGameMap);

        textInputField = new TextInputField(640, 0, 240, 60, 240, 640);
        textInputField1 = new TextInputField(640,60,240,60,240,120);

        stage.addActor(gameMap);
        stage.addActor(playerActor);
        stage.addActor(textInputField.textArea);
        stage.addActor(textInputField.textButton);
        //stage.addActor(textInputField1.textArea);
        stage.addActor(textInputField1.textButton);
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn(1f));


        textInputField.textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                playerActor.setCommands(validateInput(textInputField.textArea.getText()));
                System.out.println(validateInput(textInputField.textArea.getText()));
                textInputField.textArea.setText("");
                //System.out.println(lvlBegun);
            }
        });
        textInputField1.textButton.setText("Measure!");

        textInputField1.textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                measure = true;
            }
        });


        for (int i = 0; i < amountGoals; i++) {
            goalActorList.add(new ActorClass("images/diamond.png", w, w));
            stage.addActor(goalActorList.get(i));

        }
        //LEVEL COMMANDER
        //SET GOAL POSITIONS
        //SET HELPING DEVICES AT THE BEGINNING OF EACH NEW LEVEL
        //LEVEL 1 - 4: 1 GOAL
        //LEVEL 5-8: 2 GOALS
        //LEVEL 9-10: 4 GOALS
        //
        if(level == 1){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Can you fix the code on the right?");
            dialog.add().row();
            dialog.text("Make the soldier get the diamond!");
            dialog.add().row();
            dialog.text("Hint: Type in 'Step 10'");
            dialog.button("OK!", 1L);
            dialog.show(stage);

            goalActorList.get(0).setBounds(12*w,2*w, w,w);
            goalActorList.get(0).setPosition(12*w,2*w);
        } else if(level == 2){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Try add some more digits");
            dialog.button("OK!", 1L);
            dialog.show(stage);

            goalActorList.get(0).setBounds(18*w,2*w, w,w);
            goalActorList.get(0).setPosition(18*w,2*w);
        }else if(level == 3){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("You can turn the soldier by using 'turn left' or 'turn right'");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(2*w,12*w, w,w);
            goalActorList.get(0).setPosition(2*w,12*w);
        }else if(level == 4){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Lets try that again!");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(2*w,2*w, w,w);
            goalActorList.get(0).setPosition(2*w,2*w);
        } else if(level == 5){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("You need to get all the diamonds!");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(8*w,8*w, w,w);
            goalActorList.get(0).setPosition(8*w,8*w);
            goalActorList.get(1).setBounds(4*w,2*w, w,w);
            goalActorList.get(1).setPosition(4*w,2*w);
        }else if(level == 6){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Let's try that again");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(12*w,6*w, w,w);
            goalActorList.get(0).setPosition(12*w,6*w);
            goalActorList.get(1).setBounds(6*w,2*w, w,w);
            goalActorList.get(1).setPosition(6*w,2*w);
        }else if(level == 7){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("We have to avoid the water - the soldier can't swim");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(4*w,18*w, w,w);
            goalActorList.get(0).setPosition(8*w,18*w);
            goalActorList.get(1).setBounds(17*w,16*w, w,w);
            goalActorList.get(1).setPosition(17*w,16*w);
        }else if(level == 8){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Let's try that again");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(8*w,2*w, w,w);
            goalActorList.get(0).setPosition(8*w,8*w);
            goalActorList.get(1).setBounds(18*w,19*w, w,w);
            goalActorList.get(1).setPosition(18*w,19*w);
        }else if(level == 9){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Collect all the diamonds");
            dialog.add().row();
            dialog.text("Use the 'repeat', which will repeat all the actions untill you type 'break'");
            dialog.text("Hint: Type 'repeat 4', 'step 2', 'break' and the soldier will walk 8 steps");
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(11*w,2*w, w,w);
            goalActorList.get(0).setPosition(11*w,2*w);
            goalActorList.get(1).setBounds(11*w,12*w, w,w);
            goalActorList.get(1).setPosition(11*w,12*w);
            goalActorList.get(2).setBounds(1*w,12*w, w,w);
            goalActorList.get(2).setPosition(1*w,12*w);
        }else if(level == 10){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.text("Let me see what you got");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(18*w,6*w, w,w);
            goalActorList.get(0).setPosition(18*w,6*w);
            goalActorList.get(1).setBounds(4*w,19*w, w,w);
            goalActorList.get(1).setPosition(4*w,19*w);
            goalActorList.get(2).setBounds(1*w,1*w, w,w);
            goalActorList.get(2).setPosition(1*w,1*w);
            goalActorList.get(3).setBounds(16*w,16*w, w,w);
            goalActorList.get(3).setPosition(16*w,16*w);
        }


        /*for (ActorClass actorClass : goalActorList) {
            Random r = new Random();
            int tileSize = 32;
            int x = (r.nextInt(5) * tileSize);
            int y = (r.nextInt(5) * tileSize);
            actorClass.setBounds(x, y, actorClass.getWidth(), actorClass.getHeight());
            actorClass.sprite.setPosition(x, y);
        }*/

        /*for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(" , " +  gameMap.getTileTypeByCoordinate(1,j,i));
            }
            System.out.println(" ");
        }*/

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameMap.render(camera);
        gameMap.update(delta);
        camera.update();
        stage.act(delta);
        stage.draw();

        //MEASURE BUTTON
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && measure){
            int clickCounter = 0;
            clickCounter++;
            int firstMouseY = Gdx.input.getY() / 32;
            int firstMouseX = Gdx.input.getX() / 32;
            firstMouseY = Gdx.graphics.getHeight()/32-1 - firstMouseY;
            int playerX = (int)playerActor.getX()/32;
            int playerY = (int)playerActor.getY()/32;
            dialog = new Dialog("Measure", uiSkin , "Dialog"){
                public void result(Object obj){
                }
            };
            dialog.text("Your position: " + playerX + "," + playerY);
            dialog.add().row();
            dialog.text("Target position: " + firstMouseX + "," + firstMouseY);
            dialog.button("Ok!", true);
            dialog.show(stage);
            measure = false;
        }

        playerActor.rectangle.set(playerActor.getX(), playerActor.getY(), playerActor.sprite.getWidth() / 2, playerActor.getHeight() / 2);

        if (playerActor.getActions().size > 0) {
            textInputField.textButton.setText("Compiling!");
            lvlBegun = true;
        } else {
            textInputField.textButton.setText("Run!");
        }

        /*if (lvlBegun == true && playerActor.getActions().size <= 0 && amountGoals >= 1){
             worldGenerator = new WorldGenerator(level);
            //System.out.println("RESTART GAME HERE!");
        }*/


        //COLLISION

        if(!gameMap.getTileTypeByCoordinate(1,(int)playerActor.getX()/w,(int)playerActor.getY()/w).isCollidable()){
            playerActor.cmdList.clear();
            playerActor.cmdListRepeat.clear();
            //cmdsToExecute.reset();
            playerActor.getActions().clear();
            playerActor.setX(18*w);
            playerActor.setY(18*w);

            System.out.println("COLLIDE");
            dialog = new Dialog("Warning", uiSkin , "Dialog"){
                public void result(Object obj){

                    //System.out.println("result " + obj);
                    if (obj.equals(1L)){
                        goalActorList.clear();
                        worldGenerator = new WorldGenerator(level);
                    }else if (obj.equals(2L)){
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                    }
                }
            };

                dialog.text("Dude! You fell in the water! Try again.");
                dialog.button("Lets go!", 1L);
                dialog.button("Nope!", 2L);
                dialog.show(stage);

            }


        //SET GOALS
        for (int i = 0; i < amountGoals; i++) {
            goalActorList.get(i).sprite.setOrigin(goalActorList.get(i).getWidth() / 2, goalActorList.get(i).getHeight() / 2);
            //System.out.println(amountGoals);

        }

        //WHEN PLAYER GET GOAL
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

                dialog = new Dialog("Challenge #"+level+" completed!", uiSkin , "Dialog"){
                    public void result(Object obj){
                        //System.out.println("result " + obj);
                        if (obj.equals(1L)){
                            int newLvl = level + 1;
                            goalActorList.clear();
                            worldGenerator = new WorldGenerator(newLvl);
                        }else if (obj.equals(2L)){
                            goalActorList.clear();
                            worldGenerator = new WorldGenerator(level);
                        } else if (obj.equals(3L)){
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                        }
                    }
                };

                if (amountGoals == 0) {
                    dialog.text("You have won - Maximum points awarded! Do you want to continue?");
                    dialog.button("Next challenge!", 1L);
                    dialog.button("Replay", 2L);
                    dialog.button("No thanks", 3L);
                    dialog.show(stage);
                }



            }
        }

        switch (commandCounter){
            case 2:
                int amountofPoints = 3;
                break;
            case 3:
                amountofPoints = 2;
                break;
            case 4:
                amountofPoints = 1;
                break;
        }

        //System.out.println(amountofPoints);
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