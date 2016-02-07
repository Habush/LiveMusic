package com.dsp.livemusic;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * Application entry point.
 */
public class MainServer {


    public static void main(String[] args) throws Exception
    {
       ServerState serverState = new ServerState(new Node("src/com/dsp/livemusic/test.mp3", "server"));

    }



}
