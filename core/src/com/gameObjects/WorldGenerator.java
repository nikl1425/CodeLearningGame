package com.gameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.screens.GameScreen;

public class WorldGenerator {


    public WorldGenerator(int newLevel){

        if(newLevel == 1){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,1,1,"maps/LTC.tmx"));
        }
        else if(newLevel == 2){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(3,3,3,2,"maps/tiledmap.tmx"));
        }
        else if(newLevel == 3){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(4,3,1,3,"maps/LTC.tmx"));
        }
    }





}
