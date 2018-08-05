package com.mtri.jumpdontdie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import com.mtri.jumpdontdie.screens.GameOverScreen;
import com.mtri.jumpdontdie.screens.GameScreen;
import com.mtri.jumpdontdie.screens.LoadingScreen;
import com.mtri.jumpdontdie.screens.MenuScreen;

public class MainGame extends Game implements ApplicationListener{
    public static final float PIXELS_IN_METERS = 45f; //90f
    public static final float IMPULSE = 20f;
    public static final float SPEED = 8f;
    public GameScreen gameScreen;
    public GameOverScreen gameOverScreen;
    public MenuScreen menuScreen;

    private AssetManager manager;

    public AssetManager getManager(){
        return manager;
    }

    public void finishLoading(){
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void create(){
        manager = new AssetManager();
        manager.load("floor.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("overfloor.png", Texture.class);
        manager.load("player.png", Texture.class);
        manager.load("playerdeath.png", Texture.class);
        manager.load("spike.png", Texture.class);
        manager.load("audio/die.ogg", Sound.class);
        manager.load("audio/jump.ogg", Sound.class);
        manager.load("audio/song.ogg", Music.class);

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose(){
        manager.dispose();
    }
}
