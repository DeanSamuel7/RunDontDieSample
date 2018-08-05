package com.mtri.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mtri.jumpdontdie.MainGame;
import com.mtri.jumpdontdie.adapters.BaseScreenAdapter;

public class LoadingScreen extends BaseScreenAdapter{
    private Stage stage;
    private Skin skin;
    private Label label;

    public LoadingScreen(MainGame game){
        super(game);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        label = new Label("loadin...", skin);
        label.setPosition((stage.getWidth() - label.getWidth()) / 2,stage.getHeight() - 180f);

        stage.addActor(label);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getManager().update()){
            game.finishLoading();
        }else {
            int progress = (int)(game.getManager().getProgress() * 100);
            label.setText("Loading... " + progress + "%");
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}
