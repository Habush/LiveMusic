package com.dsp.livemusic;

/**
 * Created by Natnael Zeleke on 2/7/2016.
 */
public class NodeClient {

    public static void main(String[] args) throws Exception{
        Node node =  new Node("src/client/test.mp3","Client");
        node.start();
    }
}
