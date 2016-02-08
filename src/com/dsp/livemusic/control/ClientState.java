package com.dsp.livemusic.control;

import com.dsp.livemusic.model.Metadata;
import com.dsp.livemusic.model.SongModel;
import org.apache.log4j.Logger;
import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.util.Util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * This server handles client node
 */
public class ClientState implements RequestHandler, State {

    private Map<String, OutputStream> files = new ConcurrentHashMap<String, OutputStream>();
    private Node node;
    private final static Logger log = Logger.getLogger(ClientState.class);

    public ClientState(Node node) throws Exception {
        this.node = node;
        node.start();
        this.node.getMsgDispatcher().setRequestHandler(this);
    }

    public void changeState() {
        node.setState(this);
    }


    public Object handle(Message msg) {
        FileHeader hdr = (FileHeader) msg.getHeader(Node.ID);
        if (hdr == null)
            return "Fail";
        if (hdr.msgType == 0)
        {
            Metadata mData = (Metadata)msg.getObject();
            SongModel model = new SongModel(mData);
            log.debug(String.format("Bitrate: %s\nSample rate: %s\nTime: %s", model.getBitRate(), model.getSampleRate(),model.getTime()));
            log.debug("Metadata received successfully!");
            return "Success!";
        }
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
