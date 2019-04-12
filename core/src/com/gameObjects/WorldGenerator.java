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
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,2,2,"maps/tiledmap.tmx"));
        }
        else if(newLevel == 3){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,3,3,"maps/LTC.tmx"));
        }else if(newLevel == 4){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,4,4,"maps/LTC.tmx"));
        }else if(newLevel == 5){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,5,5,"maps/LTC.tmx"));
        }else if(newLevel == 6){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,6,6,"maps/LTC.tmx"));
        }else if(newLevel == 7){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,7,7,"maps/LTC.tmx"));
        }else if(newLevel == 8){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,8,8,"maps/LTC.tmx"));
        }else if(newLevel == 9){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,9,9,"maps/LTC.tmx"));
        }else if(newLevel == 10){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2,2,10,10,"maps/LTC.tmx"));
        }
    }





}
