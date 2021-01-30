package com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gameObjects.ImageFileClass;
import com.mygdx.game.MyGdxGame;

public class SettingScreen implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private CheckBox vSyncCheckBox;
    private TextButton back;
    private TextField levelDirectoryInput;
    private TextureAtlas atlas;
    ImageFileClass ImageFileClass = new ImageFileClass();
    private SpriteBatch batch;
    public Sound soundClick = Gdx.audio.newSound(Gdx.files.internal("button.mp3"));

    public static FileHandle levelDirectory() {
        String prefsDir = Gdx.app.getPreferences(MyGdxGame.TITLE).getString("leveldirectory").trim();
        if (prefsDir != null && !prefsDir.equals("")){
            return Gdx.files.absolute(prefsDir);
        } else {
            return Gdx.files.absolute(Gdx.files.external(MyGdxGame.TITLE + "/levels").file().getAbsolutePath());
        }

    }

    public static boolean vSync(){
        return Gdx.app.getPreferences(MyGdxGame.TITLE).getBoolean("vsync");
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ClickListener buttonHandler = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundClick.play(1.0f);
                // event.getListenerActor() returns the source of the event, e.g. a button that was clicked
                if(event.getListenerActor() == vSyncCheckBox) {
                    // save vSync
                    Gdx.app.getPreferences(MyGdxGame.TITLE).putBoolean("vsync", vSyncCheckBox.isChecked());

                    // set vSync
                    Gdx.graphics.setVSync(vSync());

                    Gdx.app.log(MyGdxGame.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));
                } else if(event.getListenerActor() == back) {
                    // save level directory
                    String actualLevelDirectory = levelDirectoryInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() + MyGdxGame.TITLE + "/levels" : levelDirectoryInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
                    Gdx.app.getPreferences(MyGdxGame.TITLE).putString("leveldirectory", actualLevelDirectory);

                    // save the settingScreen to preferences file (Preferences#flush() writes the preferences in memory to the file)
                    Gdx.app.getPreferences(MyGdxGame.TITLE).flush();

                    Gdx.app.log(MyGdxGame.TITLE, "settingScreen saved");

                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                }
            }
        };

        vSyncCheckBox = new CheckBox("vSync", skin);
        vSyncCheckBox.setChecked(Gdx.app.getPreferences(MyGdxGame.TITLE).getBoolean("vsync"));
        vSyncCheckBox.addListener(buttonHandler);


        levelDirectoryInput = new TextField(levelDirectory().path(), skin);
        levelDirectoryInput.setMessageText("level directory");

        back = new TextButton("BACK", skin);
        back.addListener(buttonHandler);
        back.pad(10);
        table.add("SETTINGS").spaceBottom(50).colspan(3).expandX().row();
        table.add();
        table.add(new Label("level directory", skin));
        table.add().row();
        table.add(vSyncCheckBox).top().expandY();
        table.add(levelDirectoryInput).top().fillX();
        table.add(back).bottom().right();

        stage.addActor(table);


    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.setFillParent(true);
        table.setClip(true);
    }

    @Override
    public void pause() {
        Gdx.app.getPreferences(MyGdxGame.TITLE).putBoolean("fullscreen", true);
        Gdx.app.getPreferences(MyGdxGame.TITLE).getBoolean("fullscreen");
        Gdx.app.getPreferences(MyGdxGame.TITLE).flush();
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
        skin.dispose();
    }
}
