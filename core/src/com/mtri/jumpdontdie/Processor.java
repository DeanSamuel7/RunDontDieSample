package com.mtri.jumpdontdie;

import com.badlogic.gdx.InputAdapter;

public class Processor extends InputAdapter{
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        printLine("down-X: " + screenX + ", down-Y: " + screenY + ", down-P: " + pointer + ", down-B: " + button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        printLine("up-X: " + screenX + ", up-Y: " + screenY + ", upP: " + pointer + ",up-B: " + button);
        return true;
    }

    /*@Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        printLine("X: " + screenX + ", Y: " + screenY + ", P: " + pointer);
        return true;
    }*/

    private void printLine(String line){
        System.out.println(line);
    }
}
