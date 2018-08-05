package com.mtri.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mtri.jumpdontdie.MainGame;
import com.mtri.jumpdontdie.adapters.BaseScreenAdapter;

public class GameOverScreen extends BaseScreenAdapter{
    private Stage stage;
    private Skin skin;

    public GameOverScreen(final MainGame game){
        super(game);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Image image = new Image(game.getManager().get("gameover.png", Texture.class));
        image.setPosition((stage.getWidth() - image.getWidth()) / 2,stage.getHeight() - 150f);

        TextButton button1 = new TextButton("Play", skin);
        button1.setSize(150f, 100f);
        button1.setPosition(50f, 50f);
        button1.addCaptureListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(game.gameScreen);
            }
        });

        TextButton button2 = new TextButton("Menu",skin);
        button2.setSize(150,100);
        button2.setPosition(stage.getWidth() - 200, 50);
        button2.addCaptureListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(game.menuScreen);
            }
        });

        stage.addActor(image);
        stage.addActor(button1);
        stage.addActor(button2);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}
