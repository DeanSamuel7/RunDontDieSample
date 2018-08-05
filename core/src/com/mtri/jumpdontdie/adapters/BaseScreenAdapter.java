package com.mtri.jumpdontdie.adapters;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mtri.jumpdontdie.MainGame;

public class BaseScreenAdapter implements Screen, ContactListener{
    protected MainGame game;

    public BaseScreenAdapter(MainGame game){
        this.game = game;
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta){

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }

    @Override
    public void beginContact(Contact contact){

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

    protected boolean areContacted(Contact contact, Object object1, Object object2){
        Fixture a = contact.getFixtureA(), b = contact.getFixtureB();
        return (a.getUserData().equals(object1) && b.getUserData().equals(object2) || a.getUserData().equals(object2) && b.getUserData().equals(object1));
    }
}
