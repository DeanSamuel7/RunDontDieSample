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

public class MenuScreen extends BaseScreenAdapter{
    private Stage stage;
    private Skin skin;

    public MenuScreen(final MainGame game){
        super(game);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Image image = new Image(game.getManager().get("logo.png", Texture.class));
        image.setPosition(stage.getWidth() - image.getWidth() - 50f,stage.getHeight() - image.getHeight() - 50f);

        TextButton button = new TextButton("Play", skin);
        button.setSize(150f,100f);
        button.setPosition(50f, 50f);
        button.addCaptureListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(game.gameScreen);
            }
        });

        stage.addActor(image);
        stage.addActor(button);
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
