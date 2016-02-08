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
    //An enum encapsulating the different states of the player
    private PlayerState playerState = PlayerState.NOTSTARTED;

    //locking object used to communicate with player thread
    private final Object playerLock = new Object();

    public PlayerHandler(){

        player = null;
    }

//    public PlayerHandler(FileInputStream in) throws JavaLayerException{
//
//        player = new Player(in);
//
//    }


    public void setNode(Node node)
    {
    }

    public void play() throws JavaLayerException
    {
        synchronized (playerLock){
            switch (playerState){
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerState = PlayerState.PLAY;
                    t.start();
                    break;
                case PAUSE:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED
     */
    public boolean pause() throws JavaLayerException
    {
        synchronized (playerLock){
            if (playerState == PlayerState.PLAY)
                playerState = PlayerState.PAUSE;

            return playerState == PlayerState.PAUSE;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING
     */
    public boolean resume(){
        synchronized (playerLock){
            if (playerState == PlayerState.PAUSE){
                playerState = PlayerState.PLAY;
                playerLock.notifyAll();
            }

            return playerState == PlayerState.PLAY;
        }
    }
    public void stop(){
        synchronized (playerLock){
            playerState = PlayerState.FINISHED;
            playerLock.notifyAll();
        }
    }

    private void playInternal(){
        while (playerState != PlayerState.FINISHED){
            try {
                if(!player.play(1))
                    break;
            }
            catch (JavaLayerException e)
            {
                e.printStackTrace();
                break;
            }
            //check if paused or terminated
            synchronized (playerLock){
                while (playerState == PlayerState.PAUSE){
                    try {
                        playerLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
        close();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Closes the player, regardless of current state
     */
    public void close() {
        synchronized (playerLock){
            playerState = PlayerState.FINISHED;
        }
        try {
            player.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setInputStream(FileInputStream in) throws JavaLayerException{

        player = new Player(in);
    }
}
