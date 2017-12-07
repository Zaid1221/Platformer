package com.jarz.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jarz.game.Controller;
import com.jarz.game.Platformer;
import com.jarz.game.Scenes.HUD;
import com.jarz.game.Sprites.Player;
import com.jarz.game.Tools.WorldCreator;

import sun.rmi.runtime.Log;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Randy on 11/26/2017.
 */

public class PlayScreen implements Screen
{

    private Platformer game;
    Texture texture;
    private OrthographicCamera gamecam; //Follows Player in the World
    private Viewport gamePort; //Restricts Aspect Ratio of the Game to Fit Screens
    private HUD HUD;
    Controller controller;

    private TextureAtlas atlas;

    //Our Player Object
    private Player player;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //Game Map Related Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(Platformer game)
    {
        atlas = new TextureAtlas("Jekplz.pack"); //Grab Main Character Sprite Information

        //Set Cameras
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, gamecam); //Using FitViewport assures that Game Maintains the Aspect Ratio on Any Screen
        HUD = new HUD(game.batch); //Instantiate The Score Display of the Game

        //Load Map and Assign a Camera / Renderer to the Game
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10), true); //First Parameter Sets Gravity
        b2dr = new Box2DDebugRenderer();

        controller = new Controller();

        //Instantiate Player Object
        player = new Player(world, this);

        new WorldCreator(world, map); //Creates the World of Our Game

        //Add some Music Assets (HOI BOI THIS GON NEED A LOT OF TESTING SINCE ASSET MANAGER IS SUPER MEMORY MANAGEMENT INTENSIVE)
       // game.GAM.loadAssets();
        //game.GAM.manager.finishLoading();
        //game.GAM.manager.get("audio/music/BY8bit.mp3", Music.class).setLooping(true);
        //game.GAM.manager.get("audio/music/BY8bit.mp3", Music.class).play();
    }


    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    //Handles Inputs from the Player (Allows Movement)
    public void handleInput(float dt)
    {
        /*
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            Gdx.app.log("Pressed", "UP");
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)  && player.b2body.getLinearVelocity().x <= 2) {
            Gdx.app.log("Pressed", "RIGHT");
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  && player.b2body.getLinearVelocity().x >= -2) {
            Gdx.app.log("Pressed", "LEFT");
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        */
        //touch controls
        if(controller.isUpPressed()) {
            Gdx.app.log("Touched", "UP");
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        if(controller.isRightPressed()  && player.b2body.getLinearVelocity().x <= 2) {
            Gdx.app.log("Touched", "RIGHT");
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(controller.isLeftPressed()  && player.b2body.getLinearVelocity().x >= -2) {
            Gdx.app.log("Touched", "LEFT");
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    // dt stands for Delta Time
    public void update(float dt)//Constantly Checks for Inputs / Updates Aspects of the Game
    {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gamecam.position.x = player.b2body.getPosition().x;
        player.update(dt);
        HUD.update(dt);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) //Draws Objects to the Screen (Is CONSTANTLY Called)
    {
        update(delta);

        //Clears Screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders Screen
        renderer.render();

        //renderer out Box2DDebugLines
        b2dr.render(world,gamecam.combined);

        //Render Main Character
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Set our batch to draw what the HUD Camera Sees
        game.batch.setProjectionMatrix(HUD.stage.getCamera().combined);
        HUD.stage.draw();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
    }

    @Override
    public void resize(int width, int height) //Update Size of Game Screen
    {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    //Used for Memory Management
    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        HUD.dispose();
    }
}
