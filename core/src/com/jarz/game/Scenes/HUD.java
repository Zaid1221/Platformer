package com.jarz.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.jarz.game.Platformer;


/**
 * Created by Randy on 11/26/2017.
 * This Class Displays Data on the Screen
 * Score, Level, Name, Time, and Health is Displayed Here.
 */

public class HUD implements Disposable//Displays Data on the Screen
{
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    //Labels that Are Displayed on the Screen
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label worldLabel;
    private Label playerLabel;
    private Label levelLabel;

    public HUD(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Platformer.V_WIDTH, Platformer.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Create a Table to Display our Labels Evenly on the Screen
        Table table = new Table();
        table.top(); //Sets Alignment
        table.setFillParent(true); //Sets Table to the Size of the Stage

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        levelLabel = new Label("TEST", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        worldLabel = new Label("LEVEL 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));
        playerLabel = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));

        //Top Row
        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();

        //Lower Row
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt)
    {
        timeCount += dt;
        if(timeCount >= 1)
        {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value)
    {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
