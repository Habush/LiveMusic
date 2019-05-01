package com.dsp.livemusic;

import java.io.FileOutputStream;

/**
 * Created by Natnael Zeleke on 2/7/2016.
 */
public class GetFromState implements Runnable {

    private FileOutputStream outStream;
    private Node node;

    public GetFromState(Node node)throws Exception{
        this.node = node;
        outStream = new FileOutputStream(node.getFilename());

    }


    @Override
    public void run() {

    }
}
