package com.dsp.livemusic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;

/**
 * Created by Xabush Semrie on 2/4/2016.
 * This class handles playing the frames
 */
public class PlayerHandler{

    private Player player;
    private FileInputStream input;
    private Node node;
    private PlayerState playerState;


    public PlayerHandler(FileInputStream in) throws JavaLayerException{

        input = in;
        player = new Player(in);

    }

    public void setNode(Node node)
    {
        this.node = node;
    }

    public void play(int frame) throws JavaLayerException
    {
        player.play(frame);
    }

    /*public void pause() throws JavaLayerException
    {
        player.stop();
    }*/


    public Player getPlayer() {
        return player;
    }


    public void close() {
        player.close();
    }
}
