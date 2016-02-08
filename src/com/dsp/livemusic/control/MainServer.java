package com.dsp.livemusic.control;

/**
 * Created by Xabush Semrie on 2/5/2016.
 * Server Application entry point.
 */
public class MainServer {


    public static void main(String[] args) throws Exception
    {
       ServerState serverState = new ServerState(new Node("res/test.mp3", "server"));

    }



}
