package com.mtri.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mtri.jumpdontdie.MainGame;
import com.mtri.jumpdontdie.actors.FloorActor;
import com.mtri.jumpdontdie.actors.PlayerActor;
import com.mtri.jumpdontdie.actors.SpikeActor;
import com.mtri.jumpdontdie.adapters.BaseScreenAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreenAdapter implements Runnable{
    private Stage stage;
    private Vector3 position;
    private World world;
    private PlayerActor playerActor;
    private List<FloorActor> floorActors;
    private List<SpikeActor> spikeActors;
    private Sound dieSound, jumpSound;
    private Music music;

    public GameScreen(MainGame game){
        super(game);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        position = new Vector3(stage.getCamera().position);

        //create world
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(this);

        //get sounds from manager
        dieSound = game.getManager().get("audio/die.ogg");
        jumpSound = game.getManager().get("audio/jump.ogg");
        music = game.getManager().get("audio/song.ogg");

        //instance arrays for spikes and floors
        spikeActors = new ArrayList<SpikeActor>();
        floorActors = new ArrayList<FloorActor>();
    }

    @Override
    public void show(){
        //get textures from manager
        Texture player = game.getManager().get("player.png");
        Texture playerDeath = game.getManager().get("playerdeath.png");
        Texture spike = game.getManager().get("spike.png");
        Texture floor = game.getManager().get("floor.png");
        Texture overFloor = game.getManager().get("overfloor.png");

        //create actors
        floorActors.add(new FloorActor(world,floor,overFloor,0,1,1010));
        float xPos = 7f, yPos, xRes;
        for(int i = 0; i < 34; i++){
            float xFloor = (10 * i) + (20 * (i + 1));
            floorActors.add(new FloorActor(world,floor,overFloor,xFloor ,2f,10f));
            xRes = xFloor + 10f;
            do{
                System.out.println(xPos);
                if(xPos >= xFloor){
                    yPos = 2f;
                }else{
                    yPos = 1f;
                }
                spikeActors.add(new SpikeActor(world, spike, xPos, yPos));
                xPos = xPos + 10f;
            }while(xPos < xRes);
        }
        playerActor = new PlayerActor(world,player,playerDeath,new Vector2(-0.5f,1.5f));

        //add actors to stage
        for(SpikeActor spikeActor : spikeActors){
            stage.addActor(spikeActor);
        }
        for(FloorActor floorActor : floorActors){
            stage.addActor(floorActor);
        }
        stage.addActor(playerActor);

        //set camera position
        stage.getCamera().position.set(position);
        stage.getCamera().update();

        //set background music
        music.setVolume(0.75f);
        music.play();
    }

    @Override
    public void hide(){
        //remove actors from stage
        stage.clear();

        //destroy resources in actors
        playerActor.dettach();
        playerActor.remove();
        for(FloorActor floorActor : floorActors){
            floorActor.dettach();
            floorActor.remove();
        }
        for(SpikeActor spikeActor : spikeActors){
            spikeActor.dettach();
            spikeActor.remove();
        }

        //clear arrays
        floorActors.clear();
        spikeActors.clear();
    }

    @Override
    public void render(float delta){
        //clear screen with color
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //jump if the player is alive and doesn't jumping
        if(Gdx.input.isTouched() && !playerActor.isJumping() && playerActor.isAlive()){
            jumpSound.play();
            playerActor.setJumping(true);
            playerActor.jump();
        }

        //update the stage
        stage.act();

        //step the world. This will update the physics and update entity positions.
        world.step(delta,6,2);

        //set the camera's positions to follow to the player through the world
        float decX = (stage.getWidth() / 2) - 150f;
        if(playerActor.getX() > 150f && playerActor.isAlive()){
            stage.getCamera().position.set(playerActor.getX() + decX, stage.getHeight() / 2 , 0);
        }

        //draw the stage, this is always last step
        stage.draw();
    }

    @Override
    public void dispose(){
        //remove references from stage and world
        stage.dispose();
        world.dispose();
    }

    @Override
    public void beginContact(Contact contact){
        if(areContacted(contact,"player", "floor")){
            playerActor.setJumping(false);
        }
        if(areContacted(contact,"spike", "player")){
            //if(playerActor.isAlive()){
                playerActor.setAlive(false);
                System.out.println("GAME OVER");
                music.stop();
                dieSound.play();
                stage.addAction(
                    Actions.sequence(
                        Actions.delay(1.5f), //wait 1.5 seconds
                        Actions.run(this)
                    )
                );
            //}
        }
    }

    @Override
    public void run(){
        game.setScreen(game.gameOverScreen);
    }
}
