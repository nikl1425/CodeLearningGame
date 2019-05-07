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
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,1,1,"maps/level4.tmx")); }
        else if(newLevel == 2){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,1,2,"maps/LTC.tmx")); }
        else if(newLevel == 3){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,1,3,"maps/LTC.tmx"));
        }else if(newLevel == 4){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(10,2,1,4,"maps/LTC.tmx"));
        }else if(newLevel == 5){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,2,5,"maps/LTC.tmx"));
        }else if(newLevel == 6){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,2,6,"maps/LTC.tmx"));
        }else if(newLevel == 7){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,2,7,"maps/LTC.tmx"));
        }else if(newLevel == 8){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,2,8,"maps/LTC.tmx"));
        }else if(newLevel == 9){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1,1,3,9,"maps/LTC.tmx"));
        }else if(newLevel == 10){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,4,10,"maps/LTC.tmx"));
        }
    }





}
