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

public class PlayerActor extends Actor{
    private Texture texture, texture2;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive, jumping;

    //constructor deprecated
    public PlayerActor(Texture texture){
        this.texture = texture;
        setSize(texture.getWidth(),texture.getHeight());
        alive = true;
    }

    //
    public PlayerActor(World world, Texture texture,Texture texture2, Vector2 position){
        this.world = world;
        this.texture = texture;
        this.texture2 = texture2;
        alive = true;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f,0.5f);//a partir del centro de gravedad
        body = world.createBody(bodyDef);
        fixture = body.createFixture(shape,3);
        fixture.setUserData("player");
        shape.dispose();

        setSize(MainGame.PIXELS_IN_METERS,MainGame.PIXELS_IN_METERS);
    }

    public void jump(){
        Vector2 position = body.getPosition();
        body.applyLinearImpulse(0,MainGame.IMPULSE, position.x, position.y,true);
    }

    public void dettach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public boolean isJumping(){
        return jumping;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(jumping){
            body.applyForceToCenter(0,-MainGame.IMPULSE + 10,true);
        }
        if(alive){
            float y = body.getLinearVelocity().y;
            body.setLinearVelocity(MainGame.SPEED, y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        setPosition((body.getPosition().x - 0.5f) * MainGame.PIXELS_IN_METERS,(body.getPosition().y - 0.5f) * MainGame.PIXELS_IN_METERS);
        if(alive){
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }else{
            batch.draw(texture2, getX(), getY(), getWidth(), getHeight());
        }
    }
}
