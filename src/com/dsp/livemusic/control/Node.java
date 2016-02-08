package com.dsp.livemusic.control;

import javazoom.jl.decoder.JavaLayerException;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.conf.ClassConfigurator;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Xabush Semrie on 2/4/2016.
 */
public class Node implements  MembershipListener {

    private JChannel channel;
    private JChannel rpcChannel;
    private String channelName;
    public static final short ID = 3500;
    private String filename;
    private GroupManager gpManager;
    private PlayerHandler playerHandler;
    private MessageDispatcher msgDispatcher;
    private RpcDispatcher rpcDispactcher;
    private State state;
    private int frames = 0;


    public Node(String filename, String name) throws Exception {
        this.filename = filename;
        this.channelName = name;
        this.gpManager = new GroupManager(name);
        playerHandler = new PlayerHandler(new FileInputStream(filename));
    }

    public void start() throws Exception {
        ClassConfigurator.add((short) 3500, FileHeader.class);
        channel = new JChannel().name(channelName);
        rpcChannel = new JChannel().name(channelName + "rpc");
        gpManager.setChannel(channel);
        msgDispatcher = new MessageDispatcher(channel, null, this);
        rpcDispactcher = new RpcDispatcher(rpcChannel, this);
        channel.connect(GroupManager.GROUP_NAME);
        rpcChannel.connect(GroupManager.GROUP_NAME);
    }

    public void playFromStream() throws JavaLayerException, IOException {
        playerHandler.play();
    }

    public void pauseStream() throws JavaLayerException{
        playerHandler.pause();
    }

    public void stopStream() throws JavaLayerException{
        playerHandler.stop();
    }

    public void closePlayer() {
        playerHandler.close();
    }
    public void resumePlayer(){
        playerHandler.resume();
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
        return playerHandler.getPlayerState();
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public RpcDispatcher getRpcDispactcher() {
        return rpcDispactcher;
    }
}