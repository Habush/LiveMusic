package com.dsp.livemusic;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.util.Util;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClientState implements RequestHandler, State {

    private Map<String, OutputStream> files = new ConcurrentHashMap<String, OutputStream>();
    private Node node;
    private StateHandler stateHandler;

    public ClientState(Node node) throws Exception {

        this.node = node;
        this.stateHandler = new StateHandler(files,node);
        changeState();
        this.node.getStateDispatcher().setRequestHandler(this.stateHandler);
    }

    public void changeState() {
        node.setState(this);
    }


    public Object handle(Message msg) {
        FileHeader hdr = (FileHeader) msg.getHeader(Node.ID);
        if (hdr == null)
            return "Fail";
        OutputStream out = files.get(hdr.filename);

        int frames = hdr.frame;
        try {
            if (out == null) { //the first receiving this file
                out = new FileOutputStream(node.getFilename());
                files.put(hdr.filename, out);


            }
            if (hdr.eof) {
                Util.close(files.remove(hdr.filename));
                System.out.println("The number of frames =" + frames);
            } else {
                out.write(msg.getRawBuffer(), msg.getOffset(), msg.getLength());
                node.setFrames(frames);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Success";
    }
}
