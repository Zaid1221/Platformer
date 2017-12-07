package com.jarz.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Randy on 12/4/2017.
 */

public class GameAssetManager
{
    public final AssetManager manager = new AssetManager();


    public void loadAssets()
    {
        manager.load("audio/music/BY8bit.mp3", Music.class);
    }

    public void dispose()
    {
        manager.dispose();
    }
}
