package com.mtri.jumpdontdie.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mtri.jumpdontdie.MainGame;

public class FloorActor extends Actor{
    private World world;
    private Texture texture1;
    private Texture texture2;
    private Body body, leftBody;
    private Fixture fixture, leftFixture;

    /**
     * @param world .
     * @param texture1 .
     * @param texture2 .
     * @param x        distancia desde el borde izquierdo en metros
     * @param y        distancia desde el borde superior en metros
     * @param width    anchura del piso en metros
     */
    public FloorActor(World world, Texture texture1, Texture texture2, float x, float y, float width){
        this.world = world;
        this.texture1 = texture1;
        this.texture2 = texture2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y - 0.5f);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, 0.5f);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("floor");
        shape.dispose();

        //Spike left
        BodyDef leftLodyDef = new BodyDef();
        leftLodyDef.position.set(x, y - 0.55f);
        leftBody = world.createBody(leftLodyDef);
        PolygonShape leftShape = new PolygonShape();
        leftShape.setAsBox(0.2f, 0.45f);
        leftFixture = leftBody.createFixture(leftShape, 1);
        leftFixture.setUserData("spike");
        leftShape.dispose();

        setSize(width * MainGame.PIXELS_IN_METERS, MainGame.PIXELS_IN_METERS);
        setPosition(x * MainGame.PIXELS_IN_METERS, (y - 1) * MainGame.PIXELS_IN_METERS);
    }

    public void dettach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture1, getX(), getY(), getWidth(), getHeight());
        batch.draw(texture2, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }
}
