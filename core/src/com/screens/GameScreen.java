package com.screens;

import com.TiledMap.GameMap;
import com.TiledMap.TiledGameMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
    MenuScreen ms;
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
    private Sound soundClick = Gdx.audio.newSound(Gdx.files.internal("button.mp3"));
    private Sound soundError = Gdx.audio.newSound(Gdx.files.internal("error.mp3"));
    private Sound soundPoint = Gdx.audio.newSound(Gdx.files.internal("point.mp3"));
    private Sound soundWin = Gdx.audio.newSound(Gdx.files.internal("win.mp3"));


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
        for (int index = 0; index < cmdArray.length; index++) {
            cmd0 = cmdArray[index].split(" ");
            if (validateCommand(cmd0)) {
                if (cmd0[0].equals("gentag")) {
                    //System.out.println("REPEAT");
                    boolean cmdIsValid = true;
                    for (int subIndex = index + 1; subIndex < cmdArray.length; subIndex++) {
                        cmd1 = cmdArray[subIndex].split(" ");
                        cmdIsValid &= validateCommand(cmd1);
                        if (cmdIsValid) {
                            if (cmd1[0].equals("slut")) {
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
            case "-gå":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "gå":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "drej":
                if (cmd.length != 2) return false;
                return cmd[1].equals("venstre") || cmd[1].equals("højre");
            case "gentag":
                return cmd.length == 2 && cmd[1].matches("^[0-9]*$");
            case "slut":
                return true;
        }
        return false;

    }


    public static SequenceAction parseCommands(String[] input, ActorClass playerObject) {
        String[] cmdSplit = input;
        SequenceAction cmdsToExecute = Actions.sequence();
        switch (cmdSplit[0]) {
            case "-gå":
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
            case "gå":
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
            case "drej":
                switch (cmdSplit[1]) {
                    case "venstre":
                        cmdsToExecute.addAction(Actions.rotateBy(90, 0.5f));
                        break;
                    case "højre":
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
        playerActor.setRotation(90);


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
                soundClick.play(1.0f);
                playerActor.setCommands(validateInput(textInputField.textArea.getText()));
                System.out.println(validateInput(textInputField.textArea.getText()));
                textInputField.textArea.setText("");

            }
        });

        textInputField1.textButton.setText("Lineal");
        textInputField1.textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                soundClick.play(1.0f);
                measure = true;
            }
        });

        //IMPORT GOALS
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
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.add().row();
            dialog.text("Du skal hente diamanten!");
            dialog.add().row();
            dialog.text("Hint: Prøv at skriv 'gå 10'");
            dialog.button("OK!", 1L);
            dialog.show(stage);

            goalActorList.get(0).setBounds(10*w,12*w, w,w);
            goalActorList.get(0).setPosition(10*w,12*w);
        } else if(level == 2){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Du kan også dreje soldaten! Prøv 'drej højre' eller 'drej venstre'");
            dialog.button("OK!", 1L);
            dialog.show(stage);

            goalActorList.get(0).setBounds(15*w,10*w, w,w);
            goalActorList.get(0).setPosition(15*w,10*w);
        }else if(level == 3){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Lad os prøve det igen!");
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(15*w,12*w, w,w);
            goalActorList.get(0).setPosition(15*w,12*w);
        }else if(level == 4){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Vi skal have alle diamanterne for at klare dette level!");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(9*w,17*w, w,w);
            goalActorList.get(0).setPosition(9*w,17*w);
            goalActorList.get(1).setBounds(19*w,17*w, w,w);
            goalActorList.get(1).setPosition(19*w,17*w);
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
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Vi skal uden om forhindringen!");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(10*w,18*w, w,w);
            goalActorList.get(0).setPosition(10*w,18*w);
        }else if(level == 6){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Lad os prøve det igen!");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(6*w,16*w, w,w);
            goalActorList.get(0).setPosition(6*w,16*w);
            goalActorList.get(1).setBounds(6*w,2*w, w,w);
            goalActorList.get(1).setPosition(6*w,2*w);
        }else if(level == 7){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Gør det nemmere ved at bruge 'Gentag'. Det gentager alt hvad du skriver nedenunder - husk at slut af med at skrive 'slut'");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(5*w,14*w, w,w);
            goalActorList.get(0).setPosition(5*w,14*w);
            goalActorList.get(1).setBounds(15*w,14*w, w,w);
            goalActorList.get(1).setPosition(15*w,14*w);
            goalActorList.get(2).setBounds(15*w,4*w, w,w);
            goalActorList.get(2).setPosition(15*w,4*w);
        }else if(level == 8){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Lad os prøve igen med 'gentag'");
            dialog.add().row();
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(3*w,18*w, w,w);
            goalActorList.get(0).setPosition(3*w,18*w);
            goalActorList.get(1).setBounds(8*w,18*w, w,w);
            goalActorList.get(1).setPosition(8*w,18*w);
            goalActorList.get(2).setBounds(13*w,18*w, w,w);
            goalActorList.get(2).setPosition(13*w,18*w);
        }else if(level == 9){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Kan du få alle diamanterne, uden at ramme forhindringerne?");
            dialog.button("OK!", 1L);
            dialog.show(stage);
            goalActorList.get(0).setBounds(8*w,6*w, w,w);
            goalActorList.get(0).setPosition(8*w,6*w);
            goalActorList.get(1).setBounds(13*w,11*w, w,w);
            goalActorList.get(1).setPosition(13*w,11*w);
            goalActorList.get(2).setBounds(18*w,16*w, w,w);
            goalActorList.get(2).setPosition(18*w,16*w);
        }else if(level == 10){
            //DIALOG BOKS
            dialog = new Dialog("Level "+level, uiSkin , "Dialog"){
                public void result(Object obj){
                    if(obj.equals(1L)){
                        soundClick.play(1.0f);
                        dialog.clear();
                        dialog.hide();
                    }
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Lad mig se hvad du har lært!");
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

        //ESCAPE MENU
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
        }


        //MEASURE BUTTON
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && measure){
            int clickCounter = 0;
            clickCounter++;
            int firstMouseY = Gdx.input.getY() / 32;
            int firstMouseX = Gdx.input.getX() / 32;
            firstMouseY = Gdx.graphics.getHeight()/32-1 - firstMouseY;
            int playerX = (int)playerActor.getX()/32;
            int playerY = (int)playerActor.getY()/32;
            dialog = new Dialog("Lineal", uiSkin , "Dialog"){
                public void result(Object obj){
                    soundClick.play(1.0f);
                }
            };
            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Din position: " + playerX + "," + playerY);
            dialog.add().row();
            if(goalActorList.size()>0) {
                dialog.text(" Første diamant: " + (int)goalActorList.get(0).getX()/w + "," + (int)goalActorList.get(0).getY()/w);
                if(goalActorList.size()>1){
                    dialog.text(" Anden diamant: " + (int)goalActorList.get(1).getX()/w + "," + (int)goalActorList.get(1).getY()/w);
                    if(goalActorList.size()>2){
                        dialog.text(" Tredje diamant: " + (int)goalActorList.get(2).getX()/w + "," + (int)goalActorList.get(2).getY()/w);
                        if(goalActorList.size()>3){
                            dialog.text(" Fjerde diamant: " + (int)goalActorList.get(3).getX()/w + "," + (int)goalActorList.get(3).getY()/w);
                        }
                    }
                }

            }
            dialog.button("OK!", true);
            dialog.show(stage);
            measure = false;
        }

        playerActor.rectangle.set(playerActor.getX(), playerActor.getY(), playerActor.sprite.getWidth() / 2, playerActor.getHeight() / 2);

        if (playerActor.getActions().size > 0) {
            textInputField.textButton.setText("Kalkulerer....");
            lvlBegun = true;
        } else {
            textInputField.textButton.setText("KØR!");
        }


        //IF NOT COMPLETED
        /*if (lvlBegun == true && playerActor.getActions().size <= 0 && amountGoals >= 1){
             worldGenerator = new WorldGenerator(level);
            //System.out.println("RESTART GAME HERE!");
        }*/


        //COLLISION
        if(playerActor.getX()/w<19 && playerActor.getX()/w>0 && playerActor.getY()/w<19 && playerActor.getY()/w>0) {

            if (!gameMap.getTileTypeByCoordinate(1, (int) playerActor.getX() / w, (int) playerActor.getY() / w).isCollidable()) {
                playerActor.cmdList.clear();
                playerActor.cmdListRepeat.clear();
                //cmdsToExecute.reset();
                playerActor.getActions().clear();
                playerActor.setX(18 * w);
                playerActor.setY(18 * w);

                soundError.play(1.0f);

                System.out.println("COLLIDE");
                dialog = new Dialog("ADVARSEL", uiSkin, "Dialog") {
                    public void result(Object obj) {

                        //System.out.println("result " + obj);
                        if (obj.equals(1L)) {
                            soundClick.play(1.0f);
                            goalActorList.clear();
                            worldGenerator = new WorldGenerator(level);
                        } else if (obj.equals(2L)) {
                            soundClick.play(1.0f);
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                        }
                    }
                };

                dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
                dialog.text("Hovsa... Lad os prøve igen!");
                dialog.button("JA!", 1L);
                dialog.button("NEJ!", 2L);
                dialog.show(stage);

            }
        } else {
            playerActor.cmdList.clear();
            playerActor.cmdListRepeat.clear();
            //cmdsToExecute.reset();
            playerActor.getActions().clear();
            playerActor.setX(5 * w);
            playerActor.setY(5 * w);

            soundError.play(1.0f);

            dialog = new Dialog("ADVARSEL", uiSkin, "Dialog") {
                public void result(Object obj) {

                    //System.out.println("result " + obj);
                    if (obj.equals(1L)) {
                        soundClick.play(1.0f);
                        goalActorList.clear();
                        worldGenerator = new WorldGenerator(level);
                    } else if (obj.equals(2L)) {
                        soundClick.play(1.0f);
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                    }
                }
            };

            dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
            dialog.text("Hovsa... Lad os prøve igen!");
            dialog.button("JA!", 1L);
            dialog.button("NEJ!", 2L);
            dialog.show(stage);

        }

        //}


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
                soundPoint.play(1.0f);
                actorGoal.remove();
                iterator.remove();
                amountGoals = amountGoals - 1;

                dialog = new Dialog("Level #"+level+" er klaret!", uiSkin , "Dialog"){
                    public void result(Object obj){
                        //System.out.println("result " + obj);
                        if (obj.equals(1L)){
                            soundClick.play(1.0f);
                            int newLvl = level + 1;
                            goalActorList.clear();
                            worldGenerator = new WorldGenerator(newLvl);
                        }else if (obj.equals(2L)){
                            soundClick.play(1.0f);
                            goalActorList.clear();
                            worldGenerator = new WorldGenerator(level);
                        } else if (obj.equals(3L)){
                            soundClick.play(1.0f);
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                        }
                    }
                };

                if (amountGoals == 0) {
                    playerActor.cmdList.clear();
                    playerActor.cmdListRepeat.clear();
                    playerActor.getActions().clear();

                    soundWin.play(1.0f);
                    dialog.text("Du vandt! Vil du fortsætte?");
                    dialog.getStyle().titleFont.getData().setScale(0.5f,0.5f);
                    dialog.button("Næste udfordring!", 1L);
                    dialog.button("Prøv igen", 2L);
                    dialog.button("Nej tak", 3L);
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
        soundClick.dispose();
        soundError.dispose();
        soundPoint.dispose();
        soundWin.dispose();
        spriteBatch.dispose();

        gameMap.dispose();

    }
}