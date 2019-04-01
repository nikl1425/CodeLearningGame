package com.mygdx.game;

import com.badlogic.gdx.Game;


public class MyGdxGame extends Game {

	public static final String TITLE = "Cast-Down", VERSION = "0.0.0.0.0.reallyEarly";

	@Override
	public void create () {
		setScreen(new com.screens.GameScreen());
	}

	@Override
	public void resize (int width, int height) {
	super.resize(width, height);
	}

	@Override
	public void render () {
	super.render();
	}

	@Override
	public void pause () {
	super.pause();
	}

	@Override
	public void resume () {
	super.resume();
	}


	@Override
	public void dispose () {
		super.dispose();
	}



}
