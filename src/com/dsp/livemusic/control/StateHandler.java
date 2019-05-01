package com.dsp.livemusic;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.util.Util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;


public class StateHandler implements RequestHandler{

    private Map<String,OutputStream> files;
    private Node node;
    public StateHandler(Map<String ,OutputStream> files,Node node){
        this.files = files;
        this.node = node;
    }

    @Override
    public Object handle(Message message) throws Exception {

        FileHeader hdr = (FileHeader)message.getHeader(Node.ID);
        if(hdr == null){
            return "Fail";
        }

        OutputStream out = this.files.get(hdr.filename);

        int frames = hdr.frame;
        try{
            if(out == null){
                out = new FileOutputStream(node.getFilename());
                files.put(hdr.filename,out);
            }

            if(hdr.eof){
                Util.close(files.remove(hdr.filename));
                System.out.println("The number of frames =" + frames);
            } else {
                out.write(message.getRawBuffer(), message.getOffset(), message.getLength());
                node.setFrames(frames);
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }


}
