package com.dsp.livemusic;

import javazoom.jl.decoder.JavaLayerException;

import jdk.internal.util.xml.impl.Input;
import org.jgroups.*;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.conf.ClassConfigurator;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

import java.io.*;



public class Node implements  MembershipListener, MessageListener{

    private JChannel channel;
    private JChannel rpcChannel;
    private JChannel stateChannel;
    private RspList stateRspList;
    private RequestOptions opts = new RequestOptions(ResponseMode.GET_ALL, 5000);
    private String channelName;
    public static final short ID = 3500;
    private String filename;
    private GroupManager gpManager;
    private PlayerHandler playerHandler;
    private MessageDispatcher msgDispatcher;
    private MessageDispatcher stateDispatcher;
    private RpcDispatcher rpcDispactcher;
    private State state;
    private int frames = 0;
    private NodeState nodeState;



    public Node( String name) throws Exception {
        nodeState = NodeState.NOT_SET;
        this.filename = "Not_set";
        this.channelName = name;
        this.gpManager = new GroupManager(name);
        playerHandler = new PlayerHandler();//after getting the file name set the fileinput stream for the player

    }

    public void start() throws Exception {
        ClassConfigurator.add((short) 3500, FileHeader.class);
        channel = new JChannel().name(channelName);
        rpcChannel = new JChannel().name(channelName + "rpc");
        stateChannel = new JChannel().name(channelName+ "state");
        msgDispatcher = new MessageDispatcher(channel, null, this);
        stateDispatcher = new MessageDispatcher(stateChannel,null,this);
        rpcDispactcher = new RpcDispatcher(rpcChannel, this);
        channel.connect(GroupManager.GROUP_NAME);
        stateChannel.connect(GroupManager.GROUP_NAME);
        rpcChannel.connect(GroupManager.GROUP_NAME);
//        channel.getState(null,0);
        handleUserAction();
    }


    @Override
    public void viewAccepted(View view) {


        System.out.println("View = " + view);
        gpManager.viewUpdate(view);

        if(gpManager.isServer()){
            if(nodeState == NodeState.SERVER){
                System.out.print("A view change happened, You are still server");
                nodeState = NodeState.SERVER;
                return;
            }
            try {
                ServerState server = new ServerState(this);
                System.out.println("You Are now a server for the first time:");
                nodeState = NodeState.SERVER;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if(nodeState == NodeState.CLIENT){
                System.out.println("A view Change happened, You Are still a client");
                nodeState = NodeState.CLIENT;
                return;
            }
            try {
                ClientState client = new ClientState(this);
                System.out.println("You Are a Client for the first time:");
                nodeState = NodeState.CLIENT;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void receive(Message message) {

    }

    @Override
    public void getState(OutputStream outputStream) throws Exception {

        //going to be called from the server

        System.out.println("Going to send Message from the server!");
//
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                int read = 0;
//                int frames = 0;
//                try {
//                    while(read != -1){
//                        byte[] buff = new byte[1044];
//                        frames++;
//                        read = ((ServerState)state).getInputStream().read(buff);
//
//                        Message msg = new Message(null,null,buff,0,buff.length).putHeader(Node.ID, new FileHeader(getFilename(),false,frames));
//                        stateRspList = stateDispatcher.castMessage(null,msg,opts);
//
//                    }
//
//                    Message msg = new Message(null,null,null,0,0).putHeader(Node.ID, new FileHeader(getFilename(),true,frames));
//                    stateRspList = stateDispatcher.castMessage(null,msg,opts);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Thread t = new Thread(r);
//        t.start();

    }

    @Override
    public void setState(InputStream inputStream){
    }


    public void handleUserAction()throws Exception{


        if(nodeState == NodeState.SERVER){

//            playerHandler.setInputStream(new FileInputStream(""));
            ((ServerState) state).sendFrame();
        }
        else{
            System.out.println("You Are Listening to = "+ gpManager.getServerName());
        }

    }
    public void playFromStream() throws JavaLayerException, IOException {
        playerHandler.play();
    }

    public void pauseStream() throws JavaLayerException{
        playerHandler.pause();
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

    public NodeState getNodeState() {
        return nodeState;
    }

    public void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    public MessageDispatcher getStateDispatcher() {
        return stateDispatcher;
    }

    public void setStateDispatcher(MessageDispatcher stateDispatcher) {
        this.stateDispatcher = stateDispatcher;
    }
}