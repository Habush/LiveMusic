package com.dsp.livemusic;

import javazoom.jl.decoder.JavaLayerException;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.conf.ClassConfigurator;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Xabush Semrie on 2/4/2016.
 */
public class Node implements Runnable, MembershipListener{

    private JChannel channel;
    private String channelName;
    public static final short ID = 3500;
    private String filename;
    private  GroupManager gpManager;
    private PlayerHandler playerHandler;
    private MessageDispatcher msgDispatcher;
    private State state;
    private PlayerState playerState = PlayerState.STOP;
    private int frames = 0;


    public Node(String filename, String name)
    {
        this.filename = filename;
        this.channelName = name;
        this.gpManager = new GroupManager(name);
    }

    public void start() throws Exception
    {
        ClassConfigurator.add((short) 3500, FileHeader.class);
        channel = new JChannel().name(channelName);
        //rpcDispatcher = new RpcDispatcher(channel, this);
        gpManager.setChannel(channel);
        msgDispatcher = new MessageDispatcher(channel,null, this);
        channel.connect(GroupManager.GROUP_NAME);
    }

    public void playFromStream() throws JavaLayerException, IOException
    {
        playerHandler = new PlayerHandler(new FileInputStream(filename));
        System.out.println(filename);
        playerState = PlayerState.PLAY;
        Thread thread = new Thread(this);
        thread.start();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public MessageDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    public void setMsgDispatcher(MessageDispatcher msgDispatcher) {
        this.msgDispatcher = msgDispatcher;
    }

    public GroupManager getGpManager() {
        return gpManager;
    }

    public void setGpManager(GroupManager gpManager) {
        this.gpManager = gpManager;
    }

    public JChannel getChannel() {
        return channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public static short getId() {
        return ID;
    }

    public String getFilename() {
        return filename;
    }

    public void run()
    {
        System.out.println("Thread running");
        try {
            while (true) {
                playerHandler.getPlayer().play();
//                System.out.println("Finished Playing a frame!");
                if (playerHandler.getPlayer().isComplete()) break;
            }

            System.out.println("Finished playing frames: The number of total frames counted is = " + frames);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void viewAccepted(View view) {
        gpManager.viewUpdate(view);
    }

    @Override
    public void suspect(Address address) {

    }

    @Override
    public void block() {

    }

    @Override
    public void unblock() {

    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }
}








