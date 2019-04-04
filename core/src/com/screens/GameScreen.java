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

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    GameMap gameMap;
    TextInputField textInputField;
    private Stage stage;
    private PlayerClass playerObject;

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
                    cmdsToExecute.addAction(Actions.moveBy(value * 32, 0, (float) value / 5));
                if (currentRotation == 90)
                    cmdsToExecute.addAction(Actions.moveBy(0, value * 32, (float) value / 5));
                if (currentRotation == 180)
                    cmdsToExecute.addAction(Actions.moveBy(-value * 32, 0, (float) value / 5));
                if (currentRotation == 270)
                    cmdsToExecute.addAction(Actions.moveBy(0, -value * 32, (float) value / 5));
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
        playerObject = new PlayerClass("images/player.png",32,32);
        playerObject.sprite.setOrigin(playerObject.getWidth()/2, playerObject.getHeight()/2);
        playerObject.setX(2*32);
        playerObject.setY(2*32);
        stage.addActor(playerObject);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        gameMap = new TiledGameMap();
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