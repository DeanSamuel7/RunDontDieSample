package com.mtri.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mtri.jumpdontdie.MainGame;
import com.mtri.jumpdontdie.actors.SpikeActor;
import com.mtri.jumpdontdie.actors.PlayerActor;
import com.mtri.jumpdontdie.adapters.BaseScreenAdapter;

public class MainScreen extends BaseScreenAdapter{
    private Stage stage;
    private Texture player, spike;
    private PlayerActor playerActor;
    private SpikeActor spikeActor;

    public MainScreen(MainGame game){
        super(game);
        player = new Texture("player.png");
        playerActor = new PlayerActor(player);
        playerActor.setPosition(20,100);
        spike = new Texture("spike.png");
        spikeActor = new SpikeActor(spike);
        spikeActor.setPosition(500,100);
        stage = new Stage();
        stage.setDebugAll(true);
        stage.addActor(playerActor);
        stage.addActor(spikeActor);
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        detectCollissions();
        stage.draw();
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        stage.dispose();
        player.dispose();
        spike.dispose();
    }

    private void detectCollissions(){
        if(playerActor.isAlive() && playerActor.getX() + playerActor.getWidth() > spikeActor.getX()){
            printLine("Collission");
            playerActor.setAlive(false);
        }
    }

    private void printLine(String line){
        System.out.println(line);
    }
}
