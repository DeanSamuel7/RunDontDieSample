package com.mtri.jumpdontdie.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mtri.jumpdontdie.MainGame;

public class SpikeActor extends Actor{
    private World world;
    private Texture texture;
    private Body body;
    private Fixture fixture;

    //constructor deprecated
    public SpikeActor(Texture texture){
        this.texture = texture;
        setSize(texture.getWidth(), texture.getHeight());
    }

    /**
     *
     *
     * @param world .
     * @param texture .
     * @param x     posición horizontal del centro del spike en metros
     * @param y     posición vertical del centro del spike en metros
     */
    public SpikeActor(World world, Texture texture, float x, float y){
        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y + 0.5f);
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0.5f,-0.5f);
        vertices[2] = new Vector2(0,0.5f);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        body = world.createBody(bodyDef);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("spike");
        shape.dispose();

        setPosition((x - 0.5f) * MainGame.PIXELS_IN_METERS, y * MainGame.PIXELS_IN_METERS);
        setSize(MainGame.PIXELS_IN_METERS,MainGame.PIXELS_IN_METERS);
    }

    public void dettach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    /*@Override
    public void act(float delta){
        super.act(delta);
        //setX(getX() - 250 * delta);
    }*/

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
}
