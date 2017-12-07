package com.jarz.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jarz.game.Screens.PlayScreen;

public class Platformer extends Game {
	public static SpriteBatch batch;
	public static final int V_WIDTH = 400;//Virtual Width
	public static final int V_HEIGHT = 205;//Virtual Height
	public static final float PPM = 100; //Pixels Per Meter
    public GameAssetManager GAM = new GameAssetManager();
	Controller controller;

	@Override
	public void create () //Start Game
	{
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		controller = new Controller();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		GAM.dispose();
	}

	@Override
	public void render ()
	{
		super.render();
	}

}
