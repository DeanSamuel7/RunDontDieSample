package com.mtri.jumpdontdie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mtri.jumpdontdie.MainGame;
import com.mtri.jumpdontdie.adapters.BaseScreenAdapter;

public class Main2DScreen extends BaseScreenAdapter{
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private World world;
    private Body playerBody, spikeBody, groundBody;
    private Fixture playerFixture, spikeFixture, groundFixture;
    private boolean isContacted,isAlive;

    public Main2DScreen(MainGame game){
        super(game);
        world = new World(new Vector2(0,-10),true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16,9);
        camera.translate(0,1);
        createSpike(6f);
        createPlayer();
        createGround();
        createContactListener();
        isAlive = true;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched() && isContacted){
            isContacted = false;
            jump();
        }

        if(isAlive){
            float y = playerBody.getLinearVelocity().y;
            playerBody.setLinearVelocity(8, y);
        }

        world.step(delta,6,2);
        camera.update();
        renderer.render(world,camera.combined);
    }

    @Override
    public void dispose(){
        playerBody.destroyFixture(playerFixture);
        spikeBody.destroyFixture(spikeFixture);
        groundBody.destroyFixture(groundFixture);
        world.destroyBody(playerBody);
        world.destroyBody(spikeBody);
        world.destroyBody(groundBody);
        world.dispose();
        renderer.dispose();
    }

    private void createPlayer(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0,0.5f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f,0.5f);
        playerBody = world.createBody(bodyDef);
        playerFixture = playerBody.createFixture(shape,3);
        playerFixture.setUserData("player");
        shape.dispose();
    }

    private void createSpike(float x){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, 0.5f);
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0.5f,-0.5f);
        vertices[2] = new Vector2(0,0.5f);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        spikeBody = world.createBody(bodyDef);
        spikeFixture = spikeBody.createFixture(shape,1);
        spikeFixture.setUserData("spike");
        shape.dispose();
    }

    private void createGround(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0,-1);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(500,1);
        groundBody = world.createBody(bodyDef);
        groundFixture = groundBody.createFixture(shape,1);
        groundFixture.setUserData("ground");
        shape.dispose();
    }

    private void createContactListener(){
        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact){
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();
                if(a.getUserData().equals("player") && b.getUserData().equals("ground") || a.getUserData().equals("ground") && b.getUserData().equals("player")){
                    printLine("contacted");
                    isContacted = true;
                }
                if(a.getUserData().equals("player") && b.getUserData().equals("spike") || a.getUserData().equals("spike") && b.getUserData().equals("player")){
                    printLine("death");
                    isAlive = false;
                }
            }

            @Override
            public void endContact(Contact contact){

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold){

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse){

            }
        });
    }

    private void jump(){
        Vector2 position = playerBody.getPosition();
        playerBody.applyLinearImpulse(0,20,position.x,position.y,true);
    }

    private void printLine(String line){
        System.out.println(line);
    }
}
