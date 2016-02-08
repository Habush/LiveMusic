package com.dsp.livemusic;

import org.jgroups.Message;
import org.jgroups.blocks.*;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * This class handles the server node
 */
public class ServerState implements State, RequestHandler {


    private FileInputStream in;
    private RpcDispatcher rpcDispatcher;
    private RspList rspList;
    private RequestOptions opts = new RequestOptions(ResponseMode.GET_ALL, 5000);
    private Node node;

    public ServerState(Node node) throws Exception {
        this.node = node;
        changeState();
        in = new FileInputStream(node.getFilename());
        this.node.getMsgDispatcher().setRequestHandler(this);

    }

    public void changeState() {
        this.node.setState(this);
    }


    public void sendFrame() throws Exception {
        Util.keyPress("Enter to start playback: ");
        int read = 0;
        while (read != -1) {

            byte[] buff = new byte[1044];
            read = in.read(buff);
            node.setFrames(node.getFrames() + 1);
            sendMessage(buff, 0, buff.length, false, node.getFrames());
            if (node.getFrames() == 100)
            {
                if (node.getPlayerState() == PlayerState.NOTSTARTED)
                {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                invokeMethod();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    Thread t = new Thread(r);
                    t.start();
                }
            }
        }
        sendMessage(null, 0, 0, true, node.getFrames());
        System.out.println("Sent Message");
    }

    private void sendMessage(byte[] buf, int offset, int length,
                             boolean eof, int fr) throws Exception {
        Message msg = new Message(null, null, buf, offset, length).putHeader(Node.ID, new
                FileHeader(node.getFilename(), eof, fr));
        // set this if the sender doesn't want to receive the file
        msg.setTransientFlag(Message.TransientFlag.DONT_LOOPBACK);
        rspList = node.getMsgDispatcher().castMessage(null, msg, opts);
    }

    public void invokeMethod() throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a command: ");
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            MethodCall mCall;
            if (command.equalsIgnoreCase("stop")) break;
            switch (command) {
                case "play":
                    if (node.getPlayerState() == PlayerState.PLAY) {
                        System.out.println("Already playing");
                    } else{
                        mCall = new MethodCall(node.getClass().getMethod("playFromStream", null));
                        rspList = node.getRpcDispactcher().callRemoteMethods(null, mCall, opts);
                        System.out.println(rspList);
                    }
                    break;
                case "pause":
                    if (node.getPlayerState() == PlayerState.PAUSE)
                        System.out.println("Already paused");
                    else{
                        mCall = new MethodCall(node.getClass().getMethod("pauseStream", null));
                        rspList = node.getRpcDispactcher().callRemoteMethods(null, mCall, opts);
                        System.out.println(rspList);
                    }
                    break;
                case "close":
                    mCall = new MethodCall(node.getClass().getMethod("closePlayer", null));
                    node.getRpcDispactcher().callRemoteMethods(null, mCall, opts);
                    break;
                case "resume":
                    mCall = new MethodCall(node.getClass().getMethod("resumePlayer", null));
                    node.getRpcDispactcher().callRemoteMethods(null, mCall, opts);
                    return;
                default:
                    break;
            }
        }
        MethodCall mCall = new MethodCall(node.getClass().getMethod("closePlayer", null));
        node.getRpcDispactcher().callRemoteMethods(null, mCall, opts);
    }

    public FileInputStream getInputStream() {
        return in;
    }

    public void setIn(FileInputStream in) {
        this.in = in;
    }

    @Override
    public Object handle(Message message) throws Exception {
        return null;
    }
}
