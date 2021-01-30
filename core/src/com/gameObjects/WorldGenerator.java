package com.gameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

public class WorldGenerator {
    Stage stage;

    public void switchScreen(final Game game, final Screen newScreen){
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(fadeOut(0.5f));
        sequenceAction.addAction(run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(newScreen);
            }
        }));
    }

    public WorldGenerator(int newLevel){

        if(newLevel == 1){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(10,2,1,1,"maps/level1to4.tmx")); }
        else if(newLevel == 2){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(5,10,1,2,"maps/level1to4.tmx")); }
        else if(newLevel == 3){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(5,2,1,3,"maps/level1to4.tmx"));
        }else if(newLevel == 4){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(9,2,2,4,"maps/level1to4.tmx"));
        }else if(newLevel == 5){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(10,8,1,5,"maps/level5.tmx"));
        }else if(newLevel == 6){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(14,2,2,6,"maps/level6.tmx"));
        }else if(newLevel == 7){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(5,4,3,7,"maps/level7.tmx"));
        }else if(newLevel == 8){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(3,8,3,8,"maps/level8.tmx"));
        }else if(newLevel == 9){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(3,1,3,9,"maps/level9.tmx"));
        }else if(newLevel == 10){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,6,10,"maps/levelX.tmx"));
        }
    }





}
