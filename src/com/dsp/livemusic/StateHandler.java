package com.dsp.livemusic;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by Natnael Zeleke on 2/7/2016.
 */
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
//                out = new FileOutputStream();
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }


}
