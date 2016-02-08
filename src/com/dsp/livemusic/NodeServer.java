package com.dsp.livemusic;

/**
 * Created by Natnael Zeleke on 2/7/2016.
 */
public class NodeServer {

    public static void main(String[] args) throws Exception{
        Node node = new Node("src/com/dsp/livemusic/test.mp3","Natnael");
        node.start();
    }
}
