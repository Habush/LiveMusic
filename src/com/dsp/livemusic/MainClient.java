package com.dsp.livemusic;

import org.jgroups.util.Util;

/**
 * Created by Xabush Semrie on 2/5/2016.
 */
public class MainClient {


    public static void main(String[] args) throws Exception
    {
        ClientState client = new ClientState( new Node("src/client/test.mp3", "client"));
    }
}
