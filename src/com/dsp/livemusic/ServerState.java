package com.dsp.livemusic;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * This class handles the server node
 */
public class ServerState implements State, RequestHandler{


    private FileInputStream in;
    private RpcDispatcher rpcDispatcher;
    private RspList rspList;
    private RequestOptions opts = new RequestOptions(ResponseMode.GET_ALL, 5000);
    private Node node;

    public ServerState(Node node) throws Exception
    {
        this.node = node;
        node.start();
        in = new FileInputStream(node.getFilename());
        sendFrame();
    }

    public void changeState()
    {
        node.setState(this);
    }


    public void sendFrame() throws Exception
    {
        Util.keyPress("Enter to start playback: ");
        int read = 0;
        while (read != -1) {

            byte[] buff = new byte[1044];
            read = in.read(buff);
            //offest += in.getByteCount();
            node.setFrames(node.getFrames() + 1) ;
            sendMessage(buff, 0, buff.length, false, node.getFrames());
            if (node.getPlayerState() != PlayerState.PLAY)
            {
                node.playFromStream();
            }

        }


        sendMessage(null, 0, 0, true, node.getFrames());
        System.out.println("Sent Message");

    }

    private void sendMessage(byte[] buf, int offset, int length, boolean eof, int fr) throws Exception {
        Message msg = new Message(null,null ,buf, offset, length).putHeader(Node.ID, new FileHeader(node.getFilename(), eof, fr));
        // set this if the sender doesn't want to receive the file
        msg.setTransientFlag(Message.TransientFlag.DONT_LOOPBACK);
        rspList = node.getMsgDispatcher().castMessage(null, msg, opts);
    }
    public void invokeMethod(PlayerState playerState) {


    }

    @Override
    public Object handle(Message message) throws Exception {
        return null;
    }
}
