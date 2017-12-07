package com.jarz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jarz.game.Screens.PlayScreen;


import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Zaid on 12/4/2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    OrthographicCamera cam;
    boolean upPressed, downPressed, leftPressed, rightPressed;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 400, cam);
        stage = new Stage(viewport, Platformer.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image up = new Image(new Texture("up.png"));
        up.setSize(50, 50);
        up.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("TAG", "Inside the touchdown function for press up");
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image down = new Image(new Texture("down.png"));
        down.setSize(50, 50);
        down.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image left = new Image(new Texture("left.png"));
        left.setSize(50, 50);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image right = new Image(new Texture("right.png"));
        right.setSize(50, 50);
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        table.add();
        table.add(up).size(up.getWidth(), up.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(left).size(left.getWidth(), left.getHeight());
        table.add();
        table.add(right).size(right.getWidth(), right.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(down).size(down.getWidth(), down.getHeight());
        table.add();

        stage.addActor(table);
    }

        public void draw(){
            stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
